    public String generateAccessToken(AccessTokenRequest accessTokenRequest) {
        logger.debug("Initiate generateAccessToken for {}", accessTokenRequest);
        AuthRequestValidator.validAuthRequest(accessTokenRequest);
        return jwtService.generateAccessToken(accessTokenRequest);
    }
