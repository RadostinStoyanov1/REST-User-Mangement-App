package users.rest.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class RestApiUserDataConflictException extends RuntimeException{
    private final Object id;

    public RestApiUserDataConflictException(String message, Object id) {
        super(message);
        this.id = id;
    }

    public Object getId() {
        return id;
    }
}
