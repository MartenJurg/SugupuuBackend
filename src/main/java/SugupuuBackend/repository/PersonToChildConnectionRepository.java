package SugupuuBackend.repository;

import SugupuuBackend.model.PersonToChildConnection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonToChildConnectionRepository extends JpaRepository<PersonToChildConnection, Long> {

    List<PersonToChildConnection> findByPersonId(Long id);
    List<PersonToChildConnection> findByChildId(Long id);
}
