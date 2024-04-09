package net.ugurkartal.backend.services.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserGetAllResponse {
    private String id;
    private String username;
    private String role;
    private String firstName;
    private String lastName;
}