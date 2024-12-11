
@RequiredArgsConstructor
@Service
public class OtpService {

    private static final LoggerUtility log = LoggerFactoryUtility.getLogger(OtpController.class);
    private final OtpDao otpDao;

    public MerchantResponse<OtpResponse> generateOTP(String userName) {
        log.info(" generating OTP for User :: "+userName);
        OtpDto otpDto = generaterandomOtp(userName);
        log.info(" saving otp in database ");
        otpDto = otpDao.saveOtp(otpDto);
        log.info(" otp saved Successfully ");
        return MerchantResponse.<OtpResponse>builder()
               .data(List.of(OtpResponse.builder()
                       .requestId(UUID.randomUUID())
                       .otpDescription("OTP successfully generated for user: "+userName)
                       .otp(otpDto.getOtp())
                       .build()))
               .status(1).build();
    }

    private OtpDto generaterandomOtp(String userName) {
        log.info(" Generating Random Otp ");
        // Generate OTP (random number between 100000 and 999999)
        int otp = new Random().nextInt(900000) + 100000;
        // Set validity (5 minutes from now)
        LocalDateTime validity = LocalDateTime.now().plusMinutes(5);
       return  OtpDto.builder()
               .userName(userName)
               .otp(otp)
               .status("G")
               .validity(validity)
               .createdAt(LocalDateTime.now())
               .build();
    }

}
