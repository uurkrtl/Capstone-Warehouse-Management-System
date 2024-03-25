package net.ugurkartal.backend.services.dtos.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SupplierRequest {
    @Size(min = 2, max = 50, message = "Der Name muss zwischen 2 und 50 Zeichen lang sein")
    private String name;
    @Size(min = 2, max = 50, message = "Der Kontaktname muss zwischen 2 und 50 Zeichen lang sein")
    private String contactName;
    @Email(message = "Die E-Mail-Adresse ist ungültig")
    private String email;
    @Size(min = 10, max = 20, message = "Die Telefonnummer muss zwischen 10 und 20 Zeichen lang sein")
    private String phone;
}