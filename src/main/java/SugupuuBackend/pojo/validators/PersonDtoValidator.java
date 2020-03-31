package SugupuuBackend.pojo.validators;

import SugupuuBackend.exceptions.ApiRequestExcepiton;
import SugupuuBackend.pojo.PersonDto;
import org.springframework.stereotype.Component;

@Component
public class PersonDtoValidator {


    public void validate(PersonDto personDto) {
        if (personDto.getFirstName() == null || personDto.getFirstName().equals("")) throw new ApiRequestExcepiton("Incorrect name!");
        if (personDto.getLastName() == null || personDto.getLastName().equals("")) throw new ApiRequestExcepiton("Incorrect name!");
        if (personDto.getAge() == 0) throw new ApiRequestExcepiton("Incorrect age!");
    }
}
