package SugupuuBackend.pojo.validators;

import SugupuuBackend.exceptions.ParentTooYoungException;
import SugupuuBackend.exceptions.PersonNotFoundException;
import SugupuuBackend.exceptions.TooManyParentsException;
import SugupuuBackend.model.Person;
import SugupuuBackend.model.PersonToChildConnection;
import SugupuuBackend.pojo.PersonDto;
import SugupuuBackend.service.PersonService;
import SugupuuBackend.service.PersonToChildConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
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

        if (person.isEmpty()) throw new PersonNotFoundException();
        else if (parent.getAge() < person.get().getAge()) throw new ParentTooYoungException();
        else if (parents.size() >= 2) throw new TooManyParentsException();
    }
}
