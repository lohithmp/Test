Could not obtain required identifier from entity MerchantCacheEntity(merchantId=1, isActive=Active, merchantName=PineLabs, countryCode=INR1, currency=INR, preferredPayMode=UPI, preferredBank=SBI Bank)

curl --location 'http://localhost:9098/admin/v1/merchant/cacheMerchant/1' \
--header 'Content-Type: application/json' \
--data '{
    "merchantId": "1",
    "isActive": "Active",
    
    
    
    "merchantName": "PineLabs",
    "countryCode": "INR1",
    "currency": "INR",
    "preferredPayMode": "UPI",
    "preferredBank": "SBI Bank"
}


'


package com.epay.admin.repository.cache;

import com.epay.admin.entity.cache.MerchantCacheEntity;
import org.springframework.data.gemfire.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MerchantCacheRepository extends CrudRepository<MerchantCacheEntity, String> {

    @Query("SELECT m.merchantName, m.countryCode FROM /Merchant m WHERE m.merchantName =:merchantName")
    Optional<MerchantCacheEntity> findMerchantNameAndCountry(String merchantName);
}



@Data
@Region("Merchants")
public class MerchantCacheEntity {

    @Id
    private String merchantId;

    private String isActive;


    private String merchantName;

    private String countryCode;

    private String currency;

    private String preferredPayMode;

    private String preferredBank;


}


package com.epay.admin.config;

import com.epay.admin.entity.cache.MerchantCacheEntity;
import com.epay.admin.repository.cache.MerchantCacheRepository;
import org.apache.geode.cache.GemFireCache;
import org.apache.geode.cache.client.ClientCache;
import org.apache.geode.cache.client.ClientRegionShortcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.gemfire.client.ClientCacheFactoryBean;
import org.springframework.data.gemfire.client.ClientRegionFactoryBean;
import org.springframework.data.gemfire.config.annotation.ClientCacheApplication;
import org.springframework.data.gemfire.config.annotation.EnableEntityDefinedRegions;
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories;
import org.springframework.data.gemfire.support.ConnectionEndpoint;

@Configuration

@ClientCacheApplication
@EnableEntityDefinedRegions(basePackageClasses = MerchantCacheEntity.class ,clientRegionShortcut = ClientRegionShortcut.LOCAL)
@EnableGemfireRepositories(basePackageClasses = MerchantCacheRepository.class)
public class CacheConfig {

    @Bean
    public ClientRegionFactoryBean<Long, MerchantCacheEntity> merchantRegion(GemFireCache gemFireCache) {
        ClientRegionFactoryBean<Long, MerchantCacheEntity> merchantRegionFactory = new ClientRegionFactoryBean<>();
        merchantRegionFactory.setCache((ClientCache) gemFireCache);
        merchantRegionFactory.setRegionName("merchants");
        merchantRegionFactory.setShortcut(ClientRegionShortcut.LOCAL);
        return merchantRegionFactory;
    }
}
