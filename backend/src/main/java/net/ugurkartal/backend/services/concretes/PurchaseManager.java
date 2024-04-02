package net.ugurkartal.backend.services.concretes;

import lombok.RequiredArgsConstructor;
import net.ugurkartal.backend.core.mappers.ModelMapperService;
import net.ugurkartal.backend.models.Product;
import net.ugurkartal.backend.models.Purchase;
import net.ugurkartal.backend.models.Supplier;
import net.ugurkartal.backend.models.enums.StockMovementReason;
import net.ugurkartal.backend.repositories.ProductRepository;
import net.ugurkartal.backend.repositories.PurchaseRepository;
import net.ugurkartal.backend.repositories.SupplierRepository;
import net.ugurkartal.backend.services.abstracts.IdService;
import net.ugurkartal.backend.services.abstracts.PurchaseService;
import net.ugurkartal.backend.services.abstracts.StockMovementService;
import net.ugurkartal.backend.services.dtos.requests.PurchaseRequest;
import net.ugurkartal.backend.services.dtos.requests.StockMovementRequest;
import net.ugurkartal.backend.services.dtos.responses.PurchaseCreatedResponse;
import net.ugurkartal.backend.services.dtos.responses.PurchaseGetAllResponse;
import net.ugurkartal.backend.services.rules.PurchaseBusinessRules;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PurchaseManager implements PurchaseService {
    private final PurchaseRepository purchaseRepository;
    private final ProductRepository productRepository;
    private final SupplierRepository supplierRepository;
    private final StockMovementService stockMovementService;
    private final ModelMapperService modelMapperService;
    private final IdService idService;
    private final PurchaseBusinessRules purchaseBusinessRules;

    @Override
    public List<PurchaseGetAllResponse> getAllPurchases() {
        List<Purchase> purchases = purchaseRepository.findAll();
        return purchases.stream()
                .map(purchase -> this.modelMapperService.forResponse()
                        .map(purchase, PurchaseGetAllResponse.class)).toList();
    }

    @Override
    public PurchaseCreatedResponse getPurchaseById(String id) {
        purchaseBusinessRules.checkIfPurchaseByIdNotFound(id);
        Purchase purchase = purchaseRepository.findById(id).orElseThrow();
        return modelMapperService.forResponse().map(purchase, PurchaseCreatedResponse.class);
    }

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

        StockMovementRequest stockMovementRequest = StockMovementRequest.builder()
                .productId(purchaseRequest.getProductId())
                .quantity(purchaseRequest.getQuantity())
                .type(true)
                .reason(StockMovementReason.PURCHASE)
                .build();
        stockMovementService.addStockMovement(stockMovementRequest);

        return modelMapperService.forResponse().map(purchase, PurchaseCreatedResponse.class);
    }

    @Override
    public PurchaseCreatedResponse updatePurchase(String id, PurchaseRequest purchaseRequest) {
        purchaseBusinessRules.checkIfPurchaseByIdNotFound(id);
        purchaseBusinessRules.checkIfProductByIdNotFound(purchaseRequest.getProductId());
        purchaseBusinessRules.checkIfSupplierByIdNotFound(purchaseRequest.getSupplierId());
        purchaseBusinessRules.checkIfStockIsNotEnough(purchaseRequest.getProductId(), id, purchaseRequest.getQuantity());
        Purchase updatedPurchase = modelMapperService.forRequest().map(purchaseRequest, Purchase.class);
        Purchase foundPurchase = purchaseRepository.findById(id).orElseThrow();
        Product selectedProduct = productRepository.findById(purchaseRequest.getProductId()).orElseThrow();
        Supplier selectedSupplier = supplierRepository.findById(purchaseRequest.getSupplierId()).orElseThrow();
        updatedPurchase.setId(id);
        updatedPurchase.setProduct(selectedProduct);
        updatedPurchase.setSupplier(selectedSupplier);
        updatedPurchase.setTotalPrice(purchaseRequest.getPurchasePrice() * purchaseRequest.getQuantity());
        updatedPurchase.setPurchaseDate(purchaseRequest.getPurchaseDate().plusHours(1));
        updatedPurchase.setActive(foundPurchase.isActive());
        updatedPurchase.setCreatedAt(foundPurchase.getCreatedAt());
        updatedPurchase.setUpdatedAt(LocalDateTime.now());
        updatedPurchase = purchaseRepository.save(updatedPurchase);

        StockMovementRequest stockMovementRequest = StockMovementRequest.builder()
                .productId(purchaseRequest.getProductId())
                .build();
        if (foundPurchase.getQuantity() > purchaseRequest.getQuantity()) {
            stockMovementRequest.setQuantity(foundPurchase.getQuantity() - purchaseRequest.getQuantity());
            stockMovementRequest.setType(false);
            stockMovementRequest.setReason(StockMovementReason.PURCHASE_RETURN);
        } else {
            stockMovementRequest.setQuantity(purchaseRequest.getQuantity() - foundPurchase.getQuantity());
            stockMovementRequest.setType(true);
            stockMovementRequest.setReason(StockMovementReason.PURCHASE);
        }
        stockMovementService.addStockMovement(stockMovementRequest);

        return modelMapperService.forResponse().map(updatedPurchase, PurchaseCreatedResponse.class);
    }

    @Override
    public PurchaseCreatedResponse changePurchaseStatus(String id, boolean status) {
        purchaseBusinessRules.checkIfPurchaseByIdNotFound(id);
        Purchase purchase = purchaseRepository.findById(id).orElseThrow();
        purchaseBusinessRules.checkIfStockIsNotEnough(purchase.getProduct().getStock(), purchase.getQuantity(), status);
        purchase.setActive(status);
        purchase.setUpdatedAt(LocalDateTime.now());
        purchase = purchaseRepository.save(purchase);

        StockMovementRequest stockMovementRequest = StockMovementRequest.builder()
                .productId(purchase.getProduct().getId())
                .build();
        if (status) {
            stockMovementRequest.setQuantity(purchase.getQuantity());
            stockMovementRequest.setType(true);
            stockMovementRequest.setReason(StockMovementReason.PURCHASE);
        } else {
            stockMovementRequest.setQuantity(purchase.getQuantity());
            stockMovementRequest.setType(false);
            stockMovementRequest.setReason(StockMovementReason.PURCHASE_RETURN);
        }
        stockMovementService.addStockMovement(stockMovementRequest);

        return modelMapperService.forResponse().map(purchase, PurchaseCreatedResponse.class);
    }
}