package SugupuuBackend.pojo.validators;

import SugupuuBackend.exceptions.ApiRequestExcepiton;
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
        if (person.isEmpty()) throw new ApiRequestExcepiton("Person Not Found");

        // Check that children are not older than person
        List<Person> children = getChildren(personId);
        for (Person child : children) {
            if (child.getAge() >= personDto.getAge()) throw new ApiRequestExcepiton("Incorrect age!");
        }

        // Check that parents are not younger than person
        List<Person> parents = getParents(personId);
        for (Person parent : parents) {
            if (parent.getAge() <= personDto.getAge()) throw new ApiRequestExcepiton("Incorrect age!");
        }
    }

    public List<Person> getParents(Long id) {
        List<PersonToChildConnection> connections = personToChildConnectionService.getConnectionsByChildId(id);
        if (connections.isEmpty()) return new ArrayList<>();
        return personService.getParents(id);
    }

    public List<Person> getChildren(Long id) {
        List<PersonToChildConnection> connections = personToChildConnectionService.getConnectionsByPersonId(id);
        if (connections.isEmpty()) return new ArrayList<>();
        return personService.getChildren(id);
    }
}
