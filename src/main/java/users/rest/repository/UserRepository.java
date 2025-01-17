package users.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import users.rest.model.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Modifying
    @Transactional
    @Query()
    void updateUserEntityById(UserEntity userEntity);

    @Transactional
    @Modifying
    void deleteById(Long id);
}
