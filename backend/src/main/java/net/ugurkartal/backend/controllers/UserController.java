package net.ugurkartal.backend.controllers;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import net.ugurkartal.backend.services.abstracts.UserService;
import net.ugurkartal.backend.services.dtos.requests.UserRequest;
import net.ugurkartal.backend.services.dtos.responses.UserCreatedResponse;
import net.ugurkartal.backend.services.dtos.responses.UserGetAllResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/me")
    public UserCreatedResponse getLoggedInUser() {
        return userService.getLoggedInUser();
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void login() {
        // This method is only here to trigger the login process
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserCreatedResponse registerUser(@RequestBody UserRequest userRequest) {
        return userService.registerUser(userRequest);
    }

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void logout(HttpSession session) {
        session.invalidate();
        SecurityContextHolder.clearContext();
    }

    @GetMapping
    public List<UserGetAllResponse> getAllUsers() {
        return userService.getAllUsers();
    }
}