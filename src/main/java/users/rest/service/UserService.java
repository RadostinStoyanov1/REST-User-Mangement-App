package users.rest.service;

import users.rest.model.dto.AddUserDTO;
import users.rest.model.dto.BooleanResultDTO;
import users.rest.model.dto.UpdateUserDTO;
import users.rest.model.dto.UserDTO;
import users.rest.model.entity.UserEntity;

import java.util.List;
import java.util.UUID;

public interface UserService {

    public UserDTO createUserEntity(AddUserDTO addUserDTO);

    public UserDTO getUserByUuid(UUID uuid);

    public boolean deleteUser(UUID uuid);

    public UserDTO updateUserById(UpdateUserDTO updateUserDTO);

    public List<UserDTO> getAllUsers(String pattern);

    public BooleanResultDTO uniqueUserEmail(String email);

    public BooleanResultDTO uniqueUserPhone(String phone);

    public boolean isUsernameOrPasswordBusy(UserEntity userEntity);

    public boolean isUserWithSameEmailOrPhoneExists(UserEntity userEntity);
}
