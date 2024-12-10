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


@Data
@Component
@RequiredArgsConstructor
public class AppConfig {

    /**
     * JWT secretKey declaration and fetching secret-key from application.properties files.
     */
    @Value("${jwt.secret.key}")
    private String secretKey;
    /**
     * JWT whiteListUrls declaration  and fetching whiteListUrls from application.properties files.
     */
    @Value("${security.whitelist.url}")
    private String[] whiteListUrls;
}
