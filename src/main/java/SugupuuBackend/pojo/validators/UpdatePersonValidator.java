package SugupuuBackend.pojo.validators;

import SugupuuBackend.exceptions.InvalidAgeInsertedException;
import SugupuuBackend.exceptions.PartnerAlreadyExistsException;
import SugupuuBackend.exceptions.PersonNotFoundException;
import SugupuuBackend.model.PartnerConnection;
import SugupuuBackend.model.Person;
import SugupuuBackend.model.PersonToChildConnection;
import SugupuuBackend.pojo.PersonDto;
import SugupuuBackend.service.PartnerConnectionService;
import SugupuuBackend.service.PersonService;
import SugupuuBackend.service.PersonToChildConnectionService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@NoArgsConstructor
public class UpdatePersonValidator {


    @Autowired
    PersonService personService;

    @Autowired
    PartnerConnectionService partnerConnectionService;

    @Autowired
    PersonToChildConnectionService personToChildConnectionService;

    public void validate(PersonDto personDto, Long personId) {
        // Find person from repository
        Optional<Person> person = personService.getPersonById(personId);
        if (person.isEmpty()) throw new PersonNotFoundException();

        // Check that children are not older than person
        List<Person> children = getChildren(personId);
        for (Person child : children) {
            if (child.getAge() >= person.get().getAge()) throw new InvalidAgeInsertedException();
        }

        // Check that parents are not younger than person
        List<Person> parents = getParents(personId);
        for (Person parent : parents) {
            if (parent.getAge() <= person.get().getAge()) throw new InvalidAgeInsertedException();
        }
    }

    public List<Person> getParents(Long id) {
        List<PersonToChildConnection> connections = personToChildConnectionService.getConnectionsByChildId(id);
        if (connections.isEmpty()) return new ArrayList<>();
        return personService.getParents(id, connections);
    }

    public List<Person> getChildren(Long id) {
        List<PersonToChildConnection> connections = personToChildConnectionService.getConnectionsByPersonId(id);
        if (connections.isEmpty()) return new ArrayList<>();
        return personService.getChildren(id, connections);
    }
}
