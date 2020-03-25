package SugupuuBackend.repository;

import SugupuuBackend.model.FamilyTree;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FamilyTreeRepository extends JpaRepository<FamilyTree, Long> {

}
