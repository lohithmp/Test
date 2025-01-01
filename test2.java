Caused by: org.springframework.core.convert.ConverterNotFoundException: No converter found capable of converting from type [org.apache.geode.pdx.internal.PdxInstanceImpl] to type [@org.springframework.data.gemfire.repository.Query com.epay.transaction.entity.cache.MerchantPayModeDownTimeCacheEntity]


@Repository
public interface DowntimeAPICacheRepository extends CrudRepository<MerchantPayModeDownTimeCacheEntity, String> {

        @Query("SELECT payModeDownTime FROM /AGG_PayGateway_DownTime_DTLS payModeDownTime WHERE payModeDownTime.status = $1")
        List<MerchantPayModeDownTimeCacheEntity> findByStatusAndRecordStatus(String status);

}

@Component
@AllArgsConstructor
public class DowntimeAPIDao {
    private final LoggerUtility logger = LoggerFactoryUtility.getLogger(DowntimeAPIDao.class);
    private final DowntimeAPICacheRepository downtimeAPICacheRepository;
    private final AdminServicesClient adminServicesClient;

    public List<DownTimeDTO> getDowntimeAPIs(String status) {
        List<MerchantPayModeDownTimeCacheEntity> downtimeAPIs = downtimeAPICacheRepository.findByStatusAndRecordStatus(status);
        List<DownTimeDTO> downTimeDTOList = new ArrayList<>();
        for(MerchantPayModeDownTimeCacheEntity merchantPayModeDownTime : downtimeAPIs){
            downTimeDTOList.add(DownTimeDTO.builder().gatewayId(merchantPayModeDownTime.getId()).startTimestamp(merchantPayModeDownTime.getStartTimestamp()).endTimestamp(merchantPayModeDownTime.getEndTimestamp()).status(merchantPayModeDownTime.getStatus()).payModeCode(merchantPayModeDownTime.getPayModeCode()).errorMessage(merchantPayModeDownTime.getErrorMessage()).build());
        }
        return downTimeDTOList;
    }
}



@Data
@Region("AGG_PayGateway_DownTime_DTLS")
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



@Configuration
@ClientCacheApplication
@EnableEntityDefinedRegions(basePackages = "com/epay/transaction/entity/cache")
@EnableGemfireRepositories(basePackages = "com/epay/transaction/repository/cache")
public class GemFireConfiguration {
    public ClientCache clientCacheConfig() {
        return new ClientCacheFactory()
                .addPoolLocator("SBIEPAYBD173.CORP.AD.SBI", 10334)
                .addPoolServer("10.30.64.173", 40404)
                .create();
    }


    @Bean
    public ClientRegionFactoryBean<String, MerchantPayModeDownTimeCacheEntity> merchantRegion(GemFireCache gemFireCache) {
        ClientRegionFactoryBean<String, MerchantPayModeDownTimeCacheEntity> merchantRegionFactory = new ClientRegionFactoryBean<>();
        merchantRegionFactory.setCache((ClientCache) gemFireCache);
        merchantRegionFactory.setShortcut(ClientRegionShortcut.PROXY);
        return merchantRegionFactory;
    }
}


APPLICATION.PROPERTIES

spring.data.gemfire.locators=localhost[10334]
spring.data.gemfire.cache.server.port=40404
spring.data.gemfire.cache.region.static=true

spring.data.gemfire.pdx.read-serialized=true
spring.data.gemfire.pdx.auto-serializable-classes=C:/Users/V1014352/Epay/epay_transaction_service/src/main/java/com/epay/transaction/entity/cache/MerchantPayModeDownTimeCacheEntity.java
spring.data.gemfire.cache.region.region-name=AGG_PayGateway_DownTime_DTLS
spring.data.gemfire.logging.level=info

logging.level.org.apache.geode=debug
logging.level.org.springframework.data.gemfire=debug
