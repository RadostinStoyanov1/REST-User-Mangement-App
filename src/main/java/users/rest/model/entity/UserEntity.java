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

    public UserEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserEntity setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserEntity setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public UserEntity setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public UserEntity setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserEntity setEmail(String email) {
        this.email = email;
        return this;
    }

}
