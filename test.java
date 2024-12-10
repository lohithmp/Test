@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private static final LoggerUtility logger = LoggerFactoryUtility.getLogger(AuthenticationService.class);
    private final JwtService jwtService;

    /**
     * generateToken() generates token
     *
     * @param accessTokenRequest Authentication
     * @return JWT token in string form.
     */
    public String generateAccessToken(AccessTokenRequest accessTokenRequest) {
        logger.debug("Initiate generateAccessToken for {}", accessTokenRequest);
        AuthRequestValidator.validAuthRequest(accessTokenRequest);
        return jwtService.generateAccessToken(accessTokenRequest);
    }
