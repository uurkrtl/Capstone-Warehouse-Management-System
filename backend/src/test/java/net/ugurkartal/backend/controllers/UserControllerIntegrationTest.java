package net.ugurkartal.backend.controllers;

import net.ugurkartal.backend.services.abstracts.UserService;
import net.ugurkartal.backend.services.dtos.responses.UserCreatedResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerIntegrationTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getLoggedInUserReturnsUserWhenUserIsLoggedIn() {
        UserCreatedResponse userCreatedResponse = new UserCreatedResponse();
        userCreatedResponse.setUsername("testUser");

        when(userService.getLoggedInUser()).thenReturn(userCreatedResponse);

        UserCreatedResponse response = userController.getLoggedInUser();

        assertEquals(userCreatedResponse.getUsername(), response.getUsername());
    }

    @Test
    void getLoggedInUserReturnsNullWhenUserIsNotLoggedIn() {
        when(userService.getLoggedInUser()).thenReturn(null);

        UserCreatedResponse response = userController.getLoggedInUser();

        assertNull(response);
    }
}