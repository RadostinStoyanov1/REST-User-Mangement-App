package users.rest.web;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(
        name = "Users",
        description = "The controller responsible for users management"
)
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Created user",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = UserDTO.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "If the user doesn't pass the validation of AddUserDTO"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "If the user has existing email or phone"
                    )
            }
    )
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody AddUserDTO addUserDTO) {
        UserDTO userResponse = userService.createUserEntity(addUserDTO);
        return new ResponseEntity<UserDTO>(userResponse, HttpStatus.CREATED);
    }

    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Found user",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = UserDTO.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "If user is not found"
                    )
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getByUuid(@PathVariable("id") UUID id) {
        return ResponseEntity
                .ok(userService.getUserByUuid(id));
    }

    @ApiResponse(
            responseCode = "200",
            description = "Got all users",
            content = {
                    @Content(mediaType = "application/json")
            }
    )
    @GetMapping("/all")
    public ResponseEntity<List<UserDTO>> getAllUsers(@RequestParam(name = "pattern", required = false) String pattern) {
        List<UserDTO> allFoundUsers = userService.getAllUsers(pattern);
        return ResponseEntity.ok(allFoundUsers);
    }

    @ApiResponse(
            responseCode = "200",
            description = "Check if user with requested email exist",
            content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BooleanResultDTO.class)
                    )
            }
    )
    @GetMapping("/by-email")
    public ResponseEntity<BooleanResultDTO> checkIfUserWithPatternEmailExists(@RequestParam("pattern") String pattern) {
        return ResponseEntity.ok(
                userService.checkIfUserEmailIsUnique(pattern)
        );
    }

    @ApiResponse(
            responseCode = "200",
            description = "Check if user with requested phone number exist",
            content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BooleanResultDTO.class)
                    )
            }
    )
    @GetMapping("/by-phone")
    public ResponseEntity<BooleanResultDTO> checkIfUserWithPatternPhoneExists(@RequestParam("pattern") String pattern) {
        return ResponseEntity.ok(
                userService.checkIfUserPhoneIsUnique(pattern)
        );
    }

    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Updated user",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = UserDTO.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "If the user doesn't pass the validation of UpdateUserDTO"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "If the user has existing email or phone by another user"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "If user with requested Uuid does not exist"
                    )
            }
    )
    @PutMapping
    public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UpdateUserDTO updateUserDTO) {
        UserDTO userResponse = userService.updateUserByUuid(updateUserDTO);
        return new ResponseEntity<UserDTO>(userResponse, HttpStatus.OK);
    }

    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Deleted user",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = BooleanResultDTO.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "If user with requested uuid is not found"
                    )
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<BooleanResultDTO> deleteUserById(@PathVariable("id") UUID uuid) {
        userService.deleteUser(uuid);
        BooleanResultDTO message = new BooleanResultDTO.Builder().data(true).build();
        return new ResponseEntity<BooleanResultDTO>(message, HttpStatus.OK);
    }
}
