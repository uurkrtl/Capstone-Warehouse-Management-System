package net.ugurkartal.backend.services.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.ugurkartal.backend.models.enums.StockMovementReason;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StockMovementRequest {
    private String productId;
    private int quantity;
    private boolean type;
    private StockMovementReason reason;
}