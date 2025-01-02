Parameter 0 of constructor in com.epay.transaction.dao.DowntimeAPIDao required a bean of type 'com.epay.transaction.repository.cache.DowntimeAPICacheRepository' that could not be found.


Action:

Consider defining a bean of type 'com.epay.transaction.repository.cache.DowntimeAPICacheRepository' in your configuration.



	
import com.epay.transaction.entity.cache.CustomerCacheEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CustomerCacheRepository extends CrudRepository<CustomerCacheEntity, String> {
}



@Service
@RequiredArgsConstructor
public class DowntimeAPIService {

//    private final DowntimeAPIDao downtimeAPIDao;
    /**
     * This methods contains transaction downtime api details.
     *
     * @return list of transaction downtime api details.
     */

//    private final DowntimeAPICacheRepository downtimeAPICacheRepository;
    private final CustomerCacheRepository customerCacheRepository;


    public Optional<CustomerCacheEntity> getCustomer() {
        Optional<CustomerCacheEntity> cacheEntity = customerCacheRepository.findById("4");
        return cacheEntity;
    }

}
