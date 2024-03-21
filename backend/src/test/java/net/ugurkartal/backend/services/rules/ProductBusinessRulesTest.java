package net.ugurkartal.backend.services.rules;

import net.ugurkartal.backend.core.exceptions.types.DuplicateRecordException;
import net.ugurkartal.backend.core.exceptions.types.RecordNotFoundException;
import net.ugurkartal.backend.models.Product;
import net.ugurkartal.backend.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class ProductBusinessRulesTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductBusinessRules productBusinessRules;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void productNameExists_throwsDuplicateRecordException() {
        String existingProductName = "Existing Product";
        when(productRepository.existsByName(existingProductName)).thenReturn(true);

        assertThrows(DuplicateRecordException.class, () -> productBusinessRules.checkIfProductNameExists(existingProductName));
    }

    @Test
    void productNameDoesNotExist_doesNotThrowException() {
        String nonExistingProductName = "Non Existing Product";
        when(productRepository.existsByName(nonExistingProductName)).thenReturn(false);

        assertDoesNotThrow(() -> productBusinessRules.checkIfProductNameExists(nonExistingProductName));
    }

    @Test
    void checkIfProductNameExistsThrowsExceptionWhenProductNameAlreadyExistsForDifferentProduct() {
        String existingProductName = "Existing Product";
        String newProductName = "New Product";
        String existingProductId = "1";

        Product existingProduct = new Product();
        existingProduct.setId(existingProductId);
        existingProduct.setName(existingProductName);

        when(productRepository.findById(existingProductId)).thenReturn(Optional.of(existingProduct));

        when(productRepository.existsByName(newProductName)).thenReturn(true);

        assertThrows(DuplicateRecordException.class, () -> productBusinessRules.checkIfProductNameExists(newProductName, existingProductId));
    }

    @Test
    void productByIdNotFound_throwsRecordNotFoundException() {
        String nonExistingProductId = "Non Existing Product Id";
        when(productRepository.existsById(nonExistingProductId)).thenReturn(false);

        assertThrows(RecordNotFoundException.class, () -> productBusinessRules.checkIfProductByIdNotFound(nonExistingProductId));
    }

    @Test
    void productByIdFound_doesNotThrowException() {
        String existingProductId = "Existing Product Id";
        when(productRepository.existsById(existingProductId)).thenReturn(true);

        assertDoesNotThrow(() -> productBusinessRules.checkIfProductByIdNotFound(existingProductId));
    }
}