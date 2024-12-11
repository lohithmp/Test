
@ExtendWith(MockitoExtension.class)
public class CaptchaServiceTest {
    @Mock
    CaptchaDao captchaDao;

    @InjectMocks
    CaptchaService captchaService;

    @BeforeEach
    void setup() throws NoSuchFieldException, IllegalAccessException {
        MockitoAnnotations.openMocks(this);
        Field field = CaptchaService.class.getDeclaredField("CAPTCHA_VALIDITY");
        field.setAccessible(true);
        field.set(captchaService, 60000L);
    }

    @Test
    void generateCaptchaSuccess() {
        UUID requestId = UUID.randomUUID();
        String requestType = "requestType";
        String captchaText = "captchaText";
        byte[] captchaImage = "mockImage".getBytes();
        CaptchaDto captchaDto = CaptchaDto.builder()
                .requestId(requestId)
                .requestType(requestType)
                .captchaImage(captchaImage)
                .captchaText(captchaText)
                .validity(System.currentTimeMillis())
                .status(CaptchaStatus.G).build();

        when(captchaDao.saveCaptcha(any(CaptchaDto.class))).thenReturn(captchaDto);

        MerchantResponse<CaptchaResponse> merchantResponse = captchaService.generateCaptcha(requestType);
        assertNotNull(merchantResponse);
        assertEquals(1, merchantResponse.getData().size());

        CaptchaResponse captchaResponse = merchantResponse.getData().get(0);

        assertEquals(captchaText, captchaResponse.getCaptchaText());

        verify(captchaDao, times(1)).saveCaptcha(any(CaptchaDto.class));
    }
}

Cannot invoke "com.epay.merchant.dto.CaptchaDto.getRequestId()" because "captchaDto" is null
java.lang.NullPointerException: Cannot invoke "com.epay.merchant.dto.CaptchaDto.getRequestId()" because "captchaDto" is null
	at com.epay.merchant.service.CaptchaService.generateCaptcha(CaptchaService.java:50)
	at com.epay.merchant.service.CaptchaServiceTest.generateCaptchaSuccess(CaptchaServiceTest.java:70)
	at java.base/java.lang.reflect.Method.invoke(Method.java:580)
	at java.base/java.util.ArrayList.forEach(ArrayList.java:1596)
	at java.base/java.util.ArrayList.forEach(ArrayList.java:1596)


OpenJDK 64-Bit Server VM warning: Sharing is only supported for boot loader classes because bootstrap classpath has been appended
