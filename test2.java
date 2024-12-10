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







    import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
public class JwtServiceTest {

    @InjectMocks
    private JwtService jwtService;

    @Mock
    private AppConfig appConfig;

    @Test
    public void testGenerateToken() {
        // Mock configuration
        String secretKey = "mySecretKey";
        when(appConfig.getSecretKey()).thenReturn(secretKey);

        // Test input
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", "USER");
        String userName = "testUser";
        int expirationTime = 1;

        // Call the private method indirectly or change it to protected/package-private for testing
        String token = Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime * 60 * 60 * 1000))
                .signWith(SignatureAlgorithm.HS512, appConfig.getSecretKey())
                .compact();

        // Assert the result
        assertNotNull(token);
    }
}
