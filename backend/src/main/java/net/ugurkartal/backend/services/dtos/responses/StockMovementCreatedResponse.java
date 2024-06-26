package net.ugurkartal.backend.services.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.ugurkartal.backend.models.enums.StockMovementReason;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StockMovementCreatedResponse {
    private String id;
    private String productId;
    private String productName;
    private int quantity;
    private boolean type;
    private StockMovementReason reason;
    private LocalDateTime createdAt;
}