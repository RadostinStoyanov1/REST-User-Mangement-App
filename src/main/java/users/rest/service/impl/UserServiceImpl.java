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
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDTO createUserEntity(AddUserDTO addUserDTO) {
        UserEntity userEntity = mapAddUserDTOToUserEntity(addUserDTO);
        //Removed check for already existing user with same email or phone:
        /*if (checkIfUserWithSameEmailOrPhoneAlreadyExists(userEntity)) {
            throw new RestApiBusyEmailOrPhoneNumberException("User with email " + userEntity.getEmail() + " or phone " + userEntity.getPhoneNumber() + " already exists", userEntity.getEmail(), userEntity.getPhoneNumber());
        }*/
        userRepository.save(userEntity);
        return mapUserEntityToUserDTO(userEntity);
    }

    @Override
    public UserDTO getUserByUuid(UUID uuid) {

        return mapUserEntityToUserDTO(userRepository.findByUuid(uuid).orElseThrow(() -> new RestApiUserNotFoundException("User with UUID: " + uuid + " was not found", uuid)));
    }

    @Override
    public boolean deleteUser(UUID uuid) {
        UserEntity userToBeDeleted = userRepository.findByUuid(uuid).orElseThrow(() -> new RestApiUserNotFoundException("User with UUID: " + uuid + " was not found", uuid));
        userRepository.deleteById(userToBeDeleted.getId());

        return true;
    }

    @Override
    public UserDTO updateUserByUuid(UpdateUserDTO updateUserDTO) {

        UserEntity userEntity = mapUpdateUserDTOToUserEntity(updateUserDTO);

        ////Removed check for another already existing user with same email or phone:
        /*if (checkIfAnotherUserWithSameEmailOrPhoneExists(userEntity)) {
            throw new RestApiBusyEmailOrPhoneNumberException("User with email " + userEntity.getEmail() + " or phone " + userEntity.getPhoneNumber() + " already exists", userEntity.getEmail(), userEntity.getPhoneNumber());
        }*/

        //Change in used repository method for updated UserEntity save operation - save instead of update:
        //userRepository.updateUserEntityById(userEntity.getId(), userEntity.getFirstName(), userEntity.getLastName(), userEntity.getBirthDate(), userEntity.getPhoneNumber(), userEntity.getEmail());
        userRepository.save(userEntity);

        return mapUserEntityToUserDTO(userEntity);
    }

    @Override
    public List<UserDTO> getAllUsers(String pattern) {

        List<UserDTO> foundUsers = new ArrayList<>();

        if (pattern == null || pattern.isBlank()) {
            userRepository
                    .findAll()
                    .forEach(user -> foundUsers.add(mapUserEntityToUserDTO(user)));
        } else {
            userRepository
                    .findAllByFirstNameOrLastNameContainingIgnoreCase(pattern)
                    .forEach(user -> foundUsers.add(mapUserEntityToUserDTO(user)));
        }

        return foundUsers
                .stream()
                .sorted(Comparator.comparing(UserDTO::getLastName).thenComparing(UserDTO::getBirthDate))
                .collect(Collectors.toList());
    }

    @Override
    public BooleanResultDTO checkIfUserEmailIsUnique(String email) {

        if (userRepository.findAllByEmail(email).isEmpty()) {
            return new BooleanResultDTO.Builder().data(true).build();
        } else {
            return new BooleanResultDTO.Builder().data(false).build();
        }
    }

    @Override
    public BooleanResultDTO checkIfUserPhoneIsUnique(String phone) {

        if (userRepository.findAllByPhoneNumber(phone).isEmpty()) {
            return new BooleanResultDTO.Builder().data(true).build();
        } else {
            return new BooleanResultDTO.Builder().data(false).build();
        }

    }


    @Override
    public boolean checkIfUserWithSameEmailOrPhoneAlreadyExists(UserEntity userEntity) {

        if (userRepository.findAllByPhoneNumberOrEmail(userEntity.getPhoneNumber(), userEntity.getEmail()).isEmpty()) {
            return false;
        }

        return true;
    }

    @Override
    public boolean checkIfAnotherUserWithSameEmailOrPhoneExists(UserEntity userEntity) {

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

    private UserDTO mapUserEntityToUserDTO(UserEntity user) {
        return new UserDTO.Builder()
                .uuid(user.getUuid())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .birthDate(user.getBirthDate())
                .phoneNumber(user.getPhoneNumber())
                .email(user.getEmail())
                .build();
    }

    private UserEntity mapAddUserDTOToUserEntity(AddUserDTO addUserDTO) {
        return new UserEntity.Builder()
                .firstName(addUserDTO.getFirstName())
                .lastName(addUserDTO.getLastName())
                .birthDate(addUserDTO.getBirthDate())
                .email(addUserDTO.getEmail())
                .phoneNumber(addUserDTO.getPhoneNumber())
                .build();
    }

    private UserEntity mapUpdateUserDTOToUserEntity(UpdateUserDTO updateUserDTO) {
        return new UserEntity.Builder()
                .id(getUserIdByUuid(updateUserDTO.getUuid()))
                .uuid(updateUserDTO.getUuid())
                .firstName(updateUserDTO.getFirstName())
                .lastName(updateUserDTO.getLastName())
                .birthDate(updateUserDTO.getBirthDate())
                .phoneNumber(updateUserDTO.getPhoneNumber())
                .email(updateUserDTO.getEmail())
                .build();
    }

    private Long getUserIdByUuid(UUID uuid) {
        return userRepository.findByUuid(uuid).orElseThrow(() -> new RestApiUserNotFoundException("User with UUID: " + uuid + " was not found", uuid)).getId();
    }
}
