package net.ugurkartal.backend.services.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetailCreatedResponse {
    private String id;
    private String orderId;
    private String productName;
    private String productId;
    private int quantity;
    private double price;
    private double totalPrice;
    private boolean isActive;
}