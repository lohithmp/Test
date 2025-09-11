createCustomer_Success()
org.mockito.exceptions.base.MockitoException: 
For com.epay.transaction.util.EncryptionDecryptionUtil, static mocking is already registered in the current thread

To create a new mock, the existing static mock registration must be deregistered
	at app//com.epay.transaction.service.CustomerServiceTest.setUp(CustomerServiceTest.java:80)
	at java.base@21.0.3/java.lang.reflect.Method.invoke(Method.java:580)
	at java.base@21.0.3/java.util.ArrayList.forEach(ArrayList.java:1596)
	at java.base@21.0.3/java.util.ArrayList.forEach(ArrayList.java:1596)
	Suppressed: java.lang.NullPointerException: Cannot invoke "org.mockito.MockedStatic.close()" because "this.mockedEncryptionDecryptionUtil" is null
		at com.epay.transaction.service.CustomerServiceTest.tearDown(CustomerServiceTest.java:100)
		... 3 more





@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    EPayPrincipal mockEPayPrincipal;
    @Mock
    private CustomerDao customerDao;
    @Mock
    private CustomerValidator customerValidator;
    @Mock
    private CustomerMapper customerMapper;
    @Mock
    private LoggerUtility logger;
    @Mock
    private Authentication authentication;
    @Mock
    private SecurityContext securityContext;
    @InjectMocks
    private CustomerService customerService;
    private EncryptedRequest testEncryptedRequest;
    private CustomerRequest testCustomerRequest;
    private CustomerDto testCustomerDto;
    private String testCustomerId;
    private String testCustomerStatus;
    private String testMek;
    private String testMerchantId;

    private MockedStatic<TransactionUtil> mockedTransactionUtil;
    private MockedStatic<EncryptionDecryptionUtil> mockedEncryptionDecryptionUtil;
    private MockedStatic<EncryptionService> mockedEncryptionService;

    @BeforeEach
    void setUp() {
        mockedTransactionUtil = mockStatic(TransactionUtil.class);
        mockedEncryptionDecryptionUtil = mockStatic(EncryptionDecryptionUtil.class);
        mockedEncryptionService = mockStatic(EncryptionService.class);


        testEncryptedRequest = new EncryptedRequest();
        testEncryptedRequest.setEncryptedRequest("encrypted-bin-check-data");

        testCustomerRequest = CustomerRequest.builder().customerName("Test Customer").email("test@example.com").phoneNumber("1234567890").build();

        testCustomerDto = CustomerDto.builder().customerId("test-customer-id").customerName("Test Customer").email("test@example.com").phoneNumber("1234567890").status(CustomerStatus.ACTIVE).build();

        testCustomerId = "test-customer-id";
        testCustomerStatus = "ACTIVE";
        testMek = "test-mek-key";
        testMerchantId = "test-merchant-id";
    }

    @AfterEach
    void tearDown() {
        mockedTransactionUtil.close();
        mockedEncryptionDecryptionUtil.close();
        mockedEncryptionService.close();
    }

    @Test
    void createCustomer_Success() {


        when(customerDao.getMerchantMek()).thenReturn(testMek);
        doNothing().when(customerValidator).validateCustomerRequest(testCustomerRequest);
        when(customerMapper.requestToDto(testCustomerRequest)).thenReturn(testCustomerDto);
        when(customerDao.saveCustomer(testCustomerDto)).thenReturn(testCustomerDto);

        mockedTransactionUtil.when(() -> TransactionUtil.buildRequestByEncryptRequest(eq(testEncryptedRequest.getEncryptedRequest()), eq(testMek), eq(CustomerRequest.class))).thenReturn(testCustomerRequest);

        mockedEncryptionService.when(() -> EncryptionService.encryptValueByStringKey(eq(testMek), any(String.class), any(EncryptionDecryptionAlgo.class), any(GCMIvLength.class), any(GCMTagLength.class))).thenReturn("mockedEncryptedString");

        mockedEncryptionDecryptionUtil.when(() -> EncryptionDecryptionUtil.encryptValue(eq(testMek), any(String.class))).thenReturn("mockedEncryptedString");

        mockedTransactionUtil.when(() -> TransactionUtil.toJson(any(CustomerDto.class))).thenReturn("json-representation-of-customer-dto");

        // Act
        TransactionResponse<String> result = customerService.createCustomer(testEncryptedRequest);

        // Assert
        assertNotNull(result);
        assertEquals(TransactionConstant.RESPONSE_SUCCESS, result.getStatus());
        assertNotNull(result.getData());
        assertEquals(1, result.getData().size());
        assertNotNull(result.getData().get(0));

        verify(customerDao, times(1)).getMerchantMek();
        verify(customerValidator, times(1)).validateCustomerRequest(testCustomerRequest);
        verify(customerMapper, times(1)).requestToDto(testCustomerRequest);
        verify(customerDao, times(1)).saveCustomer(testCustomerDto);

    }
}
