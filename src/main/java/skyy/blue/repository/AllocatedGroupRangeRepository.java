package skyy.blue.repository;
import skyy.blue.domain.AllocatedGroupRange;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AllocatedGroupRange entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AllocatedGroupRangeRepository extends JpaRepository<AllocatedGroupRange, Long> {

}
