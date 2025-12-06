java.lang.IllegalArgumentException: Cannot construct instance of `com.epay.transaction.model.request.PaymentInitiationRequest` (although at least one Creator exists): no String-argument constructor/factory method to deserialize from String value ('{"operatingMode": "someMode", "payProcId": "proc123", "payProcType": "someType", "gtwMapsId": "gtw456", "pgBankCode": "HDFC", "merchPostedAmount": 100.00, "transactionAmount": 100.00, "upiAddress": "user@vpa", "channelBank": "HDFC", "altNumber": "111", "expiryMonth": "111", "expiryYear": "2026", "cvv": "877", "cardHolderName": "ganesh"}')
 at [Source: UNKNOWN; byte offset: #UNKNOWN]

    PaymentInitiationRequest paymentInitiationRequest = mapper.convertValue(paymentInitiationDto.getPaymentObject(), PaymentInitiationRequest.class);

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
