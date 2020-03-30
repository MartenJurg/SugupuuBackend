package SugupuuBackend.pojo.validators;

import SugupuuBackend.exceptions.PartnerAlreadyExistsException;
import SugupuuBackend.exceptions.PersonNotFoundException;
import SugupuuBackend.model.PartnerConnection;
import SugupuuBackend.model.Person;
import SugupuuBackend.pojo.PersonDto;
import SugupuuBackend.repository.PersonRepository;
import SugupuuBackend.service.PartnerConnectionService;
import SugupuuBackend.service.PersonService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
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

        if (person.isEmpty()) throw new PersonNotFoundException();
        if (currentPartner.isPresent()) throw new PartnerAlreadyExistsException();
    }
}
