
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
//        captchaService.CAPTCHA_VALIDITY = 60000L;
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
