package skyy.blue.repository;
import skyy.blue.domain.NetKey;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the NetKey entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NetKeyRepository extends JpaRepository<NetKey, Long> {

}
