package users.rest.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "first_name", nullable = false)
    @NotEmpty
    @Size(min = 2, max = 30)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    @NotEmpty
    @Size(min = 2, max = 30)
    private String lastName;
    @Column(name = "birth_date", nullable = false)
    @Past
    private LocalDate birthDate;
    @Column(name = "phone_number", nullable = false, unique = true)
    @Pattern(regexp = "[0-9]+")
    @Size(min = 10, max = 10)
    private String phoneNumber;
    @Column(name = "email", nullable = false, unique = true)
    @Email
    private String email;

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

}
