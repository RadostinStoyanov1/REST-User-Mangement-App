package users.rest.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import users.rest.model.dto.AddUserDTO;
import users.rest.model.dto.UpdateUserDTO;
import users.rest.model.dto.UserDTO;
import users.rest.model.entity.UserEntity;
import users.rest.repository.UserRepository;
import users.rest.service.UserService;
import users.rest.service.exception.RestApiUserNotFoundException;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserDTO createUserEntity(AddUserDTO addUserDTO) {
        UserEntity userEntity = userRepository.save(modelMapper.map(addUserDTO, UserEntity.class));
        return modelMapper.map(userEntity, UserDTO.class);
    }

    @Override
    public UserDTO getUserById(Long id) {
        return userRepository
                .findById(id)
                .map(o -> modelMapper.map(o, UserDTO.class))
                .orElseThrow(() -> new RestApiUserNotFoundException("User with id: " + id + "was not found", id));
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public UserDTO updateUserById(UpdateUserDTO updateUserDTO) {
        UserEntity userEntity = modelMapper.map(updateUserDTO, UserEntity.class);

        return modelMapper.map(userEntity, UserDTO.class);
    }


}
