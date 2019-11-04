package skyy.blue.repository;
import skyy.blue.domain.AllocatedRange;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AllocatedRange entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AllocatedRangeRepository extends JpaRepository<AllocatedRange, Long> {

}
