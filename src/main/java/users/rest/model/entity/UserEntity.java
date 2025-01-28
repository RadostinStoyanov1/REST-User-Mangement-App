package users.rest.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import users.rest.model.dto.UserDTO;

import java.time.LocalDate;
import java.util.UUID;

import static org.hibernate.type.SqlTypes.VARCHAR;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @UuidGenerator
    @JdbcTypeCode(VARCHAR)
    private UUID uuid;
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

    public UserEntity(Builder builder) {
        this.id = builder.id;
        this.uuid = builder.uuid;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.birthDate = builder.birthDate;
        this.email = builder.email;
        this.phoneNumber = builder.phoneNumber;
    }

    public UserEntity() {
    }

    public Long getId() {
        return id;
    }

    public UUID getUuid() {
        return uuid;
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

    public static class Builder {
        private Long id;
        private UUID uuid;
        private String firstName;
        private String lastName;
        private LocalDate birthDate;
        private String phoneNumber;
        private String email;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder uuid(UUID uuid) {
            this.uuid = uuid;
            return this;
        }

        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder birthDate(LocalDate birthDate) {
            this.birthDate = birthDate;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder phoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public UserEntity build() {
            return new UserEntity(this);
        }
    }

}
