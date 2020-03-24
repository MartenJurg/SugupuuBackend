package SugupuuBackend.repository;

import SugupuuBackend.model.Connection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConnectionRepository extends JpaRepository<Connection, Long> {

    List<Connection> findByFirstPersonId(Long id);
    List<Connection> findBySecondPersonId(Long id);
}
