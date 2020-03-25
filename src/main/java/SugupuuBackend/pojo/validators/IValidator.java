package SugupuuBackend.pojo.validators;

import SugupuuBackend.pojo.PersonDto;

public interface IValidator {
    public boolean validate(PersonDto child, Long personId);
}
