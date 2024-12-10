
Argument passed to when() is null!
Example of correct stubbing:
    doThrow(new RuntimeException()).when(mock).someMethod();
Also, if you use @Mock annotation don't miss openMocks()
org.mockito.exceptions.misusing.NullInsteadOfMockException: 
Argument passed to when() is null!
Example of correct stubbing:
    doThrow(new RuntimeException()).when(mock).someMethod();
Also, if you use @Mock annotation don't miss openMocks()
	at com.sbi.epay.authentication.service.AuthenticationServiceTest.testGenerationAccessToken(AuthenticationServiceTest.java:40)
	at java.base/java.lang.reflect.Method.invoke(Method.java:580)


OpenJDK 64-Bit Server VM warning: Sharing is only supported for boot loader classes because bootstrap classpath has been appended

> Task :test
AuthenticationServiceTest > testGenerationAccessToken FAILED
    org.mockito.exceptions.misusing.NullInsteadOfMockException at AuthenticationServiceTest.java:40
1 test completed, 1 failed
> Task :test FAILED
FAILURE: Build failed with an exception.
* What went wrong:
Execution failed for task ':test'.
> There were failing tests. See the report at:



@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest {
    @Mock
    private AuthRequestValidator authRequestValidator;

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

        doNothing().when(authRequestValidator).validAuthRequest(any(AccessTokenRequest.class));

        when(jwtService.generateAccessToken(any(AccessTokenRequest.class))).thenReturn(expectedToken);

        String actualToken = authenticationService.generateAccessToken(mockRequest);

        assertEquals(expectedToken, actualToken);

        verify(authRequestValidator, times(1)).validAuthRequest(mockRequest);
        verify(jwtService, times(1)).generateAccessToken(mockRequest);
    }

