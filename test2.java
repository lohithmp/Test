package com.epay.admin.config;

import com.epay.admin.entity.cache.MerchantCacheEntity;
import org.apache.geode.cache.client.ClientRegionShortcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.gemfire.client.ClientCacheFactoryBean;
import org.springframework.data.gemfire.client.ClientRegionFactoryBean;
import org.springframework.data.gemfire.config.annotation.ClientCacheApplication;
import org.springframework.data.gemfire.config.annotation.EnableEntityDefinedRegions;
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories;
import org.springframework.data.gemfire.support.ConnectionEndpoint;

import java.util.Collections;

@Configuration
//@ClientCacheApplication(name = "ClientCacheApp", servers = {
//        @ClientCacheApplication.Server(host = "gemfire-server", port = 40404)
//})
@EnableEntityDefinedRegions(basePackages = "com.epay.admin.entity.cache" )
@EnableGemfireRepositories(basePackages = "com.epay.admin.repository.cache")
public class CacheConfig {
    //TODO: Gemfire configuration with client-server along with dev-cluster(using openshift and kubernets.)

    @Bean
    public ClientCacheFactoryBean clientCache() {
        ClientCacheFactoryBean clientCacheFactoryBean = new ClientCacheFactoryBean();
        clientCacheFactoryBean.setClose(true);
        clientCacheFactoryBean.setServers(Collections.singleton(new ConnectionEndpoint("gemfire-server", 40404)));

        return clientCacheFactoryBean;
    }

    @Bean
    public ClientRegionFactoryBean<String, MerchantCacheEntity> merchantRegion() {
        ClientRegionFactoryBean<String, MerchantCacheEntity> merchantRegionFactory = new ClientRegionFactoryBean<>();
        merchantRegionFactory.setRegionName("merchants");
        merchantRegionFactory.setShortcut(ClientRegionShortcut.LOCAL);
        return merchantRegionFactory;
    }
}


ERROR




Error starting ApplicationContext. To display the condition evaluation report re-run your application with 'debug' enabled.
2024-12-26T17:34:27.339+05:30 ERROR 23616 --- [Epay_Admin_service] [           main] o.s.b.d.LoggingFailureAnalysisReporter   : 

***************************
APPLICATION FAILED TO START
***************************

Description:

A component required a bean named 'gemfireCache' that could not be found.


Action:

Consider defining a bean named 'gemfireCache' in your configuration.


> Task :com.epay.admin.EpayAdminServiceApplication.main() FAILED
