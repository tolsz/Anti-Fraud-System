package antifraud.Repositories;

import antifraud.Entities.SuspiciousIP;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Component
@Repository
public interface SuspiciousIPRepository extends CrudRepository<SuspiciousIP, Long> {
    Optional<SuspiciousIP> findByIp(String ip);

    boolean existsByIp(String ip);

    void deleteByIp(String ip);

}
