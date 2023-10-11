package antifraud.Controllers;

import antifraud.Entities.User;
import antifraud.Repositories.UserRepository;
import antifraud.Requests.ChangeRoleRequest;
import antifraud.Requests.UserLockRequest;
import antifraud.Responses.UserDeletedResponse;
import antifraud.Responses.UserLockResponse;
import antifraud.Responses.UserInfoResponse;
import antifraud.Services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/test")
    public String helloWorld() {
        return "Hello world";
    }

    @PostMapping("/user")
    public ResponseEntity<UserInfoResponse> registerUser(@Valid @RequestBody User user, BindingResult bindingResult) {
        return userService.registerUser(user, bindingResult);
    }

    @GetMapping("/list")
    public ResponseEntity<List<UserInfoResponse>> getUsers() {
        return userService.getUsers();
    }

    @DeleteMapping("/user/{username}")
    public ResponseEntity<UserDeletedResponse> deleteUser(@PathVariable String username) {
        return userService.deleteUser(username);
    }

    @PutMapping("/role")
    public ResponseEntity<UserInfoResponse> changeUserRole(@RequestBody ChangeRoleRequest request) {
        return userService.changeRole(request);
    }

    @PutMapping("/access")
    public ResponseEntity<UserLockResponse> changeUserLockStatus(@RequestBody UserLockRequest request) {
        return userService.changeUserLockStatus(request);
    }
}
