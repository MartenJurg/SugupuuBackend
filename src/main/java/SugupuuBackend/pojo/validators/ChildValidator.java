package SugupuuBackend.pojo.validators;

import SugupuuBackend.exceptions.ChildTooOldException;
import SugupuuBackend.exceptions.PersonNotFoundException;
import SugupuuBackend.model.Person;
import SugupuuBackend.pojo.PersonDto;
import SugupuuBackend.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class ChildValidator implements IValidator {

    @Autowired
    PersonService personService;

    @Override
    public boolean validate(PersonDto child, Long personId) {
        // Find person from repository
        Optional<Person> person = personService.getPersonById(personId);

        if (person.isEmpty()) throw new PersonNotFoundException();
        else if (child.getAge() > person.get().getAge()) throw new ChildTooOldException();
        return true;
    }
}
