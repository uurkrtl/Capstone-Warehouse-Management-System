package net.ugurkartal.backend.services.dtos.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetailRequest {
    @NotNull(message = "Die Bestellung darf nicht leer sein")
    private String orderId;
    @NotNull(message = "Das Produkt darf nicht leer sein")
    private String productId;
    @Positive(message = "Die Menge muss größer als 0 sein")
    private int quantity;
}