package SugupuuBackend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Child cannot be older than parent")
public class ChildTooOldException extends RuntimeException {
}