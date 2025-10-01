 public Claims verifyJwtTokenAndGetClaims(String token) {
        Claims claims = getAllClaimsFromToken(token);
        if (issuer.equalsIgnoreCase(claims.getIssuer())) {
            if (isTokenExpired(claims.getExpiration())) {
                throw new IllegalStateException(EPayAuthenticationErrorConstants.TOKEN_EXPIRED);
            } else if (authenticationUserService.isTokenInValid(token, claims.get(TYPE, String.class))) {
                throw new IllegalStateException(EPayAuthenticationErrorConstants.INVALID_TOKEN);
            }
            String userName = (String) claims.get(USERNAME);
            String userNameWithTokenType = String.join(EPayAuthenticationConstant.JOINER, (String) claims.get(USERNAME), claims.get(TYPE, String.class));
            logger.info("Token request received for userNameWithTokenType: {}", userNameWithTokenType);
            EPayPrincipal ePayPrincipal = authenticationUserService.loadUserByUserName(userNameWithTokenType).orElseThrow(() -> new EPaySecurityException(EPayAuthenticationErrorConstants.NOT_FOUND_ERROR_CODE, MessageFormat.format(EPayAuthenticationErrorConstants.NOT_FOUND_ERROR_MESSAGE, EPayAuthenticationErrorConstants.USER_NAME)));
            logger.info("Token request received for userNameWithTokenType: {}", userNameWithTokenType);
            if (!ePayPrincipal.getAuthenticationId().equalsIgnoreCase(userName)) {
                throw new IllegalStateException(EPayAuthenticationErrorConstants.INVALID_USER);
            }
        } else {
            throw new IllegalStateException(EPayAuthenticationErrorConstants.INVALID_ISSUER);
        }
        return claims;
    }

public interface AuthenticationUserService {
    Optional<EPayPrincipal> loadUserByUserName(String userName);

    boolean isTokenInValid(String token, String tokenType);
}
