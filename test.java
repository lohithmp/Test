package com.epay.merchant.service;

import com.epay.merchant.dao.MerchantLoginDao;
import com.epay.merchant.dto.MerchantUserDto;
import com.epay.merchant.entity.MerchantUser;
import com.epay.merchant.exceptions.MerchantException;
import com.epay.merchant.model.request.AuthenticateUserRequest;
import com.epay.merchant.model.response.MerchantResponse;
import com.epay.merchant.util.ErrorConstants;
import com.epay.merchant.validatior.MerchantLoginValidation;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbi.epay.authentication.service.AuthenticationService;
import com.sbi.epay.authentication.model.UserTokenRequest;
import com.sbi.epay.authentication.util.enums.TokenType;
import com.sbi.epay.captcha.service.CaptchaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MerchantLoginServiceTest {

    @InjectMocks
    private MerchantLoginService merchantLoginService;

    @Mock
    private MerchantLoginDao merchantLoginDao;

    @Mock
    private MerchantLoginValidation merchantLoginValidation;

    @Mock
    private AuthenticationService authService;

    @Mock
    private CaptchaService captchaService;

    @Mock
    private ObjectMapper objectMapper;

    private static final String VALID_USERNAME = "testUser";
    private static final String VALID_PASSWORD = "password123";
    private static final String VALID_TOKEN = "sampleToken";

    private MerchantUserDto merchantUserDto;
    private MerchantUser merchantUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        merchantUserDto = new MerchantUserDto();
        merchantUserDto.setUserName(VALID_USERNAME);
        merchantUserDto.setUserPassword(VALID_PASSWORD);
        merchantUserDto.setRole("USER");
        merchantUserDto.setMid("123456");

        merchantUser = new MerchantUser();
        merchantUser.setUserName(VALID_USERNAME);
        merchantUser.setUserPassword(VALID_PASSWORD);
    }

    @Test
    void loginUser_Success() {
        // Mocking dependencies
        when(merchantLoginValidation.validate(VALID_USERNAME)).thenReturn("USERNAME");
        when(captchaService.isCaptchaValid(anyString(), anyString())).thenReturn(true);
        when(merchantLoginDao.findUserByUsername(anyString(), anyString())).thenReturn(merchantUserDto);
        when(objectMapper.convertValue(merchantUserDto, MerchantUser.class)).thenReturn(merchantUser);
        when(authService.generateUserToken(any(UserTokenRequest.class))).thenReturn(VALID_TOKEN);

        AuthenticateUserRequest userRequest = new AuthenticateUserRequest();
        userRequest.setRequestId("12345");
        userRequest.setCaptchaText("validCaptcha");
        userRequest.setPassword(VALID_PASSWORD);

        MerchantResponse<String> response = merchantLoginService.loginUser(VALID_USERNAME, userRequest);

        // Assertions
        assertNotNull(response);
        assertEquals(1, response.getStatus());
        assertEquals(VALID_TOKEN, response.getData().get(0));

        // Verifying method calls
        verify(merchantLoginValidation).validate(VALID_USERNAME);
        verify(captchaService).isCaptchaValid(anyString(), anyString());
        verify(merchantLoginDao).findUserByUsername(anyString(), anyString());
        verify(authService).generateUserToken(any(UserTokenRequest.class));
    }

    @Test
    void loginUser_InvalidCaptcha() {
        when(captchaService.isCaptchaValid(anyString(), anyString())).thenReturn(false);

        AuthenticateUserRequest userRequest = new AuthenticateUserRequest();
        userRequest.setRequestId("12345");
        userRequest.setCaptchaText("invalidCaptcha");
        userRequest.setPassword(VALID_PASSWORD);

        MerchantException exception = assertThrows(MerchantException.class, () ->
                merchantLoginService.loginUser(VALID_USERNAME, userRequest)
        );

        assertEquals(ErrorConstants.ERROR_CAPTCHA_INVALID, exception.getCode());

        verify(captchaService).isCaptchaValid(anyString(), anyString());
        verifyNoInteractions(merchantLoginDao);
        verifyNoInteractions(authService);
    }

    @Test
    void loginUser_InvalidCredentials() {
        when(merchantLoginValidation.validate(VALID_USERNAME)).thenReturn("USERNAME");
        when(captchaService.isCaptchaValid(anyString(), anyString())).thenReturn(true);
        when(merchantLoginDao.findUserByUsername(anyString(), anyString())).thenReturn(merchantUserDto);
        when(objectMapper.convertValue(merchantUserDto, MerchantUser.class)).thenReturn(merchantUser);

        AuthenticateUserRequest userRequest = new AuthenticateUserRequest();
        userRequest.setRequestId("12345");
        userRequest.setCaptchaText("validCaptcha");
        userRequest.setPassword("wrongPassword");

        MerchantException exception = assertThrows(MerchantException.class, () ->
                merchantLoginService.loginUser(VALID_USERNAME, userRequest)
        );

        assertEquals(ErrorConstants.ERROR_INVALID_CREDENTIALS, exception.getCode());

        verify(merchantLoginValidation).validate(VALID_USERNAME);
        verify(captchaService).isCaptchaValid(anyString(), anyString());
        verify(merchantLoginDao).findUserByUsername(anyString(), anyString());
    }
}
