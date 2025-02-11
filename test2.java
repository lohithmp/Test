package com.example.gemfire;

import org.apache.geode.cache.Region;
import org.apache.geode.pdx.PdxInstance;
import org.apache.geode.cache.client.ClientCache;
import org.apache.geode.cache.client.ClientRegionShortcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.gemfire.config.annotation.ClientCacheApplication;
import org.springframework.data.gemfire.config.annotation.EnableEntityDefinedRegions;
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;

import java.util.Optional;

@Configuration
@ClientCacheApplication
public class GemfireCacheManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(GemfireCacheManager.class);

    private final ClientCache cache;

    @Value("${gemfire.domain.package.path}")
    private String domainPackagePath;  // Dynamic domain package path

    @Value("${gemfire.repository.package.path}")
    private String repositoryPackagePath;  // Dynamic repository package path

    @Autowired
    public GemfireCacheManager(ClientCache cache) {
        this.cache = cache;
    }

    /**
     * Setup dynamic configuration after initialization.
     * This method will be used to dynamically configure scanning paths for regions and repositories.
     */
    @PostConstruct
    public void setupDynamicConfiguration() {
        LOGGER.info("Configuring GemFire with domain package: {}, repository package: {}", domainPackagePath, repositoryPackagePath);
        
        // Initialize dynamic package scanning for domain and repository based on injected paths
        System.setProperty("gemfire.domain.package.path", domainPackagePath);
        System.setProperty("gemfire.repository.package.path", repositoryPackagePath);
    }

    /**
     * Dynamically enable Entity Defined Regions with the provided domain package path.
     */
    @EnableEntityDefinedRegions(basePackage = "${gemfire.domain.package.path}")  // Dynamic domain package path
    @EnableGemfireRepositories(basePackage = "${gemfire.repository.package.path}")  // Dynamic repository package path
    @ComponentScan(basePackages = "${gemfire.base.package.scan}") // Dynamic scanning for the entire service
    public static class GemfireConfiguration {
    }

    public <K, V> Region<K, V> getRegion(String regionName) {
        return cache.<K, V>createClientRegionFactory(ClientRegionShortcut.PROXY).create(regionName);
    }

    public <K, V> Optional<V> getFromCache(String regionName, K key, Class<V> type) {
        try {
            Region<K, V> region = getRegion(regionName);
            Object value = region.get(key);

            if (value instanceof PdxInstance) {
                V deserializedValue = ((PdxInstance) value).getObject();
                LOGGER.info("Deserialized PDX object for key={}", key);
                return Optional.ofNullable(deserializedValue);
            }
            return Optional.ofNullable(value);

        } catch (Exception e) {
            handleException("retrieving value from cache", e);
            return Optional.empty();
        }
    }

    public <K, V> void putToCache(String regionName, K key, V value) {
        try {
            Region<K, V> region = getRegion(regionName);
            region.put(key, value);
            LOGGER.info("Successfully put key={} into region={}", key, regionName);
        } catch (Exception e) {
            handleException("putting value to cache", e);
        }
    }

    public <K> void removeFromCache(String regionName, K key) {
        try {
            Region<K, ?> region = getRegion(regionName);
            region.remove(key);
            LOGGER.info("Successfully removed key={} from region={}", key, regionName);
        } catch (Exception e) {
            handleException("removing value from cache", e);
        }
    }

    public void closeCache() {
        if (cache != null && !cache.isClosed()) {
            cache.close();
            LOGGER.info("GemFire client cache closed.");
        }
    }

    private void handleException(String operation, Exception e) {
        if (e.getMessage().contains("connection refused") || e.getMessage().contains("not connected")) {
            LOGGER.error("GemFire connection issue while {}: {}", operation, e.getMessage());
        } else {
            LOGGER.error("Error {}: {}", operation, e.getMessage(), e);
        }
    }
}
