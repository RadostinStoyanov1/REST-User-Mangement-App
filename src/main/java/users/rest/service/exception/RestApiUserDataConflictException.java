package users.rest.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class RestApiUserDataConflictException extends RuntimeException{
    private final Object firstName;

    public RestApiUserDataConflictException(String message, Object firstName) {
        super(message);
        this.firstName = firstName;
    }

    public Object getFirstName() {
        return firstName;
    }
}
