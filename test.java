
gfsh>describe member --name=server
Name            : server
Id              : SBIEPAYBD173(server:18580)<v3>:54220
Type            : Server
Host            : SBIEPAYBD173.CORP.AD.SBI
Regions         : Merchant
AGG_PayGateway_DownTime_DTLS
Merchant_PayMode
Timezone        : Asia/Calcutta +05:30
Metrics URL     : Not Available
PID             : 18580
Groups          :
Redundancy-Zone :
Used Heap       : 69M
Max Heap        : 2028M
Load Average1   : Not Available
Working Dir     : D:\GemFireServer\vmware-gemfire-10.1.2_torun_gfsh\vmware-gemfire-10.1.2\bin\server
Log file        : D:\GemFireServer\vmware-gemfire-10.1.2_torun_gfsh\vmware-gemfire-10.1.2\bin\server\server.log
Locators        : 10.30.64.173[10334]

Cache Server Information
Server Bind              :
Server Port              : 40404
Running                  : true

Client Connections : 2

gfsh>describe member --name=locator
Name            : locator
Id              : SBIEPAYBD173(locator:10684:locator)<ec><v0>:59193
Type            : Locator
Host            : SBIEPAYBD173.CORP.AD.SBI
Regions         :
Timezone        : Asia/Calcutta +05:30
Metrics URL     : Not Available
PID             : 10684
Groups          :
Redundancy-Zone :
Used Heap       : 232M
Max Heap        : 2028M
Load Average1   : Not Available
Working Dir     : D:\GemFireServer\vmware-gemfire-10.1.2_torun_gfsh\vmware-gemfire-10.1.2\bin\locator
Log file        : D:\GemFireServer\vmware-gemfire-10.1.2_torun_gfsh\vmware-gemfire-10.1.2\bin\locator\locator.log
Locators        : SBIEPAYBD173.CORP.AD.SBI[10334]

ERROR===>
remote server on SBIEPAYBD173(12080:loner):59111:66901926: org.apache.geode.pdx.PdxSerializationException: Could not create an instance of a class com.epay.admin.entity.cache.MerchantPayModeDownTimeCacheEntity

import com.epay.transaction.dto.booking.DownTimeDTO;
import com.epay.transaction.entity.cache.MerchantPayModeDownTimeCacheEntity;
import com.epay.transaction.model.response.EPayResponseEntity;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.geode.cache.Region;
import org.apache.geode.cache.client.ClientCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;



@Service
@RequiredArgsConstructor
public class DowntimeAPIService {

private final ClientCache clientCache;
    public EPayResponseEntity<DownTimeDTO> getDowntimeAPIs() {
        String status = "L";
        Region<String, MerchantPayModeDownTimeCacheEntity> region =  clientCache.getRegion("AGG_PayGateway_DownTime_DTLS");

        MerchantPayModeDownTimeCacheEntity downTimeCache = region.get("15");
        return EPayResponseEntity.<DownTimeDTO>builder().data((List<DownTimeDTO>) downTimeCache).status(1).count(1L).build();
    }

}



import com.epay.transaction.entity.cache.MerchantPayModeDownTimeCacheEntity;
import org.apache.geode.cache.GemFireCache;
import org.apache.geode.cache.client.ClientCache;
import org.apache.geode.cache.client.ClientCacheFactory;
import org.apache.geode.cache.client.ClientRegionShortcut;
import org.aspectj.bridge.ICommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.gemfire.client.ClientRegionFactoryBean;
import org.springframework.data.gemfire.config.annotation.ClientCacheApplication;
import org.springframework.data.gemfire.config.annotation.EnableEntityDefinedRegions;
import org.springframework.data.gemfire.mapping.MappingPdxSerializer;
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories;

@Configuration
public class GemFireConfiguration {

    @Bean
    public ClientCache clientCache() {
        return new ClientCacheFactory()
                .addPoolLocator("SBIEPAYBD173.CORP.AD.SBI",10334)
                .setPdxReadSerialized(false)
                .create();
    }

    @Bean
    public ClientRegionFactoryBean<String, MerchantPayModeDownTimeCacheEntity> downTimeRegion(ClientCache clientCache) {
        ClientRegionFactoryBean<String, MerchantPayModeDownTimeCacheEntity> merchantRegionFactory = new ClientRegionFactoryBean<>();
        merchantRegionFactory.setCache(clientCache);
        merchantRegionFactory.setRegionName("AGG_PayGateway_DownTime_DTLS");
        merchantRegionFactory.setShortcut(ClientRegionShortcut.PROXY);
        return merchantRegionFactory;
    }

}





import jakarta.persistence.Cacheable;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.geode.pdx.PdxReader;
import org.apache.geode.pdx.PdxSerializable;
import org.apache.geode.pdx.PdxWriter;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.gemfire.config.annotation.EnablePdx;
import org.springframework.data.gemfire.mapping.annotation.Region;

import javax.cache.annotation.CachePut;
import java.io.Serializable;

@Data
public class MerchantPayModeDownTimeCacheEntity implements Serializable{

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












public class MerchantPayModeDownTimeCacheEntity implements PdxSerializable, Serializable {

    // Fields and annotations remain the same

    @Override
    public void toData(PdxWriter writer) {
        writer.writeString("paygtwid", id);
        writer.writeString("downtimestartdatetime", startTimestamp);
        writer.writeString("downtimeenddatetime", endTimestamp);
        writer.writeString("remarks", errorMessage);
        writer.writeString("status", status);
        writer.writeString("recordstatus", recordStatus);
        writer.writeString("paymodecode", payModeCode);
    }

    @Override
    public void fromData(PdxReader reader) {
        this.id = reader.readString("paygtwid");
        this.startTimestamp = reader.readString("downtimestartdatetime");
        this.endTimestamp = reader.readString("downtimeenddatetime");
        this.errorMessage = reader.readString("remarks");
        this.status = reader.readString("status");
        this.recordStatus = reader.readString("recordstatus");
        this.payModeCode = reader.readString("paymodecode");
    }
}






