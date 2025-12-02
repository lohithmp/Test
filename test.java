Cannot construct instance of `com.epay.transaction.model.request.PaymentInitiationRequest` (although at least one Creator exists): no String-argument constructor/factory method to deserialize from String value ('{"operatingMode": "someMode", "payProcId": "proc123", "payProcType": "someType", "gtwMapsId": "gtw456", "pgBankCode": "HDFC", "merchPostedAmount": 100.00, "transactionAmount": 100.00, "upiAddress": "user@vpa", "channelBank": "HDFC", "altNumber": "434", "expiryMonth": "ddf", "expiryYear": "9829", "cvv": "765", "cardHolderName": "1234"}')


    "{\"operatingMode\": \"someMode\", \"payProcId\": \"proc123\", \"payProcType\": \"someType\", \"gtwMapsId\": \"gtw456\", \"pgBankCode\": \"HDFC\", \"merchPostedAmount\": 100.00, \"transactionAmount\": 100.00, \"upiAddress\": \"user@vpa\", \"channelBank\": \"HDFC\", \"altNumber\": \"434\", \"expiryMonth\": \"ddf\", \"expiryYear\": \"9829\", \"cvv\": \"765\", \"cardHolderName\": \"1234\"}"

      */
    public static <T> T buildRequestByEncryptRequest(String encryptedRequest, String key, Class<T> clazz) {
        logger.info("Request for decryption");
        String decryptedRequest = EncryptionDecryptionUtil.decryptValue(key, encryptedRequest);
        try {
            return objectMapper.readValue(decryptedRequest, clazz);
        } catch (JsonProcessingException e) {
            logger.error("error while parsing request of {}, error {}", clazz.getName(), e.getMessage());
            throw new TransactionException(INVALID_ERROR_CODE, MessageFormat.format(INVALID_ERROR_MESSAGE, "Request object", getParsingError(e)));
        }
    }


@Data
public class PaymentInitiationRequest {

    private String operatingMode;

    private String payProcId;

    private String payProcType;

    private String gtwMapsId;

    private String pgBankCode;

    private BigDecimal merchPostedAmount;

    private BigDecimal transactionAmount;

    private String upiAddress;

    @NotBlank(message = CHANNEL_BANK_IS_REQUIRED)
    private String channelBank;

    private String altNumber;

    private String expiryMonth;

    private String expiryYear;

    private String cvv;

    private String cardHolderName;

}
