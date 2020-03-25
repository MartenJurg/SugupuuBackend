package SugupuuBackend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Only one current partner allowed")
public class PartnerAlreadyExistsException extends RuntimeException {
}