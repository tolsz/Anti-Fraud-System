package antifraud.Repositories;


import antifraud.Entities.Transaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Long> {

    Transaction findById(long id);

    List<Transaction> findByNumberAndDateBetween(String number, LocalDateTime startTime, LocalDateTime endTime);

    List<Transaction> findByNumber(String number);

    boolean existsByNumber(String number);
}
