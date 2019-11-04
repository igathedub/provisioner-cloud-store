package skyy.blue.repository;
import skyy.blue.domain.AllocatedSceneRange;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AllocatedSceneRange entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AllocatedSceneRangeRepository extends JpaRepository<AllocatedSceneRange, Long> {

}
