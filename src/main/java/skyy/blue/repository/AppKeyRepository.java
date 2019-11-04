package skyy.blue.repository;
import skyy.blue.domain.AppKey;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AppKey entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AppKeyRepository extends JpaRepository<AppKey, Long> {

}
