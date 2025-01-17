package users.rest.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.METHOD_NOT_ALLOWED)
public class RestApiBusyEmailOrPhoneNumberException extends RuntimeException {
    private final Object email;
    private final Object phone;

    public RestApiBusyEmailOrPhoneNumberException(String message, Object email, Object phone) {
        super(message);
        this.email = email;
        this.phone = phone;
    }

    public Object getId() {
        return email;
    }

    public Object getPhone() {
        return phone;
    }
}
