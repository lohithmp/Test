
@Service
@RequiredArgsConstructor
public class CaptchaService {

    private static final LoggerUtility log = LoggerFactoryUtility.getLogger(CaptchaService.class);
    private final CaptchaDao captchaDao;

    @Value("${captcha.validity}")
    private  Long CAPTCHA_VALIDITY;

    public MerchantResponse<CaptchaResponse> generateCaptcha(String requestType) {
        log.info(" generate captcha service ");
        CaptchaDto captchaDto = getCaptcha(requestType);
        captchaDto  = captchaDao.saveCaptcha(captchaDto);
    return  MerchantResponse.<CaptchaResponse>builder()
                .data(List.of(CaptchaResponse.builder()
                        .requestId(captchaDto.getRequestId())
                        .captchaImageBase64(Base64.getEncoder().encodeToString(captchaDto.getCaptchaImage()).getBytes())
                        .captchaText(captchaDto.getCaptchaText())
                        .build()))
                .status(1).build();
    }

    public boolean isCaptchaValid(UUID requestId, String captchaText) {
        return captchaDao.isCaptchaValid(requestId,captchaText);
    }


    public CaptchaDto getCaptcha(String requestType) {
        UUID requestId = UUID.randomUUID();
        String captchaText = generateCaptchaText();  // Implement this method for generating CAPTCHA text
        byte[] captchaImage = generateCaptchaImage(captchaText);  // Implement image generation
        Long validity = System.currentTimeMillis() + CAPTCHA_VALIDITY; // Example: 10 minutes validity
       return CaptchaDto.builder()
               .requestId(requestId)
               .requestType(requestType)
               .captchaText(captchaText)
               .captchaImage(captchaImage)
               .validity(validity)
               .status(CaptchaStatus.G).
                build();
    }

    private String generateCaptchaText() {
        // Logic for generating random CAPTCHA text (e.g., alphanumeric string)
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder captchaText = new StringBuilder();

        // Generate 6-character random alphanumeric string
        for (int i = 0; i < 6; i++) {
            int index = random.nextInt(characters.length());
            captchaText.append(characters.charAt(index));
        }
        return captchaText.toString();
    }

    private byte[] generateCaptchaImage(String captchaText)  {
        // Logic to generate CAPTCHA image and convert to byte array (could use a library like JCaptcha)
        int width = 200;
        int height = 50;
        // Create an image buffer
        BufferedImage captchaImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = captchaImage.createGraphics();
        // Set background and text color
        g2d.setColor(Color.WHITE);  // Background color
        g2d.fillRect(0, 0, width, height);  // Fill the image with white
        g2d.setColor(Color.BLACK);  // Text color
        g2d.setFont(new Font("Arial", Font.BOLD, 40));  // Set font for the text
        // Draw the CAPTCHA text in the center
        g2d.drawString(captchaText, 30, 40);
        // Optionally, add some noise or distortion to make it harder to read for bots
        g2d.setColor(Color.GRAY);
        for (int i = 0; i < 50; i++) {
            int x1 = (int) (Math.random() * width);
            int y1 = (int) (Math.random() * height);
            int x2 = (int) (Math.random() * width);
            int y2 = (int) (Math.random() * height);
            g2d.drawLine(x1, y1, x2, y2);
        }
        g2d.dispose();  // Free up resources
        // Convert image to byte array
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            ImageIO.write(captchaImage, "png", baos);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            baos.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        byte[] imageInByte = baos.toByteArray();
        try {
            baos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return imageInByte;
        //return new byte[0]; // Placeholder
    }


}
