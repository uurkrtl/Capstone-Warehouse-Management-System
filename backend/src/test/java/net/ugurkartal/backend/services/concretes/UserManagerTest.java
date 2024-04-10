package net.ugurkartal.backend.services.concretes;

import net.ugurkartal.backend.core.mappers.ModelMapperService;
import net.ugurkartal.backend.models.User;
import net.ugurkartal.backend.models.enums.UserRole;
import net.ugurkartal.backend.repositories.UserRepository;
import net.ugurkartal.backend.services.abstracts.IdService;
import net.ugurkartal.backend.services.dtos.requests.UserRequest;
import net.ugurkartal.backend.services.dtos.responses.UserCreatedResponse;
import net.ugurkartal.backend.services.dtos.responses.UserGetAllResponse;
import net.ugurkartal.backend.services.rules.UserBusinessRules;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
        import static org.mockito.Mockito.*;

class UserManagerTest {
    @InjectMocks
    private ModelMapper modelMapper;

    @InjectMocks
    private UserManager userManager;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserBusinessRules userBusinessRules;

    @Mock
    private ModelMapperService modelMapperService;

    @Mock
    private IdService idService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        modelMapper = mock(ModelMapper.class);
    }

    @Test
    void getUserByUsernameReturnsUserWhenUserExists() {
        User user = new User();
        user.setUsername("testUser");
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));

        User returnedUser = userManager.getUserByUsername("testUser");

        assertEquals(user.getUsername(), returnedUser.getUsername());
    }

    @Test
    void getUserByUsernameThrowsExceptionWhenUserDoesNotExist() {
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> userManager.getUserByUsername("testUser"));
    }

    @Test
    void registerUserReturnsCreatedUser() {
        // Given
        User user = User.builder()
                .username("testUser")
                .password("testPassword")
                .role(UserRole.USER)
                .imageUrl("testImageUrl")
                .build();

        UserCreatedResponse expectedResponse = UserCreatedResponse.builder()
                .username("testUser")
                .role("USER")
                .build();

        UserRequest userRequest = UserRequest.builder()
                .username("testUser")
                .role("USER")
                .build();

        // When
        when(idService.generateUserId()).thenReturn("1");
        when(modelMapperService.forRequest()).thenReturn(modelMapper);
        when(modelMapperService.forResponse()).thenReturn(modelMapper);
        when(modelMapper.map(userRequest, User.class)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        when(passwordEncoder.encode(user.getPassword())).thenReturn("encodedPassword");
        when(modelMapper.map(user, UserCreatedResponse.class)).thenReturn(expectedResponse);

        UserCreatedResponse actualResponse = userManager.registerUser(userRequest);

        // Then
        verify(userRepository, times(1)).save(user);
        assertEquals(user.getUsername(), actualResponse.getUsername());
    }

    @Test
    void getAllUsersReturnsListOfUsers() {
        // Given
        List<User> users = List.of(
                new User(),
                new User()
        );

        // When
        when(modelMapperService.forResponse()).thenReturn(modelMapper);
        when(userRepository.findAll()).thenReturn(users);

        List<UserGetAllResponse> response = userManager.getAllUsers();

        // Then
        assertEquals(2, response.size());
    }

    @Test
    void getUserById_whenUserExists_shouldReturnUser() {
        // Given
        User user = User.builder().id("1").build();
        User ownUser = User.builder().id("1").role(UserRole.ADMIN).build();
        UserCreatedResponse expectedResponse = UserCreatedResponse.builder().id("1").build();

        // When
        when(modelMapperService.forResponse()).thenReturn(modelMapper);
        when(modelMapper.map(user, UserCreatedResponse.class)).thenReturn(expectedResponse);
        when(userRepository.findById("1")).thenReturn(Optional.of(user));

        UserCreatedResponse actual = userManager.getUserById(ownUser.getId(), ownUser.getRole().toString(),"1");

        // Then
        verify(userBusinessRules, times(1)).checkIfUnauthorizedUser(anyString(), anyString(), anyString());
        assertEquals(expectedResponse.getId(), actual.getId());
    }

    @Test
    void updateUser_whenUserRequestIsValid_shouldReturnSUserCreatedResponse() {
        // Given
        String id = "1";
        UserRequest request = UserRequest.builder()
                .username("Updated Test")
                .password("Updated Test")
                .role("ADMIN")
                .firstName("Updated Test")
                .lastName("Updated Test")
                .email("test@test.com")
                .imageUrl("testImageUrl")
                .build();

        User updatedUser = User.builder()
                .id(id)
                .username("Updated Test")
                .password("Updated Test")
                .role(UserRole.ADMIN)
                .firstName("Updated Test")
                .lastName("Updated Test")
                .email("test@test.com")
                .imageUrl("testImageUrl")
                .build();

        UserCreatedResponse expectedResponse = UserCreatedResponse.builder()
                .id(id)
                .username("Updated Test")
                .role("ADMIN")
                .firstName("Updated Test")
                .lastName("Updated Test")
                .email("test@test.com")
                .imageUrl("testImageUrl")
                .build();

        // When
        when(modelMapperService.forRequest()).thenReturn(modelMapper);
        when(modelMapperService.forResponse()).thenReturn(modelMapper);
        when(modelMapper.map(request, User.class)).thenReturn(updatedUser);
        when(userRepository.findById(id)).thenReturn(Optional.of(User.builder().id(id).build()));
        when(userRepository.save(updatedUser)).thenReturn(updatedUser);
        when(modelMapper.map(updatedUser, UserCreatedResponse.class)).thenReturn(expectedResponse);

        UserCreatedResponse actualResponse = userManager.updateUser(id, "ADMIN", id, request);

        // Then
        verify(userBusinessRules, times(1)).checkIfUnauthorizedUser(anyString(), anyString(), anyString());
        verify(userBusinessRules, times(1)).checkIfUsernameExists(anyString(), anyString());
        verify(userBusinessRules, times(1)).checkIfEmailExists(anyString(), anyString());
        verify(userRepository, times(1)).save(updatedUser);
        assertEquals(expectedResponse.getId(), actualResponse.getId());
        assertEquals(expectedResponse.getUsername(), actualResponse.getUsername());
        assertEquals(expectedResponse.getEmail(), actualResponse.getEmail());
    }
}