package users.rest.service;

import users.rest.model.dto.AddUserDTO;
import users.rest.model.dto.BooleanResultDTO;
import users.rest.model.dto.UpdateUserDTO;
import users.rest.model.dto.UserDTO;
import users.rest.model.entity.UserEntity;

import java.util.List;

public interface UserService {

    public UserDTO createUserEntity(AddUserDTO addUserDTO);

    public UserDTO getUserById(Long id);

    public boolean deleteUser(Long userId);

    public UserDTO updateUserById(UpdateUserDTO updateUserDTO);

    public List<UserDTO> getAllUsers(String pattern);

    public BooleanResultDTO uniqueUserEmail(String email);

    public BooleanResultDTO uniqueUserPhone(String phone);

    public boolean isUsernameOrPasswordBusy(UserEntity userEntity);

    public boolean isUserWithSameEmailOrPhoneExists(UserEntity userEntity);
}
