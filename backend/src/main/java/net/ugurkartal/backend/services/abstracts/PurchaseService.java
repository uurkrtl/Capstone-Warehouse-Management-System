package net.ugurkartal.backend.services.abstracts;

import net.ugurkartal.backend.services.dtos.requests.PurchaseRequest;
import net.ugurkartal.backend.services.dtos.responses.PurchaseCreatedResponse;
import net.ugurkartal.backend.services.dtos.responses.PurchaseGetAllResponse;

import java.util.List;

public interface PurchaseService {
    List<PurchaseGetAllResponse> getAllPurchases();
    PurchaseCreatedResponse getPurchaseById(String id);
    PurchaseCreatedResponse addPurchase(PurchaseRequest purchaseRequest);
}