gemfire config file

package com.epay.admin.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.gemfire.config.annotation.ClientCacheApplication;
import org.springframework.data.gemfire.config.annotation.EnableEntityDefinedRegions;
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories;

@Configuration
@ClientCacheApplication
@EnableEntityDefinedRegions(basePackages = "com/epay/admin/entity/cache")
@EnableGemfireRepositories(basePackages = "com/epay/admin/repository/cache")
public class GemFireConfiguration  {}


cache entity file
	package com.epay.admin.entity.cache;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.gemfire.mapping.annotation.Region;

import java.io.Serializable;

@Data
@Region("AGG_PayGateway_DownTime_DTLS")
public class MerchantPayModeDownTimeCacheEntity implements Serializable {

    @Id
    @Column(name = "paygtwid")
    private String id;

    @Column(name = "downtimestartdatetime")
    private String startTimestamp;

    @Column(name = "downtimeenddatetime")
    private String endTimestamp;

    @Column(name = "remarks")
    private String errorMessage;

    @Column(name = "status")
    private String status;

    @Column(name = "recordstatus")
    private String recordStatus;

    @Column(name = "paymodecode")
    private String payModeCode;
}

package com.epay.admin.repository.cache;

import com.epay.admin.entity.cache.MerchantPayModeDownTimeCacheEntity;
import org.springframework.data.gemfire.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MerchantPayModeDownTimeCacheRepository extends CrudRepository<MerchantPayModeDownTimeCacheEntity, String> {

    @Query("SELECT payModeDownTime FROM /AGG_PayGateway_DownTime_DTLS payModeDownTime WHERE payModeDownTime.status = $1 AND payModeDownTime.recordStatus = $2")
    List<MerchantPayModeDownTimeCacheEntity> findByStatusAndRecordStatus(String status, String recordStatus);
}


package com.epay.admin.service;

import com.epay.admin.dao.MerchantPaymentDownTimeDao;
import com.epay.admin.dto.DownTimeDTO;
import com.epay.admin.entity.MerchantPaymodeDownTime;
import com.epay.admin.entity.cache.MerchantPayModeDownTimeCacheEntity;
import com.epay.admin.model.response.ErrorDto;
import com.epay.admin.model.response.ResponseDto;
import com.epay.admin.repository.cache.MerchantPayModeDownTimeCacheRepository;
import com.epay.admin.util.ApplicationConstants;
import com.epay.admin.util.Status;
import com.sbi.epay.logging.utility.LoggerUtility;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.sbi.epay.logging.utility.LoggerFactoryUtility;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Data
@RequiredArgsConstructor
public class MerchantPayModeDownTimeService {
    LoggerUtility logger = LoggerFactoryUtility.getLogger(MerchantPayModeDownTimeService.class);

    private final MerchantPaymentDownTimeDao merchantPaymentDownTimeDao;
    private final MerchantPayModeDownTimeCacheRepository merchantPayModeDownTimeCacheRepository;

    public ResponseDto<DownTimeDTO> getDowntimePayMode() {
        try {
            logger.info("Cache Call to get pay mode down time.");
            List<MerchantPayModeDownTimeCacheEntity> payModeDownTimeCacheList = merchantPayModeDownTimeCacheRepository.findByStatusAndRecordStatus("L", "A");
            List<DownTimeDTO> downTimeDTOList = new ArrayList<>();

            if (payModeDownTimeCacheList.isEmpty()) {
                logger.info("Cache pay mode down time list is empty. Calling database.");
                //Call DataBase AGG_PAYGATEWAY_DOWNTIME_DTLS_VIEW by Id
                List<MerchantPaymodeDownTime> downTimeList = merchantPaymentDownTimeDao.getCurrentDownPayMode();
                if(CollectionUtils.isEmpty(downTimeList)){
                    logger.error("Database pay mode down time list is empty.");
                    return  ResponseDto.<DownTimeDTO>builder().status(Status.SUCCESS.getValue()).total(0L).count(0L).errors(List.of()).data(List.of()).build();
                }
                logger.info("Pay mode down time list fetched successfully from database.");
                List<MerchantPayModeDownTimeCacheEntity> payModeCacheEntities = downTimeList.stream().map(merchantPayModeCacheEntity -> {
                    MerchantPayModeDownTimeCacheEntity downTimeCache = new MerchantPayModeDownTimeCacheEntity();
                    BeanUtils.copyProperties(merchantPayModeCacheEntity, downTimeCache);
                    downTimeCache.setId(merchantPayModeCacheEntity.getPaymentGatewayId());
                    return downTimeCache;
                }).collect(Collectors.toList());
                // save downTimeList in cache.
                merchantPayModeDownTimeCacheRepository.saveAll(payModeCacheEntities);
                logger.info("Pay mode down time data saved into cache.");

                for(MerchantPaymodeDownTime merchantPayModeDownTime : downTimeList){
                    downTimeDTOList.add(DownTimeDTO.builder().gatewayId(merchantPayModeDownTime.getPaymentGatewayId()).startTimestamp(merchantPayModeDownTime.getStartTimestamp()).endTimestamp(merchantPayModeDownTime.getEndTimestamp()).status(merchantPayModeDownTime.getStatus()).payModeCode(merchantPayModeDownTime.getPayModeCode()).errorMessage(merchantPayModeDownTime.getErrorMessage()).build());
                }
            } else {
                logger.info("Pay mode down time list fetched successfully from cache.");
                for (MerchantPayModeDownTimeCacheEntity merchantPayModeDownTime : payModeDownTimeCacheList) {
                    downTimeDTOList.add(DownTimeDTO.builder().gatewayId(merchantPayModeDownTime.getId()).startTimestamp(merchantPayModeDownTime.getStartTimestamp()).endTimestamp(merchantPayModeDownTime.getEndTimestamp()).status(merchantPayModeDownTime.getStatus()).payModeCode(merchantPayModeDownTime.getPayModeCode()).errorMessage(merchantPayModeDownTime.getErrorMessage()).build());
                }
            }

            return ResponseDto.<DownTimeDTO>builder().status(Status.SUCCESS.getValue()).total((long) downTimeDTOList.size()).count(1L).errors(List.of()).data(downTimeDTOList).build();

        } catch (Exception e) {
            logger.error("Failed to fetch the Merchant pay mode down time: {}", e.getMessage());
            return ResponseDto.<DownTimeDTO>builder().status(Status.FAIL.getValue()).total(0L).count(0L).errors(List.of(ErrorDto.builder().errorCode(ApplicationConstants.UNEXPECTED_QUERY_ERROR_CODE).errorMessage(e.getMessage()).reason("").build())).data(List.of()).build();
        }

    }

}


spring.data.gemfire.locators=localhost[10334]
spring.data.gemfire.cache.server.port=40404
spring.data.gemfire.logging.level=info
	spring.data.gemfire.pdx.read-serialized=false
