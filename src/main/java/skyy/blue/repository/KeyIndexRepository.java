package skyy.blue.repository;
import skyy.blue.domain.KeyIndex;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the KeyIndex entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KeyIndexRepository extends JpaRepository<KeyIndex, Long> {

}
