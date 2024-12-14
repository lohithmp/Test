    Optional<Order> findOrderByOrderAmountAndMIDAndOrderRefNumber(BigDecimal orderAmount, @Param("merchant_id") String mID, String OrderRefNumber);

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false, unique = true)
    private UUID id;

    @Column(name = "merchant_id")
    private String mID;

    @Column(name = "customer_id")
    private String customerId;

    @Column(name = "currency_code")
    private String currencyCode;

    @Column(name = "order_amount")
    private BigDecimal orderAmount;

    @Column(name = "order_ref_number")
    private String orderRefNumber;

    @Column(name = "sbi_order_ref_number")
    private String sbiOrderRefNumber;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name = "other_details", columnDefinition = "CLOB")
    private String otherDetails;
    private Long expiry;

    @Column(name = "multi_accounts", columnDefinition = "CLOB")
    private String multiAccounts;

    @Column(name = "payment_mode")
    private String paymentMode;

    @Column(name = "order_hash")
    private String orderHash;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_by")
    private String updatedBy;

    @CreatedDate
    @Column(name = "created_date")
    private Long createdDate;

    @Column(name = "updated_date")
    private Long updatedDate;

    @Column(name = "return_url")
    private String returnUrl;

}

Caused by: org.springframework.beans.factory.UnsatisfiedDependencyException: Error creating bean with name 'customerService' defined in file [C:\Users\V1014352\Epay\epay_transaction_service\build\classes\java\main\com\epay\transaction\service\CustomerService.class]: Unsatisfied dependency expressed through constructor parameter 1: Error creating bean with name 'customerValidator' defined in file [C:\Users\V1014352\Epay\epay_transaction_service\build\classes\java\main\com\epay\transaction\validator\CustomerValidator.class]: Unsatisfied dependency expressed through constructor parameter 1: Error creating bean with name 'tokenDao' defined in file [C:\Users\V1014352\Epay\epay_transaction_service\build\classes\java\main\com\epay\transaction\dao\TokenDao.class]: Unsatisfied dependency expressed through constructor parameter 0: Error creating bean with name 'orderRepository' defined in com.epay.transaction.repository.OrderRepository defined in @EnableJpaRepositories declared on EpayTransactionServiceApplication: Could not create query for public abstract java.util.Optional com.epay.transaction.repository.OrderRepository.findOrderByOrderAmountAndMIDAndOrderRefNumber(java.math.BigDecimal,java.lang.String,java.lang.String); Reason: Failed to create query for method public abstract java.util.Optional com.epay.transaction.repository.OrderRepository.findOrderByOrderAmountAndMIDAndOrderRefNumber(java.math.BigDecimal,java.lang.String,java.lang.String); Could not resolve attribute 'MID' of 'com.epay.transaction.entity.Order'


Caused by: org.hibernate.query.sqm.PathElementException: Could not resolve attribute 'MID' of 'com.epay.transaction.entity.Order'
Caused by: org.hibernate.query.sqm.PathElementException: Could not resolve attribute 'MID' of 'com.epay.transaction.entity.Order'
