package skyy.blue.repository;
import skyy.blue.domain.NetKeyIndex;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the NetKeyIndex entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NetKeyIndexRepository extends JpaRepository<NetKeyIndex, Long> {

}
