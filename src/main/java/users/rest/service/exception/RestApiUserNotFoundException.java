package users.rest.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class RestApiUserNotFoundException extends RuntimeException {
    private final UUID id;

    public RestApiUserNotFoundException(String message, UUID id) {
        super(message);
        this.id = id;
    }

    public UUID getId() {
        return id;
    }
}
