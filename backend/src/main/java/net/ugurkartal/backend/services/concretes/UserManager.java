package net.ugurkartal.backend.services.concretes;

import lombok.RequiredArgsConstructor;
import net.ugurkartal.backend.core.mappers.ModelMapperService;
import net.ugurkartal.backend.models.User;
import net.ugurkartal.backend.repositories.UserRepository;
import net.ugurkartal.backend.services.abstracts.IdService;
import net.ugurkartal.backend.services.abstracts.UserService;
import net.ugurkartal.backend.services.dtos.requests.UserRequest;
import net.ugurkartal.backend.services.dtos.responses.UserCreatedResponse;
import net.ugurkartal.backend.services.dtos.responses.UserGetAllResponse;
import net.ugurkartal.backend.services.messages.UserMessage;
import net.ugurkartal.backend.services.rules.UserBusinessRules;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserManager implements UserService {
    private final UserRepository userRepository;
    private final ModelMapperService modelMapperService;
    private final IdService idService;
    private final UserBusinessRules userBusinessRules;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(UserMessage.USER_NOT_FOUND));
    }

    @Override
    public UserCreatedResponse getLoggedInUser() {
        var principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = getUserByUsername(principal.getUsername());
        return modelMapperService.forResponse().map(user, UserCreatedResponse.class);
    }

    @Override
    public UserCreatedResponse registerUser(UserRequest userRequest) {
        userBusinessRules.checkIfUsernameExists(userRequest.getUsername());
        userBusinessRules.checkIfEmailExists(userRequest.getEmail());
        User user = modelMapperService.forRequest().map(userRequest, User.class);
        user.setId(idService.generateUserId());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        if (user.getImageUrl().isEmpty()){
            user.setImageUrl("https://i.ibb.co/z7DKLLh/user-icon-on-transparent-background-free-png.webp");
        }
        user = userRepository.save(user);
        return modelMapperService.forResponse().map(user, UserCreatedResponse.class);
    }

    @Override
    public List<UserGetAllResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user->this.modelMapperService.forResponse()
                        .map(user, UserGetAllResponse.class)).toList();
    }

    @Override
    public UserCreatedResponse getUserById(String ownId, String  role, String searchId) {
        userBusinessRules.checkIfUnauthorizedUser(ownId, role, searchId);
        User user = userRepository.findById(searchId).orElseThrow(() -> new UsernameNotFoundException(UserMessage.USER_NOT_FOUND));
        return modelMapperService.forResponse().map(user, UserCreatedResponse.class);
    }

    @Override
    public UserCreatedResponse updateUser(String ownId, String role, String updateId, UserRequest userRequest) {
        userBusinessRules.checkIfUnauthorizedUser(ownId, role, updateId);
        userBusinessRules.checkIfUsernameExists(userRequest.getUsername(), updateId);
        userBusinessRules.checkIfEmailExists(userRequest.getEmail(), updateId);
        User foundedUser = userRepository.findById(updateId).orElseThrow(() -> new UsernameNotFoundException(UserMessage.USER_NOT_FOUND));
        User user = modelMapperService.forRequest().map(userRequest, User.class);
        user.setId(updateId);
        user.setCreatedAt(foundedUser.getCreatedAt());
        user.setUpdatedAt(LocalDateTime.now());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user.getImageUrl().isEmpty()){
            user.setImageUrl("https://i.ibb.co/z7DKLLh/user-icon-on-transparent-background-free-png.webp");
        }
        user = userRepository.save(user);
        return modelMapperService.forResponse().map(user, UserCreatedResponse.class);
    }

    @Override
    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }
}