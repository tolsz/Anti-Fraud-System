package antifraud.Repositories;

import antifraud.Entities.TransactionLimit;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Component
@Repository
public interface TransactionLimitsRepository extends CrudRepository<TransactionLimit, Long> {
    TransactionLimit findByType(String type);

    boolean existsByType(String type);
}
