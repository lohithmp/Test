gfsh>configure pdx --portable-auto-serializable-classes=com\.sbi\..* --read-serialized=true --disk-store
read-serialized = true
ignore-unread-fields = false
persistent = true
disk-store = DEFAULT
PDX Serializer = org.apache.geode.pdx.ReflectionBasedAutoSerializer
Portable Classes = [com\.sbi\..*]
Cluster configuration for group 'cluster' is updated.


gfsh>query --query="select * from /Admin_Merchant_Info"
Result  : false
Message : A ClassNotFoundException was thrown while trying to deserialize cached value.

gfsh>query --query="select * from /Admin_PayGateway_DownTime_DTLS"
Result  : false
Message : A ClassNotFoundException was thrown while trying to deserialize cached value.

gfsh>query --query="select * from /Admin_Merchant_Info"
Result  : false
Message : A ClassNotFoundException was thrown while trying to deserialize cached value.




this my springboot gemfire configuration



import org.apache.geode.cache.client.ClientCache;
import org.apache.geode.cache.client.ClientCacheFactory;
import org.apache.geode.pdx.ReflectionBasedAutoSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.gemfire.config.annotation.ClientCacheApplication;
import org.springframework.data.gemfire.config.annotation.EnableEntityDefinedRegions;
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories;

import static com.epay.admin.util.ApplicationConstants.CACHE_REPOSITORIES;
import static com.epay.admin.util.ApplicationConstants.ENTITY_DEFINED_REGIONS;

@Configuration
@EnableEntityDefinedRegions(basePackages =  "com.sbi.epay.cache.admin.entity" )
@EnableGemfireRepositories(basePackages = "com.epay.admin.repository.cache" )
public class GemFireConfiguration  {

    @Bean("gemfireCache")
    public ClientCache gemfireClientCache() {
        return new ClientCacheFactory()
                .setPdxReadSerialized(true)
                .setPdxSerializer(new ReflectionBasedAutoSerializer("com.sbi.epay.cache.admin.entity"))
                .create();
    }
}



this my service call where i interacting with cache and db


@Service
@Data
@RequiredArgsConstructor
public class MerchantPaymodeService {
    LoggerUtility logger = LoggerFactoryUtility.getLogger(MerchantPaymodeService.class);

    private final MerchantPaymodeDao merchantPayModeDao;
    private final MerchantDao merchantDao;
    private final MerchantPaymentDownTimeDao merchantPaymentDownTimeDao;
    private final MerchantCacheRepository merchantCacheRepository;
    private final MerchantPayModeCacheRepository merchantPayModeCacheRepository;
    private final MerchantPayModeDownTimeCacheRepository merchantPayModeDownTimeCacheRepository;
    private final MerchantService merchantService;

    public ResponseDto<MerchantPaymodeDTO> getMerchantPaymodes(String mId) {
        logger.info("Cache call for get merchant id.");

        AdminValidator.validateMid(mId);

        Optional<MerchantEntity> merchantCacheDetails = merchantCacheRepository.findById(mId);
        Optional<Merchant> merchantDetailsOptional;

        if (merchantCacheDetails.isEmpty()) {
            logger.info("Cache get merchant data is empty. Calling database.");
            merchantDetailsOptional = merchantDao.getMerchantById(mId);

            if (merchantDetailsOptional.isEmpty()) {
                logger.error("Database get merchant details is empty.");
                return ResponseDto.<MerchantPaymodeDTO>builder().status(Status.FAIL.getValue()).total(0L).count(0L).data(List.of()).errors(List.of(ErrorDto.builder().errorCode(ErrorConstants.NOT_FOUND_ERROR_CODE).errorMessage(MessageFormat.format(ErrorConstants.NOT_FOUND_ERROR_MESSAGE, "Mid")).reason(MessageFormat.format(ErrorConstants.NOT_FOUND_ERROR_MESSAGE, "Mid")).build())).build();
            }
            logger.info("Merchant data fetched successfully from database.");
            MerchantEntity merchantCacheEntity = new MerchantEntity();
            BeanUtils.copyProperties(merchantDetailsOptional.get(), merchantCacheEntity);
            merchantCacheRepository.save(merchantCacheEntity);
            logger.info("Merchant data saved into cache.");

        } else {
            logger.info("Merchant data fetched successfully from cache.");
            merchantDetailsOptional = Optional.of(new Merchant());
            BeanUtils.copyProperties(merchantCacheDetails.get(), merchantDetailsOptional.get());
        }

        MerchantInfoDTO merchantInfoDTO = MerchantInfoDTO.builder().build();
        BeanUtils.copyProperties(merchantDetailsOptional.get(), merchantInfoDTO);
        merchantInfoDTO.setTheme(merchantService.getPaymentTheme(mId));

        //Call Gemfire Cache Merchant_PayMode Region
        logger.info("Cache call for get merchant pay modes");
        List<MerchantPayModeEntity> merchantPayModeCacheList = merchantPayModeCacheRepository.findMerchantPayModeById(mId, "A", "A");
        List<MerchantPaymode> merchantPaymodeList;

        if (merchantPayModeCacheList.isEmpty()) {
            logger.info("Cache get merchant pay modes is empty. Calling database.");
            merchantPaymodeList = merchantPayModeDao.getMerchantPaymodeById(mId);

            if (merchantPaymodeList.isEmpty()) {
                logger.error("Database get merchant pay modes is empty.");
                return ResponseDto.<MerchantPaymodeDTO>builder().status(Status.FAIL.getValue()).total(0L).count(0L).data(List.of()).errors(List.of(ErrorDto.builder().errorCode(ErrorConstants.NOT_FOUND_ERROR_CODE).errorMessage(MessageFormat.format(ErrorConstants.NOT_FOUND_ERROR_MESSAGE, "Mid")).reason(MessageFormat.format(ErrorConstants.NOT_FOUND_ERROR_MESSAGE, "Mid")).build())).build();
            }

            List<MerchantPayModeEntity> merchantPayModeCacheEntities = merchantPaymodeList.stream().map(merchantPaymode -> {
                MerchantPayModeEntity cacheEntity = new MerchantPayModeEntity();
                BeanUtils.copyProperties(merchantPaymode, cacheEntity);
                return cacheEntity;
            }).collect(Collectors.toList());

            merchantPayModeCacheRepository.saveAll(merchantPayModeCacheEntities);

        } else {
            logger.info("Merchant pay modes fetched successfully from cache.");

            merchantPaymodeList = merchantPayModeCacheList.stream().map(cacheEntity -> {
                MerchantPaymode payMode = new MerchantPaymode();
                BeanUtils.copyProperties(cacheEntity, payMode);
                return payMode;
            }).collect(Collectors.toList());
        }
    }



Cahe Entities these are................


package com.sbi.epay.cache.admin.entity;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.gemfire.mapping.annotation.Region;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Region(name = "Admin_Merchant_Info")
public class  MerchantEntity implements Serializable {

    @Id
    private String merchantId;
    private String isActive;
    private byte[] logoUrl;
    private String merchantName;
    private String countryCode;
    private Integer maxAtrnCount;
    private String currency;
    private String preferredPayMode;
    private String preferredBank;
    @Transient
    private List<Object> themes = new ArrayList<>();
    @Transient
    private String returnURL = "NA";
    private Long accessTokenExpiryTime;
    private Long transactionTokenExpiryTime;

}


package com.sbi.epay.cache.admin.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.gemfire.mapping.annotation.Region;

import java.io.Serializable;

@Data
@Region(name = "Admin_Merchant_PayMode")
public class MerchantPayModeEntity implements Serializable {

    @Id
    private String srNo;
    private String mid;
    private String payGatewayName;
    private String payGatewayId;
    private String currency;
    private String payModeCode;
    private String payProc;
    private String gatewayIssueCode;
    private String aggregatorGatewayMapId;
    private String status;
    private String isTpActive;
    private String recordStatus;
    private String timerWindow;
    private String upiQrVpa;

}
