    public TransactionResponse<EncryptedResponse> initiatePayment() throws JsonProcessingException {
        logger.info("Payment Initiation processing request");
        EPayPrincipal ePayPrincipal = EPayIdentityUtil.getUserPrincipal();
        PaymentBookingDto paymentInitiationDto = paymentBookingDao.getPaymentBooking(ePayPrincipal.getOrderRef());
        String mek = paymentBookingDao.getMerchantMek();
        PaymentInitiationRequest paymentInitiationRequest = mapper.readValue(paymentInitiationDto.getPaymentObject(), PaymentInitiationRequest.class);
        OrderDto orderDto = mapper.readValue(paymentInitiationDto.getOrderObject(), OrderDto.class);
        EncryptedResponse encryptedResponse = paymentInitiationDao.processRequest(paymentInitiationRequest, paymentInitiationDto.getPayMode(), orderDto, mek);
        return TransactionResponse.<EncryptedResponse>builder().data(List.of(encryptedResponse)).status(TransactionConstant.RESPONSE_SUCCESS).build();
    }


@Data
@RequiredArgsConstructor
public class PaymentBookingDto {
    private UUID id;
    private String mId;
    private String sbiOrderRefNumber;
    private String orderHash;
    private String orderObject;
    private String paymentObject;
    private Long expiryTime;
    private String payMode;
}


@Data
@NoArgsConstructor
@AllArgsConstructor
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



in oracle database i inserted to the payment_object column as this object like this and type is CLOB
 '{"operatingMode": "someMode", "payProcId": "proc123", "payProcType": "someType", "gtwMapsId": "gtw456", "pgBankCode": "HDFC", "merchPostedAmount": 100.00, "transactionAmount": 100.00, "upiAddress": "user@vpa", "channelBank": "HDFC", "altNumber": "111", "expiryMonth": "111", "expiryYear": "2026", "cvv": "877", "cardHolderName": "ganesh"}'

 correct me why im getting this errror

 Unexpected character (''' (code 39)): expected a valid value (JSON String, Number, Array, Object or token 'null', 'true' or 'false')
