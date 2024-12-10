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





The signing key's size is 64 bits which is not secure enough for the HS512 algorithm.  The JWT JWA Specification (RFC 7518, Section 3.2) states that keys used with HS512 MUST have a size >= 512 bits (the key size must be greater than or equal to the hash output size).  Consider using the io.jsonwebtoken.security.Keys class's 'secretKeyFor(SignatureAlgorithm.HS512)' method to create a key guaranteed to be secure enough for HS512.  See https://tools.ietf.org/html/rfc7518#section-3.2 for more information.
io.jsonwebtoken.security.WeakKeyException: The signing key's size is 64 bits which is not secure enough for the HS512 algorithm.  The JWT JWA Specification (RFC 7518, Section 3.2) states that keys used with HS512 MUST have a size >= 512 bits (the key size must be greater than or equal to the hash output size).  Consider using the io.jsonwebtoken.security.Keys class's 'secretKeyFor(SignatureAlgorithm.HS512)' method to create a key guaranteed to be secure enough for HS512.  See https://tools.ietf.org/html/rfc7518#section-3.2 for more information.
	at io.jsonwebtoken.SignatureAlgorithm.assertValid(SignatureAlgorithm.java:389)
	at io.jsonwebtoken.SignatureAlgorithm.assertValidSigningKey(SignatureAlgorithm.java:317)
	at io.jsonwebtoken.impl.DefaultJwtBuilder.signWith(DefaultJwtBuilder.java:255)
	at io.jsonwebtoken.impl.DefaultJwtBuilder.signWith(DefaultJwtBuilder.java:267)
	at io.jsonwebtoken.impl.DefaultJwtBuilder.signWith(DefaultJwtBuilder.java:277)
	at com.sbi.epay.authentication.service.JwtServiceTest.testGenerateToken(JwtServiceTest.java:46)
	at java.base/java.lang.reflect.Method.invoke(Method.java:580)
	at java.base/java.util.ArrayList.forEach(ArrayList.java:1596)
	at java.base/java.util.ArrayList.forEach(ArrayList.java:1596)


OpenJDK 64-Bit Server VM warning: Sharing is only supported for boot loader classes because bootstrap classpath has been appended
