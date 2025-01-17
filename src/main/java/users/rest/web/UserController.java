package users.rest.web;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import users.rest.model.dto.AddUserDTO;
import users.rest.model.dto.UserDTO;
import users.rest.service.UserService;

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
}
