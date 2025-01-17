package users.rest.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class UpdateUserDTO {

    @NotEmpty
    private Long id;

    @NotEmpty
    @Size(min = 2, max = 30)
    private String firstName;

    @NotEmpty
    @Size(min = 2, max = 30)
    private String lastName;

    private LocalDate birthDate;

    @Pattern(regexp = "[0-9]+")
    @Size(min = 10, max = 10)
    private String phoneNumber;

    @Email(regexp = ".*@.*")
    private String email;

    public Long getId() {
        return id;
    }

    public UpdateUserDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public UpdateUserDTO setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UpdateUserDTO setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public UpdateUserDTO setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public UpdateUserDTO setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UpdateUserDTO setEmail(String email) {
        this.email = email;
        return this;
    }
}
