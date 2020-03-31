package SugupuuBackend.pojo.validators;

import SugupuuBackend.exceptions.ApiRequestExcepiton;
import SugupuuBackend.model.PartnerConnection;
import SugupuuBackend.model.Person;
import SugupuuBackend.pojo.PersonDto;
import SugupuuBackend.service.PartnerConnectionService;
import SugupuuBackend.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PartnerValidator implements IValidator {

    @Autowired
    PersonService personService;

    @Autowired
    PartnerConnectionService partnerConnectionService;

    public PartnerValidator() {
    }

    public void validate(PersonDto partner, Long personId) {
        // Find person from repository
        Optional<Person> person = personService.getPersonById(personId);

        // Find current partner
        Optional<PartnerConnection> currentPartner = partnerConnectionService.getCurrentPartnerConnection(personId);

        if (person.isEmpty()) throw new ApiRequestExcepiton("Person not found!");
        if (currentPartner.isPresent()) throw new ApiRequestExcepiton("Partner already exists!");
    }
}
