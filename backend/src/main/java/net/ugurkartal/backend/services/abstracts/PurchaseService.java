package net.ugurkartal.backend.services.abstracts;

import net.ugurkartal.backend.services.dtos.requests.PurchaseRequest;
import net.ugurkartal.backend.services.dtos.responses.PurchaseCreatedResponse;
import net.ugurkartal.backend.services.dtos.responses.PurchaseGetAllResponse;

import java.util.List;

public interface PurchaseService {
    List<PurchaseGetAllResponse> getAllPurchases();
    List<PurchaseGetAllResponse> getPurchasesByProductId(String productId);
    List<PurchaseGetAllResponse> getPurchasesBySupplierId(String supplierId);
    PurchaseCreatedResponse getPurchaseById(String id);
    PurchaseCreatedResponse addPurchase(PurchaseRequest purchaseRequest);
    PurchaseCreatedResponse updatePurchase(String id, PurchaseRequest purchaseRequest);
    PurchaseCreatedResponse changePurchaseStatus(String id, boolean status);
}