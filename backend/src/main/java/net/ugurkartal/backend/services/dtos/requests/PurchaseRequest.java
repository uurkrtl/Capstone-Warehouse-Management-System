package net.ugurkartal.backend.services.dtos.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PurchaseRequest {
    @NotNull(message = "Das Produkt-ID darf nicht null sein")
    private String productId;
    @NotNull(message = "Der Lieferant-ID darf nicht null sein")
    private String supplierId;
    @Positive(message = "Der Einkaufspreis muss größer als 0 sein")
    private double purchasePrice;
    @Positive(message = "Die Menge muss größer als 0 sein")
    private int quantity;
    @NotNull(message = "Das Kaufdatum darf nicht null sein")
    private LocalDateTime purchaseDate;
}