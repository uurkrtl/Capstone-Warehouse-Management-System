package net.ugurkartal.backend.services.rules;

import net.ugurkartal.backend.core.exceptions.types.DuplicateRecordException;
import net.ugurkartal.backend.core.exceptions.types.UnauthorizedAccessException;
import net.ugurkartal.backend.models.enums.UserRole;
import net.ugurkartal.backend.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class UserBusinessRulesTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserBusinessRules userBusinessRules;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void checkIfUnauthorizedUser_throwsUnauthorizedAccessException() {
        String ownId = "1";
        String searchId = "2";
        String role = UserRole.USER.name();

        assertThrows(UnauthorizedAccessException.class, () -> userBusinessRules.checkIfUnauthorizedUser(ownId, role, searchId));
    }

    @Test
    void checkIfUnauthorizedUser_doesNotThrowExceptionWhenIdsMatch() {
        String ownId = "1";
        String searchId = "1";
        String role = UserRole.USER.name();

        assertDoesNotThrow(() -> userBusinessRules.checkIfUnauthorizedUser(ownId, role, searchId));
    }

    @Test
    void checkIfUnauthorizedUser_doesNotThrowExceptionWhenRoleIsNotUser() {
        String ownId = "1";
        String searchId = "2";
        String role = UserRole.ADMIN.name();

        assertDoesNotThrow(() -> userBusinessRules.checkIfUnauthorizedUser(ownId, role, searchId));
    }

    @Test
    void checkIfUsernameExists_whenUsernameExist_throwsDuplicateRecordException() {
        String existingUserName = "Existing User";
        when(userRepository.existsByUsername(existingUserName)).thenReturn(true);

        assertThrows(DuplicateRecordException.class, () -> userBusinessRules.checkIfUsernameExists(existingUserName));
    }

    @Test
    void checkIfUsernameExists_whenUsernameNotExist_throwsNotException() {
        String nonExistingUserName = "Non Existing User";
        when(userRepository.existsByUsername(nonExistingUserName)).thenReturn(false);

        assertDoesNotThrow(() -> userBusinessRules.checkIfUsernameExists(nonExistingUserName));
    }

    @Test
    void checkIfEmailExists_whenEmailExist_throwsDuplicateRecordException() {
        String existingEmail = "Existing Email";
        when(userRepository.existsByUsername(existingEmail)).thenReturn(true);

        assertThrows(DuplicateRecordException.class, () -> userBusinessRules.checkIfUsernameExists(existingEmail));
    }

    @Test
    void checkIfEmailExists_whenEmailNotExist_throwsNotException() {
        String nonExistingEmail = "Non Existing Email";
        when(userRepository.existsByUsername(nonExistingEmail)).thenReturn(false);

        assertDoesNotThrow(() -> userBusinessRules.checkIfUsernameExists(nonExistingEmail));
    }
}