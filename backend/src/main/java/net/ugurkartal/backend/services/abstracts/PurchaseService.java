package net.ugurkartal.backend.services.abstracts;

import net.ugurkartal.backend.services.dtos.requests.PurchaseRequest;
import net.ugurkartal.backend.services.dtos.responses.PurchaseCreatedResponse;

public interface PurchaseService {
    public PurchaseCreatedResponse addPurchase(PurchaseRequest purchaseRequest);
}