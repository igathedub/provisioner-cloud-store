package skyy.blue.repository;
import skyy.blue.domain.Element;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Element entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ElementRepository extends JpaRepository<Element, Long> {

}
