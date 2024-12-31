Caused by: org.springframework.beans.BeanInstantiationException: Failed to instantiate [org.springframework.data.gemfire.client.ClientRegionFactoryBean]: Factory method 'merchantRegion' threw exception with message: A connection to a distributed system already exists in this VM.  It has the following configuration:  ack-severe-alert-threshold="0"



package com.epay.admin.config;

import com.epay.admin.entity.cache.MerchantCacheEntity;
import org.apache.geode.cache.GemFireCache;
import org.apache.geode.cache.client.ClientCache;
import org.apache.geode.cache.client.ClientCacheFactory;
import org.apache.geode.cache.client.ClientRegionShortcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
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
@ClientCacheApplication
@EnableEntityDefinedRegions(basePackages = "com/epay/admin/entity/cache")
@EnableGemfireRepositories(basePackages = "com/epay/admin/repository/cache")
public class GemFireConfiguration  {

    Logger logger = LoggerFactory.getLogger(GemFireConfiguration.class);

    @Value("${cache.factory.PoolLocator}")
    private String poolLocatorPath;

    @Value("${cache.factory.port}")
    private Integer poolLocatorPort;

    @Value("${log.level}")
    private String logLevel;

    @Value("${ssl.enabled.components}")
    private String sslEnabledComponents;

    @Value("${ssl.keystore}")
    private String sslKeystore;

    @Value("${ssl.keystore.password}")
    private String sslKeystorePassword;

    @Value("${ssl.keystore.type}")
    private String sslKeystoreType;

    @Value("${ssl.truststore}")
    private String sslTruststore;

    @Value("${ssl.truststore.password}")
    private String sslTruststorePassword;

    @Value("${ssl.truststore.type}")
    private String sslTruststoreType;

    @Value("${ssl.endpoint.identification.enabled}")
    private String sslEndpointIdentificationEnabled;

    @Value("${ssl.require.authentication}")
    private String sSLRequireAuthentication;

    @Value("${ssl.web.require.authentication}")
    private String sSLWebRequireAuthentication;

    @Value("${ssl.protocols}")
    private String sslProtocols;

    @Value("${ssl.ciphers}")
    private String sslCiphers;

    /*@Value("${cache.region.name}")
    private String cacheRegionName;*/

    public ClientCache clientCacheConfig() {
        return new ClientCacheFactory()
                .addPoolLocator(poolLocatorPath, poolLocatorPort)
                //.addPoolServer("gemfireserver.apps.dev.sbiepay.sbi",443)
                .set("log-level", logLevel)
                //.set("log-level","info")
                // .set("ssl-endpoint-identification-enabled",sslEndpointIdentificationEnabled)
                //.set("ssl-enabled-components",sslEnabledComponents)
                .set("ssl-keystore", sslKeystore)
                .set("ssl-keystore-password", sslKeystorePassword)
                .set("ssl-keystore-type", sslTruststoreType)
                .set("ssl-truststore", sslTruststore)
                .set("ssl-truststore-password", sslTruststorePassword)
                .set("ssl-truststore-type", sslTruststoreType)
                .set("ssl-protocols", sslProtocols)
                .set("ssl-ciphers", sslCiphers)
                .set("ssl-require-authentication", sSLRequireAuthentication)
                .set("ssl-web-require-authentication", sSLWebRequireAuthentication)
                .create();
    }

//    @Bean
//    public ClientCacheFactoryBean clientCache() {
//        ClientCacheFactoryBean cacheFactoryBean = new ClientCacheFactoryBean();
//
//        cacheFactoryBean.setServers(Collections.singleton(new ConnectionEndpoint("localhost", 40404)));
//        return cacheFactoryBean;
//    }

    @Bean
    public ClientRegionFactoryBean<String, MerchantCacheEntity> merchantRegion(GemFireCache gemFireCache) {
       // ClientRegionFactoryBean<String, MerchantCacheEntity> merchantRegionFactory = new ClientRegionFactoryBean<>();
        ClientRegionFactoryBean<String, MerchantCacheEntity> merchantRegionFactory = (ClientRegionFactoryBean<String, MerchantCacheEntity>) clientCacheConfig();
        merchantRegionFactory.setCache((ClientCache) gemFireCache);
        merchantRegionFactory.setShortcut(ClientRegionShortcut.PROXY);
        return merchantRegionFactory;
    }
}
