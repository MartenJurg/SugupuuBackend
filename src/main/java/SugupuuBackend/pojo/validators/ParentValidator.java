package SugupuuBackend.pojo.validators;

import SugupuuBackend.exceptions.ApiRequestExcepiton;
import SugupuuBackend.model.Person;
import SugupuuBackend.model.PersonToChildConnection;
import SugupuuBackend.pojo.PersonDto;
import SugupuuBackend.service.PersonService;
import SugupuuBackend.service.PersonToChildConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ParentValidator implements IValidator {

    @Autowired
    PersonService personService;

    @Autowired
    PersonToChildConnectionService personToChildConnectionService;

    @Override
    public void validate(PersonDto parent, Long personId) {
        // Find person from repository
        Optional<Person> person = personService.getPersonById(personId);
        // Find all the parents for this person.
        List<PersonToChildConnection> parents = personToChildConnectionService.getConnectionsByChildId(personId);

        if (person.isEmpty()) throw new ApiRequestExcepiton("Person not found!");
        else if (parent.getAge() < person.get().getAge()) throw new ApiRequestExcepiton("Parent is too young!");
        else if (parents.size() >= 2) throw new ApiRequestExcepiton("Person already ahs 2 parents!");
    }
}
