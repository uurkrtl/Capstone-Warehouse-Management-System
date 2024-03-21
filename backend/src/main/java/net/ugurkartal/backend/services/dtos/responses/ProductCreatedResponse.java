package net.ugurkartal.backend.services.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductCreatedResponse {
    private String id;
    private String name;
    private String description;
    private double salePrice;
    private int stock;
    private int criticalStock;
    private String imageUrl;
    private String categoryId;
    private String categoryName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean isActive;
}