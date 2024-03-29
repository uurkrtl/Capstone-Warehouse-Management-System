package net.ugurkartal.backend.services.dtos.requests;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequest {
    @Size(min = 2, max = 50, message = "Der Name des Kunden muss zwischen 2 und 50 Zeichen lang sein")
    private String customerName;
}