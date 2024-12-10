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
    userRequest.setRequestId(UUID.randomUUID()); // Simulating a valid UUID

    MerchantUserDto merchantUserDto = new MerchantUserDto();
    merchantUserDto.setUserName(username);
    merchantUserDto.setUserPassword("validPassword");

    MerchantUser merchantUser = new MerchantUser();
    merchantUser.setUserPassword("validPassword");

    // Mocking behaviors
    when(merchantLoginValidation.validate(username)).thenReturn("USERNAME");
    when(captchaService.isCaptchaValid(anyString(), eq(captchaText))).thenReturn(isCaptchaValid);

    if (userExists) {
        when(merchantLoginDao.findUserByUsername(username, "USERNAME")).thenReturn(merchantUserDto);
        when(objectMapper.convertValue(merchantUserDto, MerchantUser.class)).thenReturn(merchantUser);
    } else {
        when(merchantLoginDao.findUserByUsername(username, "USERNAME"))
                .thenThrow(new MerchantException(ErrorConstants.NOT_FOUND_ERROR_CODE, "User not found"));
    }

    if (userExists && isCaptchaValid && "validPassword".equals(password)) {
        when(authService.generateUserToken(any())).thenReturn("generatedToken");
    }

    // Act & Assert
    if (userExists && isCaptchaValid && "validPassword".equals(password)) {
        // Successful login scenario
        MerchantResponse<String> response = merchantLoginService.loginUser(username, userRequest);

        // Verify success response
        assertNotNull(response);
        assertEquals(1, response.getStatus());
        assertTrue(response.getData().contains("generatedToken"));

        // Verify that DAO was called
        verify(merchantLoginDao, times(1)).findUserByUsername(username, "USERNAME");
    } else {
        // Failure scenarios
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

        // Verify that DAO is not called if captcha is invalid
        if (!isCaptchaValid) {
            verify(merchantLoginDao, times(0)).findUserByUsername(anyString(), anyString());
        } else {
            // Verify that DAO was called for valid captcha
            verify(merchantLoginDao, times(userExists ? 1 : 0)).findUserByUsername(username, "USERNAME");
        }
    }

    // Always verify captcha service
    verify(captchaService, times(1)).isCaptchaValid(anyString(), eq(captchaText));
}
