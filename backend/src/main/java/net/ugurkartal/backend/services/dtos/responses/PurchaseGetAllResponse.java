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
public class PurchaseGetAllResponse {
    private String id;
    private String productId;
    private String productName;
    private String supplierId;
    private String supplierName;
    private LocalDateTime purchaseDate;
    private boolean active;
}