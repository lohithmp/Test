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
    public ResponseDto<String> saveMerchant(String mId, MerchantCacheEntity merchant) {
        Optional<MerchantCacheEntity> optionalMerchant = merchantCacheRepository.findById(mId);

        if (optionalMerchant.isPresent()) {
            return ResponseDto.<String>builder().status(Status.FAIL.getValue()).total(0L).count(0L).data(List.of()).errors(List.of(ErrorDto.builder().errorCode(ErrorConstants.ALREADY_EXIST_ERROR_CODE).errorMessage(MessageFormat.format(ErrorConstants.ALREADY_EXIST_ERROR_MESSAGE, "Mid")).reason(MessageFormat.format(ErrorConstants.ALREADY_EXIST_ERROR_MESSAGE, "Mid")).build())).build();
        }
        try{
            MerchantCacheEntity merchantCacheEntity =  merchantCacheRepository.save(merchant);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return ResponseDto.<String>builder().status(Status.SUCCESS.getValue()).total(1L).count(1L).errors(List.of()).data(List.of("Merchant Data saved successfully.")).build();
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


@Repository
public interface MerchantCacheRepository extends CrudRepository<MerchantCacheEntity, String> {

    @Query("SELECT m.merchantName, m.countryCode FROM /Merchant m WHERE m.merchantName =:merchantName")
    Optional<MerchantCacheEntity> findMerchantNameAndCountry(String merchantName);
}


@Configuration

@ClientCacheApplication
@EnableEntityDefinedRegions(basePackageClasses = MerchantCacheEntity.class ,clientRegionShortcut = ClientRegionShortcut.LOCAL)
@EnableGemfireRepositories(basePackageClasses = MerchantCacheRepository.class)
public class CacheConfig {


    @Bean
    public ClientRegionFactoryBean<String, MerchantCacheEntity> merchantRegion(GemFireCache gemFireCache) {
        ClientRegionFactoryBean<String, MerchantCacheEntity> merchantRegionFactory = new ClientRegionFactoryBean<>();
        merchantRegionFactory.setCache((ClientCache) gemFireCache);
        merchantRegionFactory.setRegionName("Merchants");
        merchantRegionFactory.setShortcut(ClientRegionShortcut.LOCAL);
        return merchantRegionFactory;
    }
}
