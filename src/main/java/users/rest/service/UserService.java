package users.rest.service;

import users.rest.model.dto.AddUserDTO;
import users.rest.model.dto.UpdateUserDTO;
import users.rest.model.dto.UserDTO;
import users.rest.model.entity.UserEntity;

public interface UserService {

    public UserDTO createUserEntity(AddUserDTO addUserDTO);

    public UserDTO getUserById(Long id);

    public void deleteUser(Long userId);

    public UserDTO updateUserById(UpdateUserDTO updateUserDTO);
}
