package com.epay.transaction.config;

import com.epay.transaction.entity.cache.MerchantPayModeDownTimeCacheEntity;
import com.epay.transaction.repository.cache.DowntimeAPICacheRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.gemfire.config.annotation.ClientCacheApplication;
import org.springframework.data.gemfire.config.annotation.EnableEntityDefinedRegions;
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories;


@Configuration
@ClientCacheApplication
@EnableEntityDefinedRegions(basePackageClasses  = MerchantPayModeDownTimeCacheEntity.class)
@EnableGemfireRepositories(basePackageClasses = DowntimeAPICacheRepository.class)
public class GemFireConfiguration {}


package com.epay.transaction.entity.cache;

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

package com.epay.transaction.repository.cache;

import com.epay.transaction.entity.cache.MerchantPayModeDownTimeCacheEntity;
import org.springframework.data.gemfire.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface DowntimeAPICacheRepository extends CrudRepository<MerchantPayModeDownTimeCacheEntity, String> {

        @Query("SELECT payModeDownTime FROM /AGG_PayGateway_DownTime_DTLS payModeDownTime WHERE payModeDownTime.status = $1")
        List<Object> findByStatusAndRecordStatus(String status);

}


package com.epay.transaction.dao;

import com.epay.transaction.dto.DowntimeAPIDto;
import com.epay.transaction.externalservice.AdminServicesClient;
import com.epay.transaction.repository.cache.DowntimeAPICacheRepository;
import com.sbi.epay.logging.utility.LoggerFactoryUtility;
import com.sbi.epay.logging.utility.LoggerUtility;
import lombok.AllArgsConstructor;
import org.apache.geode.pdx.PdxInstance;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class DowntimeAPIDao {
    private final LoggerUtility logger = LoggerFactoryUtility.getLogger(DowntimeAPIDao.class);
    private final DowntimeAPICacheRepository downtimeAPICacheRepository;
    private final AdminServicesClient adminServicesClient;

    public List<DowntimeAPIDto> getDowntimeAPIs(String status) {
        logger.info("Fetch the Transaction downtime details from cache region AGG_PayGateway_DownTime_DTLS.");
        List<Object> objectList = downtimeAPICacheRepository.findByStatusAndRecordStatus(status);
        if (objectList.isEmpty()) {
            logger.info("Transaction downtime api details found empty in cache and called from database tables.");
            return adminServicesClient.getDowntimeAPIs();
        }
        logger.info("Transaction downtime details from cache region AGG_PayGateway_DownTime_DTLS fetched successfully.");
        return objectList.stream().map(this::convertToEntity).collect(Collectors.toList());
    }

    private DowntimeAPIDto convertToEntity(Object pdxInstance) {
        if (pdxInstance instanceof PdxInstance) {
            logger.info("Transaction downtime details from cache is in type PdxInstance.");
            return (DowntimeAPIDto) mapPdxInstanceToEntity((PdxInstance) pdxInstance);
        } else if (pdxInstance instanceof DowntimeAPIDto) {
            logger.info("Transaction downtime details from cache is in type Object type");
            return (DowntimeAPIDto) pdxInstance;
        }
        return (DowntimeAPIDto) pdxInstance;
    }

    public DowntimeAPIDto mapPdxInstanceToEntity(PdxInstance instance) {
        logger.info("Mapping started for PdxInstance type to Object.");
        DowntimeAPIDto entity = new DowntimeAPIDto();
        entity.setPayGtwId((String) instance.getField("id"));
        entity.setDownTimeStartDatetime((String) instance.getField("startTimestamp"));
        entity.setDownTimeEndDatetime((String) instance.getField("endTimestamp"));
        entity.setErrorMessage((String) instance.getField("errorMessage"));
        entity.setStatus((String) instance.getField("status"));
        entity.setRecordStatus((String) instance.getField("recordStatus"));
        entity.setPayModeCode((String) instance.getField("payModeCode"));
        return entity;
    }
}


spring.data.gemfire.locators=localhost[10334]
spring.data.gemfire.cache.server.port=40404
spring.data.gemfire.logging.level=info
logging.level.org.apache.geode=debug
logging.level.org.springframework.data.gemfire=debug
    spring.data.gemfire.pdx.read-serialized=false
