package SugupuuBackend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Person cannot have more than two parents")
public class TooManyParentsException extends RuntimeException {
}