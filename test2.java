Caused by: org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'merchantCacheRepository' defined in com.epay.admin.repository.cache.MerchantCacheRepository defined in @EnableGemfireRepositories declared on CacheConfig: Error creating bean with name 'merchantRegion' defined in class path resource [com/epay/admin/config/CacheConfig.class]: Cache is required

Caused by: org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'merchantRegion' defined in class path resource [com/epay/admin/config/CacheConfig.class]: Cache is required

Caused by: java.lang.IllegalArgumentException: Cache is required
