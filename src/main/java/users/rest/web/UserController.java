package users.rest.web;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import users.rest.model.dto.AddUserDTO;
import users.rest.model.dto.BooleanResultDTO;
import users.rest.model.dto.UpdateUserDTO;
import users.rest.model.dto.UserDTO;
import users.rest.service.UserService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody AddUserDTO addUserDTO) {
        UserDTO userResponse = userService.createUserEntity(addUserDTO);
        return new ResponseEntity<UserDTO>(userResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getByUuid(@PathVariable("id") UUID id) {
        return ResponseEntity
                .ok(userService.getUserByUuid(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserDTO>> getAllUsers(@RequestParam(name = "pattern", required = false) String pattern) {
        List<UserDTO> allFoundUsers = userService.getAllUsers(pattern);
        return ResponseEntity.ok(allFoundUsers);
    }

    @GetMapping("/by-email")
    public ResponseEntity<BooleanResultDTO> checkIfUserWithPatternEmailExists(@RequestParam("pattern") String pattern) {
        return ResponseEntity.ok(
                userService.checkIfUserEmailIsUnique(pattern)
        );
    }

    @GetMapping("/by-phone")
    public ResponseEntity<BooleanResultDTO> checkIfUserWithPatternPhoneExists(@RequestParam("pattern") String pattern) {
        return ResponseEntity.ok(
                userService.checkIfUserPhoneIsUnique(pattern)
        );
    }

    @PutMapping
    public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UpdateUserDTO updateUserDTO) {
        UserDTO userResponse = userService.updateUserByUuid(updateUserDTO);
        return new ResponseEntity<UserDTO>(userResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BooleanResultDTO> deleteUserById(@PathVariable("id") UUID uuid) {
        userService.deleteUser(uuid);
        BooleanResultDTO message = new BooleanResultDTO.Builder().data(true).build();
        return new ResponseEntity<BooleanResultDTO>(message, HttpStatus.OK);
    }
}
