    Optional<Order> findOrderByOrderAmountAndMIDAndOrderRefNumber(BigDecimal orderAmount, @Param("mID") String mID, String OrderRefNumber);

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


Caused by: org.hibernate.query.sqm.PathElementException: Could not resolve attribute 'MID' of 'com.epay.transaction.entity.Order'
Caused by: org.hibernate.query.sqm.PathElementException: Could not resolve attribute 'MID' of 'com.epay.transaction.entity.Order'
