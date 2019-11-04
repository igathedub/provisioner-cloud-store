package skyy.blue.repository;
import skyy.blue.domain.Features;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Features entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FeaturesRepository extends JpaRepository<Features, Long> {

}
