package users.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import users.rest.model.entity.UserEntity;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Modifying
    @Transactional
    @Query("update UserEntity u set u.firstName = :firstName, u.lastName = :lastName, u.birthDate = :birthDate, u.phoneNumber = :phoneNumber, u.email = :email where u.id = :id")
    void updateUserEntityById(Long id, String firstName, String lastName, LocalDate birthDate, String phoneNumber, String email);

    @Transactional
    @Modifying
    void deleteById(Long id);

    //List<UserEntity> findAllByFirstNameContainingOrLastNameContaining(String pattern, String pattern2);
    @Query("SELECT u FROM UserEntity u WHERE LOWER(u.firstName) LIKE LOWER(CONCAT('%', :pattern, '%')) OR LOWER(u.lastName) LIKE LOWER(CONCAT('%', :pattern, '%'))")
    List<UserEntity> findAllByFirstNameOrLastNameContainingIgnoreCase(@Param("pattern") String pattern);
}
