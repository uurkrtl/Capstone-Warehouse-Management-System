package net.ugurkartal.backend.services.dtos.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductRequest {
    @Size(min = 2, max = 50, message = "Der Name muss zwischen 2 und 50 Zeichen lang sein")
    private String name;
    @Size(min = 2, max = 150, message = "Die Beschreibung muss zwischen 2 und 50 Zeichen lang sein")
    private String description;
    @Positive(message = "Der Verkaufspreis muss größer als 0 sein")
    private double salePrice;
    @PositiveOrZero(message = "Der kritische Bestand muss größer oder gleich 0 sein")
    private int criticalStock;
    private String imageUrl;
    @NotNull(message = "Die Kategorie-ID darf nicht null sein")
    private String categoryId;
}