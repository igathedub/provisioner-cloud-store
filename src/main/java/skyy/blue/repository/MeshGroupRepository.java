package skyy.blue.repository;
import skyy.blue.domain.MeshGroup;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the MeshGroup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MeshGroupRepository extends JpaRepository<MeshGroup, Long> {

}
