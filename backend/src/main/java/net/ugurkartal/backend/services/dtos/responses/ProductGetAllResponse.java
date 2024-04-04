package net.ugurkartal.backend.services.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductGetAllResponse {
    private String id;
    private String name;
    private double salePrice;
    private int stock;
    private int criticalStock;
    private String categoryId;
    private String categoryName;
    private boolean isActive;
}