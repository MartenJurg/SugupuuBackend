package SugupuuBackend.pojo.validators;

import SugupuuBackend.pojo.PersonDto;
import org.springframework.stereotype.Component;

@Component
public interface IValidator {
    public void validate(PersonDto child, Long personId);
}
