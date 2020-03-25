package SugupuuBackend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Parent has to be older than child")
public class ParentTooYoungException extends RuntimeException {
}