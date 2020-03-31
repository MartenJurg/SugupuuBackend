package SugupuuBackend.exceptions;

public class ApiRequestExcepiton extends RuntimeException {

    public ApiRequestExcepiton(String message, Throwable cause) {
        super(message, cause);
    }

    public ApiRequestExcepiton(String message) {
        super(message);
    }
}
