package com.epay.transaction.config;

import com.epay.transaction.entity.cache.MerchantPayModeDownTimeCacheEntity;
import com.epay.transaction.repository.cache.DowntimeAPICacheRepository;
import org.apache.geode.cache.client.ClientCache;
import org.apache.geode.cache.client.ClientRegionShortcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.gemfire.client.ClientRegionFactoryBean;
import org.springframework.data.gemfire.config.annotation.ClientCacheApplication;
import org.springframework.data.gemfire.config.annotation.EnableEntityDefinedRegions;
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories;


@Configuration
@ClientCacheApplication
@EnableEntityDefinedRegions(basePackageClasses = MerchantPayModeDownTimeCacheEntity.class)
@EnableGemfireRepositories(basePackageClasses = DowntimeAPICacheRepository.class)
public class GemFireConfiguration {

    @Bean
    public ClientRegionFactoryBean<MerchantPayModeDownTimeCacheEntity, String> merchantRegionFactory(ClientCache clientCache) {
        ClientRegionFactoryBean<MerchantPayModeDownTimeCacheEntity, String> region = new ClientRegionFactoryBean<>();
        region.setCache(clientCache);
        region.setRegionName("AGG_PayGateway_DownTime_DTLS");
        region.setShortcut(ClientRegionShortcut.PROXY);
        return region;
    }
}
Step 1:
		I make the spring.data.gemfire.pdx.read-serialized=false I will face this error
		
		Implementation:
			spring.data.gemfire.pdx.read-serialized=false
		Error:
			org.springframework.dao.DataAccessResourceFailureException: remote server on SBIEPAYBD173(SpringBasedClientCacheApplication:7672:loner):53067:dae10297:SpringBasedClientCacheApplication: 
			org.apache.geode.SerializationException: While deserializing query result


Step 2:
		I have tried to use the ReflectionBasedAutoSerializer but still faced the same error 

		Implementation: 
			@Configuration
			@EnableEntityDefinedRegions(basePackageClasses = MerchantPayModeDownTimeCacheEntity.class)
			@EnableGemfireRepositories(basePackageClasses = DowntimeAPICacheRepository.class)
			public class GemFireConfiguration {

				@Bean("gemfireCache")
				public ClientCache clientCache() {
					return new ClientCacheFactory()
							.setPdxReadSerialized(false)
							.setPdxSerializer(new ReflectionBasedAutoSerializer("com.epay.transaction.entity.cache.*"))
							.create();
				}
			}

		Error:
			remote server on SBIEPAYBD173(25464:loner):56714:21f99597: org.apache.geode.SerializationException: While deserializing query result


Step 3: I have enabled the PdxReadSerialized but I will get the data in pdxInstance format. Right now we are handling pdxInstance by mapping the data.

		Implementation: 
			spring.data.gemfire.pdx.read-serialized=true

		Error:
			org.springframework.dao.DataAccessResourceFailureException: remote server on SBIEPAYBD173(SpringBasedClientCacheApplication:7672:loner):53067:dae10297:SpringBasedClientCacheApplication: 
			org.apache.geode.SerializationException: While deserializing query result

Step 4: I have followed MappingPdxSerializer and annotation EnablePdx but here also facing the same error

		Implementation:
			@Configuration
			@ClientCacheApplication
			@EnableEntityDefinedRegions(basePackageClasses = MerchantPayModeDownTimeCacheEntity.class)
			@EnableGemfireRepositories(basePackageClasses = DowntimeAPICacheRepository.class)
			@EnablePdx(serializerBeanName = "myCustomMappingPdxSerializer")
			public class GemFireConfiguration {

				@Bean
				MappingPdxSerializer myCustomMappingPdxSerializer() {

					MappingPdxSerializer customMappingPdxSerializer =
							MappingPdxSerializer.newMappingPdxSerializer();

					customMappingPdxSerializer.setIncludeTypeFilters(
							type -> Principal.class.isAssignableFrom(type));

					return customMappingPdxSerializer;
				}
			}
			
		Error:
			org.springframework.dao.DataAccessResourceFailureException: remote server on SBIEPAYBD173(SpringBasedClientCacheApplication:7672:loner):53067:dae10297:SpringBasedClientCacheApplication: 
			org.apache.geode.SerializationException: While deserializing query result

