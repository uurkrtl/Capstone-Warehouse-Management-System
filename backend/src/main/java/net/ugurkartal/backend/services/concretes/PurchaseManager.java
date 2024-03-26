package net.ugurkartal.backend.services.concretes;

import lombok.RequiredArgsConstructor;
import net.ugurkartal.backend.core.mappers.ModelMapperService;
import net.ugurkartal.backend.models.Product;
import net.ugurkartal.backend.models.Purchase;
import net.ugurkartal.backend.models.Supplier;
import net.ugurkartal.backend.repositories.ProductRepository;
import net.ugurkartal.backend.repositories.PurchaseRepository;
import net.ugurkartal.backend.repositories.SupplierRepository;
import net.ugurkartal.backend.services.abstracts.IdService;
import net.ugurkartal.backend.services.abstracts.PurchaseService;
import net.ugurkartal.backend.services.dtos.requests.PurchaseRequest;
import net.ugurkartal.backend.services.dtos.responses.PurchaseCreatedResponse;
import net.ugurkartal.backend.services.rules.PurchaseBusinessRules;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PurchaseManager implements PurchaseService {
    private final PurchaseRepository purchaseRepository;
    private final ProductRepository productRepository;
    private final SupplierRepository supplierRepository;
    private final ModelMapperService modelMapperService;
    private final IdService idService;
    private final PurchaseBusinessRules purchaseBusinessRules;

    @Override
    public PurchaseCreatedResponse addPurchase(PurchaseRequest purchaseRequest) {
        purchaseBusinessRules.checkIfProductByIdNotFound(purchaseRequest.getProductId());
        purchaseBusinessRules.checkIfSupplierByIdNotFound(purchaseRequest.getSupplierId());
        Purchase purchase = modelMapperService.forRequest().map(purchaseRequest, Purchase.class);
        Product selectedProduct = productRepository.findById(purchaseRequest.getProductId()).orElseThrow();
        Supplier selectedSupplier = supplierRepository.findById(purchaseRequest.getSupplierId()).orElseThrow();
        purchase.setProduct(selectedProduct);
        purchase.setSupplier(selectedSupplier);
        purchase.setTotalPrice(purchaseRequest.getPurchasePrice() * purchaseRequest.getQuantity());
        purchase.setId(idService.generatePurchaseId());
        purchase.setCreatedAt(LocalDateTime.now());
        purchase.setActive(true);
        purchase.setPurchaseDate(purchaseRequest.getPurchaseDate().plusHours(1));
        purchase = purchaseRepository.save(purchase);
        return modelMapperService.forResponse().map(purchase, PurchaseCreatedResponse.class);
    }
}