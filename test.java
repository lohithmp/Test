import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.epay.merchant.dao.MerchantLoginDao;
import com.epay.merchant.dto.MerchantUserDto;
import com.epay.merchant.entity.MerchantUser;
import com.epay.merchant.exceptions.MerchantException;
import com.epay.merchant.model.request.AuthenticateUserRequest;
import com.epay.merchant.model.response.MerchantResponse;
import com.epay.merchant.service.MerchantLoginService;
import com.epay.merchant.util.ErrorConstants;
import com.epay.merchant.validatior.MerchantLoginValidation;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbi.epay.authentication.service.AuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class MerchantLoginServiceLoginUserTest {

    @Mock
    private MerchantLoginDao merchantLoginDao;

    @Mock
    private MerchantLoginValidation merchantLoginValidation;

    @Mock
    private AuthenticationService authService;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private CaptchaService captchaService;

    @InjectMocks
    private MerchantLoginService merchantLoginService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest
    @CsvSource({
        "validUser, validCaptcha, validPassword, true, true",   // Successful login
        "validUser, invalidCaptcha, validPassword, true, false", // Invalid captcha
        "invalidUser, validCaptcha, validPassword, false, false", // User not found
        "validUser, validCaptcha, invalidPassword, true, false"  // Invalid password
    })
    void testLoginUser(String username, String captchaText, String password, boolean userExists, boolean isCaptchaValid) {
        // Arrange
        AuthenticateUserRequest userRequest = new AuthenticateUserRequest();
        userRequest.setCaptchaText(captchaText);
        userRequest.setPassword(password);
        userRequest.setRequestId("REQ123");

        MerchantUserDto merchantUserDto = new MerchantUserDto();
        merchantUserDto.setUserName(username);
        merchantUserDto.setUserPassword("validPassword");
        merchantUserDto.setRole("ROLE_USER");

        MerchantUser merchantUser = new MerchantUser();
        merchantUser.setUserPassword("validPassword");

        if (userExists) {
            when(merchantLoginValidation.validate(username)).thenReturn("USERNAME");
            when(merchantLoginDao.findUserByUsername(username, "USERNAME")).thenReturn(merchantUserDto);
            when(objectMapper.convertValue(merchantUserDto, MerchantUser.class)).thenReturn(merchantUser);
        } else {
            when(merchantLoginValidation.validate(username)).thenReturn("USERNAME");
            when(merchantLoginDao.findUserByUsername(username, "USERNAME"))
                    .thenThrow(new MerchantException(ErrorConstants.NOT_FOUND_ERROR_CODE, "User not found"));
        }

        when(captchaService.isCaptchaValid("REQ123", captchaText)).thenReturn(isCaptchaValid);

        if (userExists && isCaptchaValid && "validPassword".equals(password)) {
            when(authService.generateUserToken(any())).thenReturn("generatedToken");
        }

        // Act & Assert
        if (userExists && isCaptchaValid && "validPassword".equals(password)) {
            MerchantResponse<String> response = merchantLoginService.loginUser(username, userRequest);

            // Verify success response
            assertNotNull(response);
            assertEquals(1, response.getStatus());
            assertTrue(response.getData().contains("generatedToken"));
        } else {
            MerchantException exception = assertThrows(
                MerchantException.class,
                () -> merchantLoginService.loginUser(username, userRequest)
            );

            // Verify exception details
            if (!isCaptchaValid) {
                assertEquals(ErrorConstants.ERROR_CAPTCHA_INVALID, exception.getCode());
            } else if (!userExists) {
                assertEquals(ErrorConstants.NOT_FOUND_ERROR_CODE, exception.getCode());
            } else if (!"validPassword".equals(password)) {
                assertEquals(ErrorConstants.ERROR_INVALID_CREDENTIALS, exception.getCode());
            }
        }

        // Verify mocked interactions
        verify(merchantLoginValidation, times(1)).validate(username);
        verify(captchaService, times(1)).isCaptchaValid("REQ123", captchaText);

        if (userExists) {
            verify(merchantLoginDao, times(1)).findUserByUsername(username, "USERNAME");
        } else {
            verify(merchantLoginDao, times(1)).findUserByUsername(username, "USERNAME");
        }
    }
}
