
    private List<CardOnboardingCache> fetchCardOnBoardDetails(String mId) {
        try {
            return mapPdxList(cardOnboardingCacheRepository.findBymId(mId));
        } catch (Exception e) {
            logger.error("GemFire cache lookup failed or GemFire server down. Reason: {}", e.getMessage());
        }
        return Collections.emptyList();
    }    


@Repository
public interface CardOnboardingCacheRepository extends CrudRepository<CardOnboardingCache, Long> {

    List<PdxInstance> findBymId(String mId);

}

public static <T> List<T> mapPdxList(List<PdxInstance> pdxInstances) {
        return (List<T>) pdxInstances.stream().map(PdxInstance::getObject).toList();
    }

this code will work
 

same like this i want for single object non list


    public Optional<MerchantNotificationCache> fetchNotificationViewByMid(String mId) {
        try {
            Optional<MerchantNotificationCache> merchantCacheDetails =  merchantNotificationCacheRepository.findById(mId);
            return Optional.ofNullable((MerchantNotificationCache) ((PdxInstance) merchantCacheDetails.get()).getObject());
        } catch (Exception e) {
            logger.error("GemFire cache lookup failed or GemFire server down. Reason: {}", e.getMessage());
        }
        return Optional.empty();
    }



@NoRepositoryBean
public interface CrudRepository<T, ID> extends Repository<T, ID> {
    <S extends T> S save(S entity);

    <S extends T> Iterable<S> saveAll(Iterable<S> entities);

    Optional<T> findById(ID id);

    boolean existsById(ID id);

    Iterable<T> findAll();

    Iterable<T> findAllById(Iterable<ID> ids);

    long count();

    void deleteById(ID id);

    void delete(T entity);

    void deleteAllById(Iterable<? extends ID> ids);

    void deleteAll(Iterable<? extends T> entities);

    void deleteAll();
}

