package com.epay.transaction.service;

import com.epay.transaction.dao.CustomerDao;
import com.epay.transaction.dto.CustomerDto;
import com.epay.transaction.exceptions.TransactionException;
import com.epay.transaction.mapper.CustomerMapper;
import com.epay.transaction.model.request.CustomerRequest;
import com.epay.transaction.model.request.EncryptedRequest;
import com.epay.transaction.model.response.TransactionResponse;
import com.epay.transaction.util.EPayIdentityUtil;
import com.epay.transaction.util.TransactionConstant;
import com.epay.transaction.util.TransactionUtil;
import com.epay.transaction.util.enums.CustomerStatus;
import com.epay.transaction.validator.CustomerValidator;
import com.sbi.epay.authentication.model.EPayPrincipal;
import com.sbi.epay.encryptdecrypt.service.EncryptionService;
import com.sbi.epay.encryptdecrypt.util.enums.EncryptionDecryptionAlgo;
import com.sbi.epay.encryptdecrypt.util.enums.GCMIvLength;
import com.sbi.epay.encryptdecrypt.util.enums.GCMTagLength;
import com.sbi.epay.logging.utility.LoggerUtility;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.text.MessageFormat;

import static com.epay.transaction.util.TransactionConstant.VALID_CUSTOMER;
import static com.epay.transaction.util.TransactionErrorConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock private EPayPrincipal mockEPayPrincipal;
    @Mock private CustomerDao customerDao;
    @Mock private CustomerValidator customerValidator;
    @Mock private CustomerMapper customerMapper;
    @Mock private LoggerUtility logger;
    @Mock private Authentication authentication;
    @Mock private SecurityContext securityContext;

    @InjectMocks
    private CustomerService customerService;

    private EncryptedRequest testEncryptedRequest;
    private CustomerRequest testCustomerRequest;
    private CustomerDto testCustomerDto;
    private String testCustomerId;
    private String testCustomerStatus;
    private String testMek;
    private String testMerchantId;

    @BeforeEach
    void setUp() {
        testEncryptedRequest = new EncryptedRequest();
        testEncryptedRequest.setEncryptedRequest("encrypted-bin-check-data");

        testCustomerRequest = CustomerRequest.builder()
                .customerName("Test Customer")
                .email("test@example.com")
                .phoneNumber("1234567890")
                .build();

        testCustomerDto = CustomerDto.builder()
                .customerId("test-customer-id")
                .customerName("Test Customer")
                .email("test@example.com")
                .phoneNumber("1234567890")
                .status(CustomerStatus.ACTIVE)
                .build();

        testCustomerId = "test-customer-id";
        testCustomerStatus = "ACTIVE";
        testMek = "test-mek-key";
        testMerchantId = "test-merchant-id";
    }

    @AfterEach
    void tearDown() {
        // Clears any leftover static mocks
        Mockito.framework().clearInlineMocks();
        // Clears Spring Security context between tests
        SecurityContextHolder.clearContext();
    }

    @Test
    void createCustomer_Success() {
        try (MockedStatic<TransactionUtil> mockedTransactionUtil = mockStatic(TransactionUtil.class);
             MockedStatic<EncryptionService> mockedEncryptionService = mockStatic(EncryptionService.class)) {

            when(customerDao.getMerchantMek()).thenReturn(testMek);
            doNothing().when(customerValidator).validateCustomerRequest(testCustomerRequest);
            when(customerMapper.requestToDto(testCustomerRequest)).thenReturn(testCustomerDto);
            when(customerDao.saveCustomer(testCustomerDto)).thenReturn(testCustomerDto);

            mockedTransactionUtil.when(() ->
                    TransactionUtil.buildRequestByEncryptRequest(eq(testEncryptedRequest.getEncryptedRequest()), eq(testMek), eq(CustomerRequest.class)))
                    .thenReturn(testCustomerRequest);

            mockedEncryptionService.when(() ->
                    EncryptionService.encryptValueByStringKey(eq(testMek), any(String.class), any(EncryptionDecryptionAlgo.class),
                            any(GCMIvLength.class), any(GCMTagLength.class)))
                    .thenReturn("mockedEncryptedString");

            mockedTransactionUtil.when(() -> TransactionUtil.toJson(any(CustomerDto.class)))
                    .thenReturn("json-representation-of-customer-dto");

            TransactionResponse<String> result = customerService.createCustomer(testEncryptedRequest);

            assertNotNull(result);
            assertEquals(TransactionConstant.RESPONSE_SUCCESS, result.getStatus());
            assertEquals(1, result.getData().size());
            assertNotNull(result.getData().getFirst());

            verify(customerDao).getMerchantMek();
            verify(customerValidator).validateCustomerRequest(testCustomerRequest);
            verify(customerMapper).requestToDto(testCustomerRequest);
            verify(customerDao).saveCustomer(testCustomerDto);
        }
    }

    @Test
    void createCustomer_WithNullEncryptedRequest() {
        EncryptedRequest nullRequest = null;
        when(customerDao.getMerchantMek()).thenReturn(testMek);

        assertThrows(NullPointerException.class, () ->
                customerService.createCustomer(nullRequest));
    }

    @Test
    void createCustomer_ValidatorThrowsException() {
        when(customerDao.getMerchantMek()).thenReturn(testMek);

        try (MockedStatic<TransactionUtil> mockedTransactionUtil = mockStatic(TransactionUtil.class)) {
            mockedTransactionUtil.when(() ->
                    TransactionUtil.buildRequestByEncryptRequest(eq(testEncryptedRequest.getEncryptedRequest()), eq(testMek), eq(CustomerRequest.class)))
                    .thenReturn(testCustomerRequest);

            doThrow(new RuntimeException("Validation failed"))
                    .when(customerValidator).validateCustomerRequest(testCustomerRequest);

            assertThrows(RuntimeException.class, () ->
                    customerService.createCustomer(testEncryptedRequest));

            verify(customerDao).getMerchantMek();
            verify(customerValidator).validateCustomerRequest(testCustomerRequest);
            verify(customerMapper, never()).requestToDto(any());
        }
    }

    @Test
    void getCustomerByCustomerId_Success() {
        try (MockedStatic<EPayIdentityUtil> mockedEPayIdentityUtil = mockStatic(EPayIdentityUtil.class);
             MockedStatic<EncryptionService> mockedEncryptionService = mockStatic(EncryptionService.class)) {

            SecurityContextHolder.getContext().setAuthentication(authentication);
            mockedEPayIdentityUtil.when(EPayIdentityUtil::getUserPrincipal).thenReturn(mockEPayPrincipal);
            when(mockEPayPrincipal.getMId()).thenReturn("1234");

            mockedEncryptionService.when(() ->
                    EncryptionService.encryptValueByStringKey(anyString(), anyString(),
                            any(EncryptionDecryptionAlgo.class), any(GCMIvLength.class), any(GCMTagLength.class)))
                    .thenReturn("mockedEncryptedString");

            when(customerDao.getCustomerByCustomerId("1234", testCustomerId)).thenReturn(testCustomerDto);
            when(customerDao.getMerchantMek()).thenReturn(testMek);

            TransactionResponse<String> result = customerService.getCustomerByCustomerId(testCustomerId);

            assertNotNull(result);
            assertEquals(TransactionConstant.RESPONSE_SUCCESS, result.getStatus());
            assertEquals(1, result.getData().size());
            assertNotNull(result.getData().getFirst());

            verify(customerValidator).validateCustomerId(testCustomerId);
            verify(customerDao).getCustomerByCustomerId("1234", testCustomerId);
            verify(customerDao).getMerchantMek();
        }
    }

    // ... (repeat same cleanup for all other tests, always inside try-with-resources for static mocks)

}
