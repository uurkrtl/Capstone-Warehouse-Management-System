package net.ugurkartal.backend.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.ugurkartal.backend.models.enums.StockMovementReason;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "stock_movements")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StockMovement {
    @Id
    private String id;
    private Product product;
    private int quantity;
    private boolean type;
    private StockMovementReason reason;
    private LocalDateTime createdAt;
}