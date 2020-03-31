package SugupuuBackend.repository;

import SugupuuBackend.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    List<Person> getAllByFamilyTreeId(Long familyTreeId);
    List<Person> getAllByFirstNameContainingAndFamilyTreeId(@NotBlank String firstName, @NotNull Long familyTreeId);

}
