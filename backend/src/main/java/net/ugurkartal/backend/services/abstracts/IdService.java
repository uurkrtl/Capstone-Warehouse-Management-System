package net.ugurkartal.backend.services.abstracts;

public interface IdService {
    String generateProductId();
    String generateCategoryId();
    String generateSupplierId();
    String generatePurchaseId();
    String generateOrderId();
    String generateOrderDetailId();
}