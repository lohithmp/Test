@Test
void getCaptchaFailureGenerateCaptchaText() throws NoSuchFieldException, IllegalAccessException {
    // Set CAPTCHA_VALIDITY using reflection
    Field field = CaptchaService.class.getDeclaredField("CAPTCHA_VALIDITY");
    field.setAccessible(true);
    field.set(captchaService, 60000L); // 60 seconds validity

    // Create a spy for the CaptchaService
    CaptchaService spyCaptchaService = spy(captchaService);

    // Mock the generateCaptchaText method to throw an exception
    doThrow(new RuntimeException("Failed to generate CAPTCHA text")).when(spyCaptchaService).generateCaptchaText();

    String requestType = "requestType";

    Exception exception = assertThrows(RuntimeException.class, () -> {
        spyCaptchaService.getCaptcha(requestType);
    });

    assertEquals("Failed to generate CAPTCHA text", exception.getMessage());

    // Verify that generateCaptchaText was called
    verify(spyCaptchaService, times(1)).generateCaptchaText();
    verify(spyCaptchaService, never()).generateCaptchaImage(anyString());
}
