package skyy.blue.repository;
import skyy.blue.domain.Publish;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Publish entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PublishRepository extends JpaRepository<Publish, Long> {

}
