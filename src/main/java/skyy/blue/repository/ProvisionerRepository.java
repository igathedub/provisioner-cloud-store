package skyy.blue.repository;
import skyy.blue.domain.Provisioner;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Provisioner entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProvisionerRepository extends JpaRepository<Provisioner, Long> {

}
