package net.ugurkartal.backend.services.rules;

import lombok.RequiredArgsConstructor;
import net.ugurkartal.backend.core.exceptions.types.NegativeStockException;
import net.ugurkartal.backend.core.exceptions.types.RecordNotFoundException;
import net.ugurkartal.backend.repositories.ProductRepository;
import net.ugurkartal.backend.repositories.PurchaseRepository;
import net.ugurkartal.backend.repositories.SupplierRepository;
import net.ugurkartal.backend.services.messages.PurchaseMessage;
import net.ugurkartal.backend.services.messages.SupplierMessage;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PurchaseBusinessRules {
    private final PurchaseRepository purchaseRepository;
    private final ProductRepository productRepository;
    private final SupplierRepository supplierRepository;

    public void checkIfProductByIdNotFound(String productId) {
        if(!this.productRepository.existsById(productId)) {
            throw new RecordNotFoundException(PurchaseMessage.PRODUCT_NOT_FOUND);
        }
    }

    public void checkIfSupplierByIdNotFound(String supplierId) {
        if(!this.supplierRepository.existsById(supplierId)) {
            throw new RecordNotFoundException(PurchaseMessage.SUPPLIER_NOT_FOUND);
        }
    }

    public void checkIfPurchaseByIdNotFound(String id) {
        if(!this.purchaseRepository.existsById(id)) {
            throw new RecordNotFoundException(SupplierMessage.PURCHASE_NOT_FOUND);
        }
    }

    public void checkIfStockIsNotEnough(String productId, String purchaseId, int quantity) {
        int stockIncrease = quantity - purchaseRepository.findById(purchaseId).orElseThrow().getQuantity();
        if (stockIncrease < 0 && productRepository.findById(productId).orElseThrow().getStock() < Math.abs(stockIncrease)) {
            throw new NegativeStockException(PurchaseMessage.STOCK_NOT_ENOUGH);
        }
    }

    public void checkIfStockIsNotEnough(int stock, int quantity, boolean status) {
        if (stock < quantity && !status) {
            throw new NegativeStockException(PurchaseMessage.STOCK_NOT_ENOUGH);
        }
    }
}