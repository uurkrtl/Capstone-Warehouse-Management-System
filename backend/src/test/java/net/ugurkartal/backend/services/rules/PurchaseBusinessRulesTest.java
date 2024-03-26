package net.ugurkartal.backend.services.rules;

import net.ugurkartal.backend.core.exceptions.types.RecordNotFoundException;
import net.ugurkartal.backend.repositories.CategoryRepository;
import net.ugurkartal.backend.repositories.ProductRepository;
import net.ugurkartal.backend.repositories.SupplierRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class PurchaseBusinessRulesTest {
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

}