
@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private static final LoggerUtility logger = LoggerFactoryUtility.getLogger(AuthenticationService.class);
    private final JwtService jwtService;

    /**
     * generateToken() generates token
     *
     * @param accessTokenRequest Authentication
     * @return JWT token in string form.
     */
    public String generateAccessToken(AccessTokenRequest accessTokenRequest) {
        logger.debug("Initiate generateAccessToken for {}", accessTokenRequest);
        AuthRequestValidator.validAuthRequest(accessTokenRequest);
        return jwtService.generateAccessToken(accessTokenRequest);
    }

    public String generateTransactionToken(TransactionTokenRequest transactionTokenRequest) {
        logger.debug("Initiate generateTransactionToken for {}", transactionTokenRequest);
        AuthRequestValidator.validAuthRequest(transactionTokenRequest);
        return jwtService.generateTransactionToken(transactionTokenRequest);
    }

    public String generateUserToken(UserTokenRequest userTokenRequest) {
        logger.debug("Initiate generateUserToken for {}", userTokenRequest);
        AuthRequestValidator.validAuthRequest(userTokenRequest);
        return jwtService.generateUserLoginToken(userTokenRequest);
    }

    public String generatePaymentToken(PaymentTokenRequest paymentTokenRequest) {
        logger.debug("Initiate generatePaymentToken for {}", paymentTokenRequest);
        AuthRequestValidator.validAuthRequest(paymentTokenRequest);
        return jwtService.generatePaymentToken(paymentTokenRequest);
    }

}


























package com.sbi.epay.authentication.service;

import com.sbi.epay.authentication.common.AppConfig;
import com.sbi.epay.authentication.model.*;
import com.sbi.epay.logging.utility.LoggerFactoryUtility;
import com.sbi.epay.logging.utility.LoggerUtility;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Class Name: JwtService
 * *
 * Description: Service Class for generating and validating JSON Web Tokens (JWT).
 * * This class provides methods to create signed JWTs using various inputs, including username/password, API keys, and hashed values.
 * *
 * Author: V1018217(Nirvay K. Bikram)
 * Copyright (c) 2024 [State Bank of India]
 * All rights reserved
 * *
 * Version:1.0
 */
@Component
@RequiredArgsConstructor
public class JwtService {
    private static final LoggerUtility logger = LoggerFactoryUtility.getLogger(JwtService.class);
    private final AppConfig appConfig;

    /**
     * Generates a JWT token with the specified claims, secret key, and expiration time.
     *
     * @param claims         A map of claims to include in the token.
     * @param userName       The secret key used to sign the token.
     * @param expirationTime The expiration time of the token in milliseconds.
     * @return A signed JWT token as a string.
     */
    private String generateToken(Map<String, Object> claims, String userName, int expirationTime) {
        logger.info("ClassName - JwtService,MethodName - generateToken,generate a JWT token  with the specified claims, secret key, and expiration time.");
        return Jwts.builder().claims(claims).subject(userName) // Subject (e.g., user ID)
                .issuedAt(new Date(System.currentTimeMillis()))// Issued time
                .expiration(DateUtils.addHours(new Date(), expirationTime))
                .signWith(SignatureAlgorithm.HS512, appConfig.getSecretKey()) // Use the HS512 algorithm
                .compact();


    }

    /**
     * Generates a JWT token using a username, expirationTime and role.
     *
     * @param userTokenRequest
     * @returnA signed JWT token as a string.
     */
    public String generateUserLoginToken(UserTokenRequest userTokenRequest) {
        logger.info("ClassName - JwtService,MethodName - generateTokenWithUsernamePassword, Generates a JWT token using a username, expirationTime and role.");
        Map<String, Object> claims = new HashMap<>();
        claims.put(EPayPlatformJwtClaimsSet.USERNAME, userTokenRequest.getUsername());
        claims.put(EPayPlatformJwtClaimsSet.ROLE, userTokenRequest.getRoles());
        claims.put(EPayPlatformJwtClaimsSet.TYPE, userTokenRequest.getTokenType());
        return generateToken(claims, appConfig.getSecretKey(), userTokenRequest.getExpirationTime());
    }

    /**
     * Generates a JWT token for a payment.
     *
     * @param paymentTokenRequest
     * @returnA signed JWT token as a string.
     */
    public String generatePaymentToken(PaymentTokenRequest paymentTokenRequest) {
        logger.info("ClassName - JwtService,MethodName - generateTokenWithUsernamePassword, Generates a JWT token using a username, expirationTime and role.");
        Map<String, Object> claims = new HashMap<>();
        claims.put(EPayPlatformJwtClaimsSet.USERNAME, paymentTokenRequest.getSbiOrderReferenceNumber());
        claims.put(EPayPlatformJwtClaimsSet.ROLE, paymentTokenRequest.getRoles());
        claims.put(EPayPlatformJwtClaimsSet.TYPE, paymentTokenRequest.getTokenType());
        claims.put(EPayPlatformJwtClaimsSet.ORDER_NUMBER, paymentTokenRequest.getSbiOrderReferenceNumber());
        claims.put(EPayPlatformJwtClaimsSet.ATRN_NUMBER, paymentTokenRequest.getAtrnNumber());
        claims.put(EPayPlatformJwtClaimsSet.MID, paymentTokenRequest.getMid());
        return generateToken(claims, appConfig.getSecretKey(), paymentTokenRequest.getExpirationTime());
    }

    /**
     * Generates a JWT token using an API key , role, expirationTime and secret.
     *
     * @param accessTokenRequest
     * @return A signed JWT token as a string.
     */
    public String generateAccessToken(AccessTokenRequest accessTokenRequest) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(EPayPlatformJwtClaimsSet.USERNAME, accessTokenRequest.getMid());
        claims.put(EPayPlatformJwtClaimsSet.MID, accessTokenRequest.getMid());
        claims.put(EPayPlatformJwtClaimsSet.ROLE, accessTokenRequest.getRoles());
        claims.put(EPayPlatformJwtClaimsSet.TYPE, accessTokenRequest.getTokenType());
        return generateToken(claims, accessTokenRequest.getMid(), accessTokenRequest.getExpirationTime());
    }

    /**
     * Generates a JWT token using a hash of the order reference number, expiration time and merchant ID.
     *
     * @param transactionTokenRequest
     * @return A signed JWT token as a string.
     */
    public String generateTransactionToken(TransactionTokenRequest transactionTokenRequest) {
        logger.info("ClassName - JwtService,MethodName - generateTokenWithHash, Generates a JWT token using a hash of the order reference number, expiration time and merchant ID.");
        Map<String, Object> claims = new HashMap<>();
        claims.put(EPayPlatformJwtClaimsSet.ORDER_NUMBER, transactionTokenRequest.getSbiOrderReferenceNumber());
        claims.put(EPayPlatformJwtClaimsSet.MID, transactionTokenRequest.getMid());
        claims.put(EPayPlatformJwtClaimsSet.USERNAME, transactionTokenRequest.getSbiOrderReferenceNumber());
        claims.put(EPayPlatformJwtClaimsSet.ROLE, transactionTokenRequest.getRoles());
        claims.put(EPayPlatformJwtClaimsSet.TYPE, transactionTokenRequest.getTokenType());
        return generateToken(claims, appConfig.getSecretKey(), transactionTokenRequest.getExpirationTime());
    }


    /**
     * Get all claims from token. and @Param token
     *
     * @param token as a String
     * @return Claims of the token
     */
    public Claims getAllClaimsFromToken(String token) {
        logger.info("ClassName - JwtService,MethodName - getAllClaimsFromToken, getting all claims from token.");
        return Jwts.parser().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
    }

    /**
     * Get SigningKey
     *
     * @return signed key
     */
    private Key getSignKey() {
        logger.info("ClassName - JwtService,MethodName - getSignKey, getting SigningKey.");
        byte[] keyBytes = Decoders.BASE64.decode(appConfig.getSecretKey());
        return Keys.hmacShaKeyFor(keyBytes);

    }


    /**
     * Get username from token
     *
     * @param token as a String
     * @return String username from token
     */
    public String getUsernameFromToken(String token) {
        logger.info("ClassName - JwtService,MethodName - getUsernameFromToken, username from token.");
        final Claims claims = getAllClaimsFromToken(token);
        return claims.get(EPayPlatformJwtClaimsSet.USERNAME) == null ? null : String.valueOf(claims.get(EPayPlatformJwtClaimsSet.USERNAME));
    }


    /**
     * Get Claims from Token
     *
     * @param token
     * @param claimsResolver
     * @param <T>
     * @return All Claims from the token
     */
    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        logger.info("ClassName - JwtService,MethodName - getClaimFromToken, Claims from token.");
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }


    /**
     * Get expiration time from token
     *
     * @param token is as String
     * @return Boolean true if token is expired otherwise false.
     */
    private Boolean isTokenExpired(String token) {
        logger.info("ClassName - JwtService,MethodName - isTokenExpired, getting expiration time from token.");
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    /**
     * Getting expiration time from token.
     *
     * @param token
     * @return Date from token
     */
    protected Date getExpirationDateFromToken(String token) {
        logger.info("ClassName - JwtService,MethodName - getExpirationDateFromToken, getting expiration time from token.");
        return getClaimFromToken(token, Claims::getExpiration);

    }

    /**
     * Validate token using user details and expiration time.
     *
     * @param token
     * @param authenticateUser
     * @return Boolean if username and expiration is valid then return true else return false.
     */
    public boolean isTokenValid(String token, EPayPrincipal authenticateUser) {
        final String userName = getUsernameFromToken(token);
        return (userName.equals(authenticateUser.getUsername())) && !isTokenExpired(token);
    }

    /**
     * <p>
     * Get role from JWT token
     *
     * @param token
     * @return String Role from token
     */
    private String getRoleFromToken(String token) {
        logger.info("ClassName - JwtService,MethodName - getApiKeyFromToken, getting role from JWT token.");
        final Claims claims = getAllClaimsFromToken(token);
        return claims.get(EPayPlatformJwtClaimsSet.ROLE) == null ? null : String.valueOf(claims.get(EPayPlatformJwtClaimsSet.ROLE));
    }


}













public class AuthRequestValidator {
    public static final String TOKEN_TYPE = "Token Type";
    public static final String EXPIRY_TIME = "Expiry Time";
    private static final LoggerUtility logger = LoggerFactoryUtility.getLogger(AuthRequestValidator.class);

    /**
     * Validate authentication respective request object based on token type.
     *
     * @param tokenRequest gets request for validation.
     */
    public static void validAuthRequest(TokenRequest tokenRequest) {
        List<ErrorDto> errorDtoList = new ArrayList<>();
        switch (tokenRequest.getTokenType()) {
            case ACCESS -> validAccessTokenRequest((AccessTokenRequest) tokenRequest, errorDtoList);
            case PAYMENT -> validPaymentTokenRequest((PaymentTokenRequest) tokenRequest, errorDtoList);
            case TRANSACTION -> validTransactionTokenRequest((TransactionTokenRequest) tokenRequest, errorDtoList);
            case USER -> validUserTokenRequest((UserTokenRequest) tokenRequest, errorDtoList);
            default ->
                    errorDtoList.add(new ErrorDto(ErrorConstants.INVALID_ERROR_CODE, MessageFormat.format(ErrorConstants.INVALID_ERROR_MESSAGE, TOKEN_TYPE)));
        }

        if (!CollectionUtils.isEmpty(errorDtoList)) {
            logger.error("Error -> ", errorDtoList);
            throw new AuthenticationException(errorDtoList);
        }
    }
















@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest {

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private  AuthenticationService authenticationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGenerationAccessToken() {
        AccessTokenRequest mockRequest = new AccessTokenRequest();

        mockRequest.setApiKey("Value1");
        mockRequest.setSecretKey("value2");
        mockRequest.setCustomerId("valoue3");

        String expectedToken = "accessToken";

//        doNothing().when(AuthRequestValidator.class);
//        AuthRequestValidator.validAuthRequest(mockRequest);
//        when(jwtService.generateAccessToken(mockRequest)).thenReturn(expectedToken);
//        String actualToken = authenticationService.generateAccessToken(mockRequest);
//        assertNotNull(actualToken);
//        when(jwtService.generateAccessToken(any(AccessTokenRequest.class))).thenReturn(expectedToken);

        try (MockedStatic<AuthRequestValidator> mockedValidator =  Mockito.mockStatic(AuthRequestValidator.class)) {
            mockedValidator.when(() -> AuthRequestValidator.validAuthRequest(mockRequest)).thenAnswer(invocation -> null);
            when(jwtService.generateAccessToken(mockRequest)).thenReturn(expectedToken);

            String actualToken = authenticationService.generateAccessToken(mockRequest);
            assertNotNull(actualToken);
            assertEquals(expectedToken, actualToken);
            mockedValidator.verify(() -> AuthRequestValidator.validAuthRequest(mockRequest), times(1));
        }

//        verify(AuthRequestValidator.class, times(1)).validAuthRequest(mockRequest);
        verify(jwtService, times(1)).generateAccessToken(mockRequest);
    }

}



Cannot invoke "com.sbi.epay.authentication.service.JwtService.generateAccessToken(com.sbi.epay.authentication.model.AccessTokenRequest)" because "this.jwtService" is null
java.lang.NullPointerException: Cannot invoke "com.sbi.epay.authentication.service.JwtService.generateAccessToken(com.sbi.epay.authentication.model.AccessTokenRequest)" because "this.jwtService" is null
	at com.sbi.epay.authentication.service.AuthenticationServiceTest.testGenerationAccessToken(AuthenticationServiceTest.java:48)
	at java.base/java.lang.reflect.Method.invoke(Method.java:580)


OpenJDK 64-Bit Server VM warning: Sharing is only supported for boot loader classes because bootstrap classpath has been appended
