package antifraud.Services;

import antifraud.Entities.User;
import antifraud.Repositories.UserRepository;
import antifraud.Requests.ChangeRoleRequest;
import antifraud.Requests.UserLockRequest;
import antifraud.Responses.UserDeletedResponse;
import antifraud.Responses.UserInfoResponse;
import antifraud.Responses.UserLockResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<UserInfoResponse> registerUser(User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        if (userRepository.existsByUsernameIgnoreCase(user.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        if (!userRepository.findAll().iterator().hasNext()) {
            user.setRole("ADMINISTRATOR");
            user.setLocked(false);
        } else {
            user.setRole("MERCHANT");
            user.setLocked(true);
        }

        userRepository.save(user);

        return new ResponseEntity<>(new UserInfoResponse(user), HttpStatus.CREATED);
    }

    public ResponseEntity<List<UserInfoResponse>> getUsers() {
        List<User> users = (List<User>) userRepository.findAll();
        List<UserInfoResponse> userResponses = users.stream()
                .map(UserInfoResponse::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(userResponses);
    }

    @Transactional
    public ResponseEntity<UserDeletedResponse> deleteUser(String username) {
        if (!userRepository.existsByUsernameIgnoreCase(username)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        userRepository.deleteByUsernameIgnoreCase(username);

        return ResponseEntity.ok(new UserDeletedResponse(username));
    }

    public ResponseEntity<UserInfoResponse> changeRole(ChangeRoleRequest request) {
        if (!request.getRole().matches("MERCHANT|SUPPORT")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        User user = userRepository.findByUsernameIgnoreCase(request.getUsername())
                .orElse(null);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        if (user.getRole().equals(request.getRole())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        user.setRole(request.getRole());

        userRepository.save(user);

        return ResponseEntity.ok(new UserInfoResponse(user));
    }

    public ResponseEntity<UserLockResponse> changeUserLockStatus(UserLockRequest request) {
        User user = userRepository.findByUsernameIgnoreCase(request.getUsername()).orElse(null);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        if (user.getRole().equals("ADMINISTRATOR")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        if (request.getOperation().equals("LOCK") && user.isLocked()
                || request.getOperation().equals("UNLOCK") && !user.isLocked()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        user.setLocked(request.getOperation().equals("LOCK"));

        userRepository.save(user);

        return ResponseEntity.ok(new UserLockResponse(request.getUsername(), request.getOperation()));
    }
}
