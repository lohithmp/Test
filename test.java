import org.springframework.data.annotation.Id;
import org.springframework.data.gemfire.mapping.annotation.Region;

import java.util.Objects;

@Region("MerchantRegion")  // Defines the region in GemFire
public class MerchantEntity {

    @Id  // Primary Key
    private String merchantId;
    private String name;
    private String country;

    // Constructors
    public MerchantEntity() {}

    public MerchantEntity(String merchantId, String name, String country) {
        this.merchantId = merchantId;
        this.name = name;
        this.country = country;
    }

    // Getters and Setters
    public String getMerchantId() { return merchantId; }
    public void setMerchantId(String merchantId) { this.merchantId = merchantId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    // Overriding equals()
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MerchantEntity that = (MerchantEntity) o;
        return Objects.equals(merchantId, that.merchantId);
    }

    // Overriding hashCode()
    @Override
    public int hashCode() {
        return Objects.hash(merchantId);
    }

    @Override
    public String toString() {
        return "MerchantEntity{" +
                "merchantId='" + merchantId + '\'' +
                ", name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", hashCode=" + this.hashCode() +  // Include hashCode in output
                '}';
    }
}




import org.springframework.data.gemfire.repository.GemfireRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MerchantRepository extends GemfireRepository<MerchantEntity, String> {
}







import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/merchant")
public class MerchantController {

    @Autowired
    private MerchantRepository merchantRepository;

    // Insert Merchant Data
    @PostMapping("/add")
    public MerchantEntity addMerchant(@RequestBody MerchantEntity merchant) {
        merchantRepository.save(merchant);
        return merchant;  // Returns saved entity with hashCode included in toString()
    }

    // Get Merchant by ID
    @GetMapping("/{id}")
    public Optional<MerchantEntity> getMerchant(@PathVariable String id) {
        return merchantRepository.findById(id);
    }

    // Get hashCode of a Merchant by ID
    @GetMapping("/hashcode/{id}")
    public String getMerchantHashCode(@PathVariable String id) {
        Optional<MerchantEntity> merchant = merchantRepository.findById(id);
        return merchant.map(value -> "HashCode: " + value.hashCode()).orElse("Merchant not found");
    }
}
