package skyy.blue.repository;
import skyy.blue.domain.AllocatedUnicastRange;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AllocatedUnicastRange entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AllocatedUnicastRangeRepository extends JpaRepository<AllocatedUnicastRange, Long> {

}
