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

    public UserDTO updateUserByUuid(UpdateUserDTO updateUserDTO);

    public List<UserDTO> getAllUsers(String pattern);

    public BooleanResultDTO checkIfUserEmailIsUnique(String email);

    public BooleanResultDTO checkIfUserPhoneIsUnique(String phone);

    public boolean checkIfUserWithSameEmailOrPhoneAlreadyExists(UserEntity userEntity);

    public boolean checkIfAnotherUserWithSameEmailOrPhoneExists(UserEntity userEntity);
}
