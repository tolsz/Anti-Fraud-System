package antifraud.Repositories;

import antifraud.Entities.StolenCard;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Component
@Repository
public interface StolenCardRepository extends CrudRepository<StolenCard, Long> {
    Optional<StolenCard> findByNumber(String number);

    boolean existsByNumber(String number);

    void deleteByNumber(String number);
}
