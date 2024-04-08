package net.ugurkartal.backend.services.abstracts;

import net.ugurkartal.backend.models.User;
import net.ugurkartal.backend.services.dtos.requests.UserRequest;
import net.ugurkartal.backend.services.dtos.responses.UserCreatedResponse;
import net.ugurkartal.backend.services.dtos.responses.UserGetAllResponse;

import java.util.List;

public interface UserService {
    User getUserByUsername(String username);
    UserCreatedResponse getLoggedInUser();
    UserCreatedResponse registerUser(UserRequest userRequest);
    List<UserGetAllResponse> getAllUsers();
}