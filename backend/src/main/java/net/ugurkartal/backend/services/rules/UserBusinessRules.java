package net.ugurkartal.backend.services.rules;

import lombok.RequiredArgsConstructor;
import net.ugurkartal.backend.core.exceptions.types.DuplicateRecordException;
import net.ugurkartal.backend.core.exceptions.types.UnauthorizedAccessException;
import net.ugurkartal.backend.models.enums.UserRole;
import net.ugurkartal.backend.repositories.UserRepository;
import net.ugurkartal.backend.services.messages.UserMessage;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserBusinessRules {
    private final UserRepository userRepository;
    public void checkIfUnauthorizedUser(String ownId, String  role, String searchId) {
        if(!ownId.equals(searchId) && role.equals(UserRole.USER.name())) {
            throw new UnauthorizedAccessException(UserMessage.UNAUTHORIZED_ACCESS_REQUEST);
        }
    }

    public void checkIfUsernameExists(String username) {
        if(this.userRepository.existsByUsername(username)) {
            throw new DuplicateRecordException(UserMessage.USERNAME_ALREADY_EXISTS);
        }
    }

    public void checkIfEmailExists(String email) {
        if(this.userRepository.existsByEmail(email)) {
            throw new DuplicateRecordException(UserMessage.EMAIL_ALREADY_EXISTS);
        }
    }
}