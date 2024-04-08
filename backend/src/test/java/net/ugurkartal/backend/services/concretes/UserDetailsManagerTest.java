package net.ugurkartal.backend.services.concretes;

import net.ugurkartal.backend.models.User;
import net.ugurkartal.backend.models.enums.UserRole;
import net.ugurkartal.backend.services.abstracts.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserDetailsManagerTest {

    @InjectMocks
    private UserDetailsManager userDetailsManager;

    @Mock
    private UserService userService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void loadUserByUsernameReturnsUserDetailsWhenUserExists() {
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("testPassword");
        user.setRole(UserRole.ADMIN);

        when(userService.getUserByUsername("testUser")).thenReturn(user);

        UserDetails userDetails = userDetailsManager.loadUserByUsername("testUser");

        assertEquals(user.getUsername(), userDetails.getUsername());
        assertEquals(user.getPassword(), userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")));
    }
}