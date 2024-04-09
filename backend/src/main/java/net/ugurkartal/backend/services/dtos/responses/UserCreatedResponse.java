package net.ugurkartal.backend.services.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCreatedResponse {
    private String id;
    private String username;
    private String role;
    private String firstName;
    private String lastName;
    private String email;
    private String imageUrl;
}