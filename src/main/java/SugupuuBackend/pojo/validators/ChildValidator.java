package SugupuuBackend.pojo.validators;

import SugupuuBackend.exceptions.ApiRequestExcepiton;
import SugupuuBackend.model.Person;
import SugupuuBackend.pojo.PersonDto;
import SugupuuBackend.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ChildValidator implements IValidator {

    @Autowired
    PersonService personService;

    @Override
    public void validate(PersonDto child, Long personId) {
        // Find person from repository
        Optional<Person> person = personService.getPersonById(personId);

        if (person.isEmpty()) throw new ApiRequestExcepiton("Person not found!");
        else if (child.getAge() >= person.get().getAge()) throw new ApiRequestExcepiton("Child cant be older than parent!");
    }
}
