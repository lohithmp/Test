
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailDTO {
    private UUID id;
    private String orderRefNumber;
    private OrderStatus status;
    private String currencyCode;
}


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentVerificationDto extends OrderDetailDTO{
    private String atrnNumber;
    private double orderAmount;
    private double gstAmount;
    private double debitAmt;
    private int chargesAmount;
    private int totalAmount;
    private String payMode;
    private String transactionStatus;
    private String bankTxnNumber;
    private String processor;
}



@Service
@RequiredArgsConstructor
public class PaymentStatusVerificationService {
    private final OrderDao orderDao;
    private final TransactionDao transactionDao;



    public TransactionResponse<PaymentVerificationResponse> getPaymentStatusVerification(PaymentVerificationRequest paymentVerificationRequest) {
        MerchantDto merchantDto = getMerchantDto();

        PaymentVerificationResponse paymentVerificationResponse  = new PaymentVerificationResponse();
        return TransactionResponse.<PaymentVerificationResponse>builder()
                .data(List.of(paymentVerificationResponse))
                .status(1)
                .build();

    }

    private MerchantDto getMerchantDto() {
        EPayPrincipal ePayPrincipal = EPayIdentityUtil.getUserPrincipal();
        return orderDao.getActiveMerchantByMID(ePayPrincipal.getMid()).orElseThrow(() -> new TransactionException(ErrorConstants.NOT_FOUND_ERROR_CODE, MessageFormat.format(ErrorConstants.NOT_FOUND_ERROR_MESSAGE, "Valid Merchant")));
    }
}


@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

    // Corrected query method: 'status' instead of 'transactionStatus'
    Optional<Transaction> findBySbiOrderRefNumberAndStatus(String sbiOrderRefNumber, TransactionStatus status);

    @Query(value = """
            SELECT t.*, o.*
            FROM transaction t JOIN orders o
            ON t.order_ref_number = o.order_ref_number
            WHERE t.order_ref_number = :orderRefNumber AND (:sbiOrderRefNumber IS NULL OR t.sbi_order_ref_number) AND (:atrnNumber IS NULL OR t.atrn_num = :atrnNumber) AND o.order_amount = :orderAmount
            """, nativeQuery = true)
    Optional<Object[]> findAllTransactionAndOrderFields(@Param("orderRefNumber") String orderRefNumber, @Param("sbiOrderRefNumber") String sbiOrderRefNumber, @Param("atrnNumber") String atrnNumber, @Param("orderAmount") Double orderAmount);
}



@Component
@RequiredArgsConstructor
public class TransactionDao {

    private final TransactionRepository transactionRepository;
    private final ObjectMapper objectMapper;


    public PaymentVerificationDto getOrderAndTransactionByOrderRefNo(String orderRefNumber, String sbiOrderRefNumber, String atrnNumber, Double orderAmount) {
        Object[] response = new Optional[]{transactionRepository.findAllTransactionAndOrderFields(orderRefNumber, sbiOrderRefNumber, atrnNumber, orderAmount)};
        return objectMapper.convertValue(response, PaymentVerificationDto.class);
    }
}


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PaymentVerificationResponse {
    private OrderDto orderInfo;
    private List<TransactionDto> paymentInfo;
}
