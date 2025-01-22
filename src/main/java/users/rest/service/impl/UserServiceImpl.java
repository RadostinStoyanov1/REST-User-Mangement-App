package users.rest.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import users.rest.model.dto.AddUserDTO;
import users.rest.model.dto.BooleanResultDTO;
import users.rest.model.dto.UpdateUserDTO;
import users.rest.model.dto.UserDTO;
import users.rest.model.entity.UserEntity;
import users.rest.repository.UserRepository;
import users.rest.service.UserService;
import users.rest.service.exception.RestApiBusyEmailOrPhoneNumberException;
import users.rest.service.exception.RestApiUserNotFoundException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private static final String UNIQUE = "UNIQUE";
    private static final String USED = "USED";
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserDTO createUserEntity(AddUserDTO addUserDTO) {
        UserEntity userEntity = modelMapper.map(addUserDTO, UserEntity.class);
        if (isUsernameOrPasswordBusy(userEntity)) {
            throw new RestApiBusyEmailOrPhoneNumberException("User with email " + userEntity.getEmail() + " or phone " + userEntity.getPhoneNumber() + " already exists", userEntity.getEmail(), userEntity.getPhoneNumber());
        }
        userRepository.save(userEntity);
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
        if (userRepository.findById(userId).isEmpty()) {
            throw new RestApiUserNotFoundException("User with id: " + userId + "was not found", userId);
        }
        userRepository.deleteById(userId);
    }

    @Override
    public UserDTO updateUserById(UpdateUserDTO updateUserDTO) {

        UserEntity userEntity = modelMapper.map(updateUserDTO, UserEntity.class);

        if (isUserWithSameEmailOrPhoneExists(userEntity)) {
            throw new RestApiBusyEmailOrPhoneNumberException("User with email " + userEntity.getEmail() + " or phone " + userEntity.getPhoneNumber() + " already exists", userEntity.getEmail(), userEntity.getPhoneNumber());
        }

        userRepository.updateUserEntityById(userEntity.getId(), userEntity.getFirstName(), userEntity.getLastName(), userEntity.getBirthDate(), userEntity.getPhoneNumber(), userEntity.getEmail());
        return modelMapper.map(userEntity, UserDTO.class);
    }

    @Override
    public List<UserDTO> getAllUsers(String pattern) {

        List<UserDTO> foundUsers = new ArrayList<>();

        if (pattern.isBlank() || pattern == null) {
            userRepository
                    .findAll()
                    .forEach(user -> foundUsers.add(modelMapper.map(user, UserDTO.class)));
        } else {
            userRepository
                    .findAllByFirstNameOrLastNameContainingIgnoreCase(pattern)
                    .forEach(user -> foundUsers.add(modelMapper.map(user, UserDTO.class)));
        }

        return foundUsers
                .stream()
                .sorted(Comparator.comparing(UserDTO::getLastName).thenComparing(UserDTO::getBirthDate))
                .collect(Collectors.toList());
    }

    @Override
    public BooleanResultDTO uniqueUserEmail(String email) {
        BooleanResultDTO result = new BooleanResultDTO();
        if (userRepository.findAllByEmail(email).isEmpty()) {
            result.setResult(UNIQUE);
        } else {
            result.setResult(USED);
        }
        return result;
    }

    @Override
    public BooleanResultDTO uniqueUserPhone(String phone) {
        BooleanResultDTO result = new BooleanResultDTO();
        if (userRepository.findAllByPhoneNumber(phone).isEmpty()) {
            result.setResult(UNIQUE);
        } else {
            result.setResult(USED);
        }
        return result;
    }


    @Override
    public boolean isUsernameOrPasswordBusy(UserEntity userEntity) {

        if (userRepository.findAllByPhoneNumberOrEmail(userEntity.getPhoneNumber(), userEntity.getEmail()).isEmpty()) {
            return false;
        }

        return true;
    }

    @Override
    public boolean isUserWithSameEmailOrPhoneExists(UserEntity userEntity) {

        List<UserEntity> foundUsers = userRepository.findAllByPhoneNumberOrEmail(userEntity.getPhoneNumber(), userEntity.getEmail());

        if (foundUsers.isEmpty()) {
            return false;
        }

        for (UserEntity foundUser : foundUsers) {
            if (foundUser.getId() != userEntity.getId()) {
                return true;
            }
        }
        return false;
    }

}
