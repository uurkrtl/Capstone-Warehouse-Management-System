package net.ugurkartal.backend.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "products")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {
    @Id
    private String id;
    private String name;
    private String description;
    private double salePrice;
    private int stock;
    private int criticalStock;
    private String imageUrl;
    @DBRef
    private Category category;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean isActive;
}