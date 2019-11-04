package skyy.blue.repository;
import skyy.blue.domain.Retransmit;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Retransmit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RetransmitRepository extends JpaRepository<Retransmit, Long> {

}
