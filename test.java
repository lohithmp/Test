package com.epay.transaction.service;

import com.epay.transaction.dao.CustomerDao;
import com.epay.transaction.dto.CustomerDto;
import com.epay.transaction.exceptions.TransactionException;
import com.epay.transaction.mapper.CustomerMapper;
import com.epay.transaction.model.request.CustomerRequest;
import com.epay.transaction.model.request.EncryptedRequest;
import com.epay.transaction.model.response.TransactionResponse;
import com.epay.transaction.util.EPayIdentityUtil;
import com.epay.transaction.util.EncryptionDecryptionUtil;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
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

    @Mock
    EPayPrincipal mockEPayPrincipal = mock(EPayPrincipal.class);
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

    @BeforeEach
    void setUp() {
        testEncryptedRequest = new EncryptedRequest();
        testEncryptedRequest.setEncryptedRequest("encrypted-bin-check-data");

        testCustomerRequest = CustomerRequest.builder().customerName("Test Customer").email("test@example.com").phoneNumber("1234567890").build();

        testCustomerDto = CustomerDto.builder().customerId("test-customer-id").customerName("Test Customer").email("test@example.com").phoneNumber("1234567890").status(CustomerStatus.ACTIVE).build();

        testCustomerId = "test-customer-id";
        testCustomerStatus = "ACTIVE";
        testMek = "test-mek-key";
        testMerchantId = "test-merchant-id";
//        try (MockedStatic<EncryptionDecryptionUtil> mockedEncryptionDecryptionUtil = mockStatic(EncryptionDecryptionUtil.class)) {
//            mockedEncryptionDecryptionUtil.when(() -> EncryptionDecryptionUtil.encryptValue(eq(testMek), any(String.class))).thenReturn("mockedEncryptedString");
//        }

    }

    @Test
    void createCustomer_Success() {

        try (MockedStatic<TransactionUtil> mockedTransactionUtil = mockStatic(TransactionUtil.class);
             MockedStatic<EncryptionService> mockedEncryptionService = mockStatic(EncryptionService.class)) {

            when(customerDao.getMerchantMek()).thenReturn(testMek);
            doNothing().when(customerValidator).validateCustomerRequest(testCustomerRequest);
            when(customerMapper.requestToDto(testCustomerRequest)).thenReturn(testCustomerDto);
            when(customerDao.saveCustomer(testCustomerDto)).thenReturn(testCustomerDto);

            mockedTransactionUtil.when(() -> TransactionUtil.buildRequestByEncryptRequest(eq(testEncryptedRequest.getEncryptedRequest()), eq(testMek), eq(CustomerRequest.class))).thenReturn(testCustomerRequest);

            mockedEncryptionService.when(() -> EncryptionService.encryptValueByStringKey(eq(testMek), any(String.class), any(EncryptionDecryptionAlgo.class), any(GCMIvLength.class), any(GCMTagLength.class))).thenReturn("mockedEncryptedString");


            mockedTransactionUtil.when(() -> TransactionUtil.toJson(any(CustomerDto.class))).thenReturn("json-representation-of-customer-dto");

            // Act
            TransactionResponse<String> result = customerService.createCustomer(testEncryptedRequest);

            // Assert
            assertNotNull(result);
            assertEquals(TransactionConstant.RESPONSE_SUCCESS, result.getStatus());
            assertNotNull(result.getData());
            assertEquals(1, result.getData().size());
            assertNotNull(result.getData().getFirst());

            verify(customerDao, times(1)).getMerchantMek();
            verify(customerValidator, times(1)).validateCustomerRequest(testCustomerRequest);
            verify(customerMapper, times(1)).requestToDto(testCustomerRequest);
            verify(customerDao, times(1)).saveCustomer(testCustomerDto);
        }
    }

    @Test
    void createCustomer_WithNullEncryptedRequest() {
        // Arrange
        EncryptedRequest nullRequest = null;
        when(customerDao.getMerchantMek()).thenReturn(testMek);

        // Act & Assert
        assertThrows(NullPointerException.class, () -> {
            customerService.createCustomer(nullRequest);
        });
    }
    @Test
    void createCustomer_WithNullEncryptedRequestt() {
        // Arrange
        EncryptedRequest nullRequest = null;
        when(customerDao.getMerchantMek()).thenReturn(testMek);

        // Act & Assert
        assertThrows(NullPointerException.class, () -> {
            customerService.createCustomer(nullRequest);
        });
    }


    @Test
    void createCustomer_ValidatorThrowsException() {
        // Arrange
        when(customerDao.getMerchantMek()).thenReturn(testMek);

        try (MockedStatic<TransactionUtil> mockedTransactionUtil = mockStatic(TransactionUtil.class)) {

            mockedTransactionUtil.when(() -> TransactionUtil.buildRequestByEncryptRequest(eq(testEncryptedRequest.getEncryptedRequest()), eq(testMek), eq(CustomerRequest.class))).thenReturn(testCustomerRequest);

            doThrow(new RuntimeException("Validation failed")).when(customerValidator).validateCustomerRequest(testCustomerRequest);

            // Act & Assert
            assertThrows(RuntimeException.class, () -> {
                customerService.createCustomer(testEncryptedRequest);
            });

            verify(customerDao, times(1)).getMerchantMek();
            verify(customerValidator, times(1)).validateCustomerRequest(testCustomerRequest);
            verify(customerMapper, never()).requestToDto(any());
        }
    }

    @Test
    void createCustomer_GetMekThrowsException() {
        // Arrange
        when(customerDao.getMerchantMek()).thenThrow(new RuntimeException("Failed to get MEK"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            customerService.createCustomer(testEncryptedRequest);
        });

        verify(customerDao, times(1)).getMerchantMek();
        verify(customerValidator, never()).validateCustomerRequest(any());
    }

    @Test
    void createCustomer_SaveCustomerThrowsException() {
        // Arrange
        when(customerDao.getMerchantMek()).thenReturn(testMek);
        try (MockedStatic<TransactionUtil> mockedTransactionUtil = mockStatic(TransactionUtil.class)) {
            mockedTransactionUtil.when(() -> TransactionUtil.buildRequestByEncryptRequest(eq(testEncryptedRequest.getEncryptedRequest()), eq(testMek), eq(CustomerRequest.class))).thenReturn(testCustomerRequest);

            when(customerMapper.requestToDto(testCustomerRequest)).thenReturn(testCustomerDto);
            when(customerDao.saveCustomer(testCustomerDto)).thenThrow(new RuntimeException("Failed to save customer"));

            // Act & Assert
            assertThrows(RuntimeException.class, () -> {
                customerService.createCustomer(testEncryptedRequest);
            });

            verify(customerDao, times(1)).getMerchantMek();
            verify(customerValidator, times(1)).validateCustomerRequest(testCustomerRequest);
            verify(customerMapper, times(1)).requestToDto(testCustomerRequest);
            verify(customerDao, times(1)).saveCustomer(testCustomerDto);
        }
    }

    @Test
    void getCustomerByCustomerId_Success() {
        // Arrange
        try (MockedStatic<EPayIdentityUtil> mockedEPayIdentityUtil = Mockito.mockStatic(EPayIdentityUtil.class, CALLS_REAL_METHODS)) {
            SecurityContextHolder.getContext().setAuthentication(mock(Authentication.class));
            when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(mockEPayPrincipal);
            mockedEPayIdentityUtil.when(() -> EPayIdentityUtil.getUserPrincipal().getMId()).thenReturn("1234");

            try (MockedStatic<EncryptionService> mockedEncryptionService = Mockito.mockStatic(EncryptionService.class)) {
                mockedEncryptionService.when(() -> EncryptionService.encryptValueByStringKey(any(String.class), any(String.class), any(EncryptionDecryptionAlgo.class), any(GCMIvLength.class), any(GCMTagLength.class))).thenReturn("mockedEncryptedString");

                when(customerDao.getCustomerByCustomerId("1234", "test-customer-id")).thenReturn(testCustomerDto);
                when(customerDao.getMerchantMek()).thenReturn(testMek);

                // Act
                TransactionResponse<String> result = customerService.getCustomerByCustomerId(testCustomerId);

                // Assert
                assertNotNull(result);
                assertEquals(TransactionConstant.RESPONSE_SUCCESS, result.getStatus());
                assertNotNull(result.getData());
                assertEquals(1, result.getData().size());
                assertNotNull(result.getData().get(0));

                verify(customerValidator, times(1)).validateCustomerId(testCustomerId);
                verify(customerDao, times(1)).getCustomerByCustomerId("1234", "test-customer-id");
                verify(customerDao, times(1)).getMerchantMek();
            }
        }
    }

    @Test
    void getCustomerByCustomerId_WithEmptyCustomerId() {
        // Arrange
        String emptyCustomerId = "";
        doThrow(new RuntimeException("Invalid customer ID")).when(customerValidator).validateCustomerId(emptyCustomerId);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            customerService.getCustomerByCustomerId(emptyCustomerId);
        });

        verify(customerValidator, times(1)).validateCustomerId(emptyCustomerId);
        verify(customerDao, never()).getCustomerByCustomerId(anyString(), anyString());
    }

    @Test
    void getCustomerByCustomerId_WithNullCustomerId() {
        // Arrange
        String nullCustomerId = null;
        doThrow(new RuntimeException("Invalid customer ID")).when(customerValidator).validateCustomerId(nullCustomerId);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            customerService.getCustomerByCustomerId(nullCustomerId);
        });

        verify(customerValidator, times(1)).validateCustomerId(nullCustomerId);
        verify(customerDao, never()).getCustomerByCustomerId(anyString(), anyString());
    }

    @Test
    void getCustomerByCustomerId_ValidatorThrowsException() {
        // Arrange
        doThrow(new RuntimeException("Validation failed")).when(customerValidator).validateCustomerId(testCustomerId);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            customerService.getCustomerByCustomerId(testCustomerId);
        });

        verify(customerValidator, times(1)).validateCustomerId(testCustomerId);
        verify(customerDao, never()).getCustomerByCustomerId(anyString(), anyString());
    }

    @Test
    void getCustomerByCustomerId_GetCustomerThrowsExceptiosn() {
        try (MockedStatic<EPayIdentityUtil> mockedEPayIdentityUtil = mockStatic(EPayIdentityUtil.class)) {
            SecurityContextHolder.getContext().setAuthentication(mock(Authentication.class));

            // Mock static method call
            mockedEPayIdentityUtil.when(EPayIdentityUtil::getUserPrincipal).thenReturn(mockEPayPrincipal);
            when(mockEPayPrincipal.getMId()).thenReturn(testMerchantId);

            // Mock the DAO method to throw the specific exception
            when(customerDao.getCustomerByCustomerId(eq(testMerchantId), eq(testCustomerId))).thenThrow(new TransactionException(NOT_FOUND_ERROR_CODE, MessageFormat.format(NOT_FOUND_ERROR_MESSAGE, VALID_CUSTOMER)));

            // Act & Assert
            // Assert the specific exception type
            assertThrows(TransactionException.class, () -> {
                customerService.getCustomerByCustomerId(testCustomerId);
            });

            // Verify interactions
            verify(customerValidator, times(1)).validateCustomerId(testCustomerId);
            verify(customerDao, times(1)).getCustomerByCustomerId(eq(testMerchantId), eq(testCustomerId));
            // getMerchantMek is not called because the exception is thrown first.
            verify(customerDao, never()).getMerchantMek();
        }
    }

    @Test
    void getCustomerByCustomerId_GetMekThrowsException() {
        // Arrange
        try (MockedStatic<EPayIdentityUtil> mockedEPayIdentityUtil = mockStatic(EPayIdentityUtil.class)) {
            SecurityContextHolder.getContext().setAuthentication(mock(Authentication.class));

            // Mock static method call
            mockedEPayIdentityUtil.when(EPayIdentityUtil::getUserPrincipal).thenReturn(mockEPayPrincipal);
            when(mockEPayPrincipal.getMId()).thenReturn(testMerchantId);

            when(customerDao.getCustomerByCustomerId(testMerchantId, testCustomerId)).thenReturn(testCustomerDto);
            when(customerDao.getMerchantMek()).thenThrow(new RuntimeException("Failed to get MEK"));

            // Act & Assert
            assertThrows(RuntimeException.class, () -> {
                customerService.getCustomerByCustomerId(testCustomerId);
            });

            verify(customerValidator, times(1)).validateCustomerId(testCustomerId);
            verify(customerDao, times(1)).getCustomerByCustomerId(testMerchantId, testCustomerId);
            verify(customerDao, times(1)).getMerchantMek();
        }
    }

    @Test
    void updateCustomerStatus_Success() {

        try (MockedStatic<EPayIdentityUtil> mockedEPayIdentityUtil = mockStatic(EPayIdentityUtil.class)) {
            SecurityContextHolder.getContext().setAuthentication(mock(Authentication.class));

            // Mock static method call
            mockedEPayIdentityUtil.when(EPayIdentityUtil::getUserPrincipal).thenReturn(mockEPayPrincipal);
            when(mockEPayPrincipal.getMId()).thenReturn(testMerchantId);

            try (MockedStatic<EncryptionService> mockedEncryptionService = Mockito.mockStatic(EncryptionService.class)) {
                mockedEncryptionService.when(() -> EncryptionService.encryptValueByStringKey(any(String.class), any(String.class), any(EncryptionDecryptionAlgo.class), any(GCMIvLength.class), any(GCMTagLength.class))).thenReturn("mockedEncryptedString");
                // Arrange
                doReturn(testMek).when(customerDao).getMerchantMek();
                doReturn(testCustomerDto).when(customerDao).updateCustomerStatus(anyString(), anyString(), eq(CustomerStatus.ACTIVE));

                // Act
                TransactionResponse<String> result = customerService.updateCustomerStatus(testCustomerId, testCustomerStatus);

                // Assert
                assertNotNull(result);
                assertEquals(TransactionConstant.RESPONSE_SUCCESS, result.getStatus());
                assertNotNull(result.getData());
                assertEquals(1, result.getData().size());
                assertNotNull(result.getData().getFirst());

                mockedEPayIdentityUtil.verify(EPayIdentityUtil::getUserPrincipal, times(1));

                verify(customerValidator, times(1)).validateCustomerId(testCustomerId);
                verify(customerDao, times(1)).updateCustomerStatus(testMerchantId, testCustomerId, CustomerStatus.ACTIVE);
                verify(customerDao, times(1)).getMerchantMek();
            }
        }
    }

    @Test
    void updateCustomerStatus_WithEmptyCustomerId() {
        // Arrange
        String emptyCustomerId = "";
        doThrow(new RuntimeException("Invalid customer ID")).when(customerValidator).validateCustomerId(emptyCustomerId);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            customerService.updateCustomerStatus(emptyCustomerId, testCustomerStatus);
        });

        verify(customerValidator, times(1)).validateCustomerId(emptyCustomerId);
        verify(customerDao, never()).updateCustomerStatus(anyString(), anyString(), any());
    }

    @Test
    void updateCustomerStatus_WithEmptyStatus() {
        // Mock static method calls and dependencies
        try (MockedStatic<EPayIdentityUtil> mockedEPayIdentityUtil = mockStatic(EPayIdentityUtil.class);
             MockedStatic<CustomerStatus> mockedCustomerStatus = mockStatic(CustomerStatus.class)) {
            SecurityContextHolder.getContext().setAuthentication(mock(Authentication.class));

            // Mock static method call
            mockedEPayIdentityUtil.when(EPayIdentityUtil::getUserPrincipal).thenReturn(mockEPayPrincipal);
            when(mockEPayPrincipal.getMId()).thenReturn(testMerchantId);


            String emptyStatus = "";

            // Mock static CustomerStatus call to throw an exception for empty status
            mockedCustomerStatus.when(() -> CustomerStatus.getStatus(eq(emptyStatus)))
                    .thenThrow(new TransactionException(
                            INVALID_ERROR_CODE_NO_REASON,
                            MessageFormat.format(INVALID_ERROR_MESSAGE_NO_REASON, "Customer status", emptyStatus)));

            // Act & Assert
            assertThrows(TransactionException.class, () -> {
                customerService.updateCustomerStatus(testCustomerId, emptyStatus);
            });

            // Verify interactions
            verify(customerValidator, times(1)).validateCustomerId(testCustomerId);
            // Verify that DAO methods were never called
            verify(customerDao, never()).getMerchantMek();
            verify(customerDao, never()).updateCustomerStatus(any(), any(), any());
        }
    }

    @Test
    void updateCustomerStatus_WithNullCustomerId() {
        // Arrange
        String nullCustomerId = null;
        doThrow(new RuntimeException("Invalid customer ID")).when(customerValidator).validateCustomerId(nullCustomerId);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            customerService.updateCustomerStatus(nullCustomerId, testCustomerStatus);
        });

        verify(customerValidator, times(1)).validateCustomerId(nullCustomerId);
        verify(customerDao, never()).updateCustomerStatus(anyString(), anyString(), any());
    }

    @Test
    void updateCustomerStatus_WithNullStatus() {
        try (MockedStatic<EPayIdentityUtil> mockedEPayIdentityUtil = mockStatic(EPayIdentityUtil.class);
             MockedStatic<CustomerStatus> mockedCustomerStatus = mockStatic(CustomerStatus.class)) {
            SecurityContextHolder.getContext().setAuthentication(mock(Authentication.class));

            // Mock static method call
            mockedEPayIdentityUtil.when(EPayIdentityUtil::getUserPrincipal).thenReturn(mockEPayPrincipal);
            when(mockEPayPrincipal.getMId()).thenReturn(testMerchantId);
            // Mock static CustomerStatus call to throw an exception for empty status
            mockedCustomerStatus.when(() -> CustomerStatus.getStatus(null))
                    .thenThrow(new TransactionException(
                            INVALID_ERROR_CODE_NO_REASON,
                            MessageFormat.format(INVALID_ERROR_MESSAGE_NO_REASON, "Customer status", null)));

            // Act & Assert
            assertThrows(TransactionException.class, () -> {
                customerService.updateCustomerStatus(testCustomerId, null);
            });

            // Verify interactions
            verify(customerValidator, times(1)).validateCustomerId(testCustomerId);
            // Verify that DAO methods were never called
            verify(customerDao, never()).getMerchantMek();
            verify(customerDao, never()).updateCustomerStatus(any(), any(), any());
        }
    }

    @Test
    void updateCustomerStatus_ValidatorThrowsException() {
        // Arrange
        doThrow(new RuntimeException("Validation failed")).when(customerValidator).validateCustomerId(testCustomerId);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            customerService.updateCustomerStatus(testCustomerId, testCustomerStatus);
        });

        verify(customerValidator, times(1)).validateCustomerId(testCustomerId);
        verify(customerDao, never()).updateCustomerStatus(anyString(), anyString(), any());
    }


    @Test
    void updateCustomerStatus_UpdateCustomerThrowsException() {
        try (MockedStatic<EPayIdentityUtil> mockedEPayIdentityUtil = mockStatic(EPayIdentityUtil.class)) {
            SecurityContextHolder.getContext().setAuthentication(mock(Authentication.class));

            // Mock static method call
            mockedEPayIdentityUtil.when(EPayIdentityUtil::getUserPrincipal).thenReturn(mockEPayPrincipal);
            when(mockEPayPrincipal.getMId()).thenReturn(testMerchantId);

            // **Remove this stubbing: It's redundant and is likely confusing Mockito**
            // when(customerDao.updateCustomerStatus(testMerchantId, testCustomerId, CustomerStatus.ACTIVE)).thenReturn(testCustomerDto);

            // Mock DAO call to throw an exception
            when(customerDao.updateCustomerStatus(eq(testMerchantId), eq(testCustomerId), any(CustomerStatus.class)))
                    .thenThrow(new TransactionException(NOT_FOUND_ERROR_CODE, MessageFormat.format(NOT_FOUND_ERROR_MESSAGE, VALID_CUSTOMER)));

            // Act & Assert using assertThrows to cleanly test for the exception
            assertThrows(TransactionException.class, () -> {
                customerService.updateCustomerStatus(testCustomerId, String.valueOf(CustomerStatus.ACTIVE));
            });

            // Verify interactions that should have happened BEFORE the exception
            // Fix: Use eq() for all arguments when using matchers.
            verify(customerValidator, times(1)).validateCustomerId(eq(testCustomerId));
            verify(customerDao, times(1)).updateCustomerStatus(eq(testMerchantId), eq(testCustomerId), eq(CustomerStatus.ACTIVE));

            // Verify interactions that should NOT have happened due to the exception
            verify(customerDao, never()).getMerchantMek();
        }
    }


    @Test
    void updateCustomerStatus_GetMekThrowsException() {

        // Arrange
        try (MockedStatic<EPayIdentityUtil> mockedEPayIdentityUtil = mockStatic(EPayIdentityUtil.class)) {
            SecurityContextHolder.getContext().setAuthentication(mock(Authentication.class));

            // Mock static method call
            mockedEPayIdentityUtil.when(EPayIdentityUtil::getUserPrincipal).thenReturn(mockEPayPrincipal);
            when(mockEPayPrincipal.getMId()).thenReturn(testMerchantId);

            when(customerDao.getCustomerByCustomerId(testMerchantId, testCustomerId)).thenReturn(testCustomerDto);
            when(customerDao.getMerchantMek()).thenThrow(new RuntimeException("Failed to get MEK"));

            // Act & Assert
            assertThrows(RuntimeException.class, () -> {
                customerService.getCustomerByCustomerId(testCustomerId);
            });

            verify(customerValidator, times(1)).validateCustomerId(testCustomerId);
            verify(customerDao, times(1)).getCustomerByCustomerId(testMerchantId, testCustomerId);
            verify(customerDao, times(1)).getMerchantMek();
        }
    }

    @ParameterizedTest(name = "Customer status update with status: {0}")
    @EnumSource(CustomerStatus.class)
    void updateCustomerStatus_WithDifferentStatuses(CustomerStatus status) {
        // Arrange
        try (MockedStatic<EPayIdentityUtil> mockedEPayIdentityUtil = mockStatic(EPayIdentityUtil.class)) {
            SecurityContextHolder.getContext().setAuthentication(mock(Authentication.class));

            // Mock static method call
            mockedEPayIdentityUtil.when(EPayIdentityUtil::getUserPrincipal).thenReturn(mockEPayPrincipal);
            when(mockEPayPrincipal.getMId()).thenReturn(testMerchantId);
            try (MockedStatic<EncryptionService> mockedEncryptionService = Mockito.mockStatic(EncryptionService.class)) {
                mockedEncryptionService.when(() -> EncryptionService.encryptValueByStringKey(any(String.class), any(String.class), any(EncryptionDecryptionAlgo.class), any(GCMIvLength.class), any(GCMTagLength.class))).thenReturn("mockedEncryptedString");
                // Arrange
                when(customerDao.getMerchantMek()).thenReturn(testMek);
            when(customerDao.updateCustomerStatus(testMerchantId, testCustomerId, status))
                    .thenReturn(testCustomerDto);

            // Act
            TransactionResponse<String> result = customerService.updateCustomerStatus(testCustomerId, status.name());

            // Assert
            assertNotNull(result);
            assertEquals(TransactionConstant.RESPONSE_SUCCESS, result.getStatus());

            // Verify that the mocked methods were called exactly once for this specific status
            verify(customerValidator, times(1)).validateCustomerId(testCustomerId);
            verify(customerDao, times(1)).getMerchantMek();
            verify(customerDao, times(1)).updateCustomerStatus(testMerchantId, testCustomerId, status);

            // Reset the mock interactions after each test run to ensure isolation
            reset(customerValidator, customerDao);

        }}}

    @Test
    void updateCustomerStatus_WithSpecialCharacters() {
        // Arrange
        String specialCustomerId = "customer-id-!@#$%^&*()";
        String specialStatus = "status-!@#$%^&*()";
        try (MockedStatic<EPayIdentityUtil> mockedEPayIdentityUtil = mockStatic(EPayIdentityUtil.class);
             MockedStatic<CustomerStatus> mockedCustomerStatus = mockStatic(CustomerStatus.class)) {
            SecurityContextHolder.getContext().setAuthentication(mock(Authentication.class));

            // Mock static method call
            mockedEPayIdentityUtil.when(EPayIdentityUtil::getUserPrincipal).thenReturn(mockEPayPrincipal);
            when(mockEPayPrincipal.getMId()).thenReturn(testMerchantId);
            // Mock static CustomerStatus call to throw an exception for empty status
            mockedCustomerStatus.when(() -> CustomerStatus.getStatus(specialStatus))
                    .thenThrow(new TransactionException(
                            INVALID_ERROR_CODE_NO_REASON,
                            MessageFormat.format(INVALID_ERROR_MESSAGE_NO_REASON, "Customer status", null)));

            // Act & Assert
            assertThrows(TransactionException.class, () -> {
                customerService.updateCustomerStatus(specialCustomerId, specialStatus);
            });

            // Verify interactions
            verify(customerValidator, times(1)).validateCustomerId(specialCustomerId);
            // Verify that DAO methods were never called
            verify(customerDao, never()).getMerchantMek();
            verify(customerDao, never()).updateCustomerStatus(any(), any(), any());
        }
    }
}
