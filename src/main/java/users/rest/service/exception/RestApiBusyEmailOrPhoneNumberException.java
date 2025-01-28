package users.rest.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class RestApiBusyEmailOrPhoneNumberException extends RuntimeException {
    private final String email;
    private final String phone;

    public RestApiBusyEmailOrPhoneNumberException(String message, String email, String phone) {
        super(message);
        this.email = email;
        this.phone = phone;
    }

    public String getId() {
        return email;
    }

    public String getPhone() {
        return phone;
    }
}
