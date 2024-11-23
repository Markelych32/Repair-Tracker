package ru.solonchev.backend.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.solonchev.backend.service.user.UserService;

@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {

    private final UserService userService;

    @Override
    public ResponseEntity<?> deleteUser(@PathVariable Integer userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok().body("User deleted successfully");
    }

    @Override
    public ResponseEntity<?> getTasksOfUser(Integer userId) {
        return ResponseEntity.ok().body(userService.getUserTasks(userId));
    }
}
