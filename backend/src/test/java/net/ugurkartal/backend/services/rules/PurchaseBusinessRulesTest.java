package net.ugurkartal.backend.services.rules;

import net.ugurkartal.backend.core.exceptions.types.NegativeStockException;
import net.ugurkartal.backend.core.exceptions.types.RecordNotFoundException;
import net.ugurkartal.backend.models.Product;
import net.ugurkartal.backend.models.Purchase;
import net.ugurkartal.backend.repositories.ProductRepository;
import net.ugurkartal.backend.repositories.PurchaseRepository;
import net.ugurkartal.backend.repositories.SupplierRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class PurchaseBusinessRulesTest {

    @Mock
    private PurchaseRepository purchaseRepository;
    @Mock
    private SupplierRepository supplierRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private PurchaseBusinessRules purchaseBusinessRules;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void checkIfProductByIdNotFound_throwsRecordNotFoundException_whenProductDoesNotExist() {
        String nonExistingProductId = "Non Existing Product Id";
        when(productRepository.existsById(nonExistingProductId)).thenReturn(false);

        assertThrows(RecordNotFoundException.class, () -> purchaseBusinessRules.checkIfProductByIdNotFound(nonExistingProductId));
    }

    @Test
    void checkIfProductByIdNotFound_doesNotThrowException_whenProductExists() {
        String existingProductId = "Existing Product Id";
        when(productRepository.existsById(existingProductId)).thenReturn(true);

        assertDoesNotThrow(() -> purchaseBusinessRules.checkIfProductByIdNotFound(existingProductId));
    }

    @Test
    void checkIfSupplierByIdNotFound_throwsRecordNotFoundException_whenSupplierDoesNotExist() {
        String nonExistingSupplierId = "Non Existing Supplier Id";
        when(supplierRepository.existsById(nonExistingSupplierId)).thenReturn(false);

        assertThrows(RecordNotFoundException.class, () -> purchaseBusinessRules.checkIfSupplierByIdNotFound(nonExistingSupplierId));
    }

    @Test
    void checkIfSupplierByIdNotFound_doesNotThrowException_whenSupplierExists() {
        String existingSupplierId = "Existing Supplier Id";
        when(supplierRepository.existsById(existingSupplierId)).thenReturn(true);

        assertDoesNotThrow(() -> purchaseBusinessRules.checkIfSupplierByIdNotFound(existingSupplierId));
    }

    @Test
    void checkIfPurchaseByIdNotFound_throwsRecordNotFoundException_whenPurchaseDoesNotExist() {
        String nonExistingPurchaseId = "Non Existing Purchase Id";
        when(purchaseRepository.existsById(nonExistingPurchaseId)).thenReturn(false);

        assertThrows(RecordNotFoundException.class, () -> purchaseBusinessRules.checkIfPurchaseByIdNotFound(nonExistingPurchaseId));
    }

    @Test
    void checkIfPurchaseByIdNotFound_doesNotThrowException_whenPurchaseExists() {
        String existingPurchaseId = "Existing Purchase Id";
        when(purchaseRepository.existsById(existingPurchaseId)).thenReturn(true);

        assertDoesNotThrow(() -> purchaseBusinessRules.checkIfPurchaseByIdNotFound(existingPurchaseId));
    }

    @Test
    void checkIfStockIsNotEnough_throwsNegativeStockException() {
        String productId = "Existing Product Id";
        String purchaseId = "Existing Purchase Id";
        int quantity = 1;

        when(purchaseRepository.findById(purchaseId)).thenReturn(Optional.of(new Purchase(){{
            setQuantity(5);
        }}));

        when(productRepository.findById(productId)).thenReturn(Optional.of(new Product(){{
            setStock(3);
        }}));

        assertThrows(NegativeStockException.class, () -> purchaseBusinessRules.checkIfStockIsNotEnough(productId, purchaseId, quantity));
    }
}