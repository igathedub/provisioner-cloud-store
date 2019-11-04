package skyy.blue.repository;
import skyy.blue.domain.NetworkConfiguration;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the NetworkConfiguration entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NetworkConfigurationRepository extends JpaRepository<NetworkConfiguration, Long> {

}
