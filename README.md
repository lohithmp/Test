import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class AuthenticationServiceTest {

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthenticationService authenticationService;

    @BeforeEach
    void setUp() {
        // Ensure mocks are initialized before each test
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGenerateAccessToken() {
        // Arrange
        AccessTokenRequest mockRequest = new AccessTokenRequest();
        mockRequest.setApiKey("Value1");
        mockRequest.setSecretKey("Value2");
        mockRequest.setCustomerId("Value3");
        mockRequest.setExpirationTime(3600);
        mockRequest.setTokenType("ACCESS");

        String expectedToken = "accessToken";

        // Mock static method
        try (MockedStatic<AuthRequestValidator> mockedValidator = mockStatic(AuthRequestValidator.class)) {
            mockedValidator.when(() -> AuthRequestValidator.validAuthRequest(mockRequest)).thenAnswer(invocation -> null);
            when(jwtService.generateAccessToken(mockRequest)).thenReturn(expectedToken);

            // Act
            String actualToken = authenticationService.generateAccessToken(mockRequest);

            // Assert
            assertNotNull(actualToken);
            assertEquals(expectedToken, actualToken);

            // Verify interactions
            mockedValidator.verify(() -> AuthRequestValidator.validAuthRequest(mockRequest), times(1));
            verify(jwtService, times(1)).generateAccessToken(mockRequest);
        }
    }
}
