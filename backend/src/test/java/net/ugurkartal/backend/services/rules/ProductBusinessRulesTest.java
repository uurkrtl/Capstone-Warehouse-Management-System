package net.ugurkartal.backend.services.rules;

import net.ugurkartal.backend.core.exceptions.types.CategoryOfProductPassiveException;
import net.ugurkartal.backend.core.exceptions.types.DuplicateRecordException;
import net.ugurkartal.backend.core.exceptions.types.RecordNotFoundException;
import net.ugurkartal.backend.core.exceptions.types.StockNotZeroException;
import net.ugurkartal.backend.models.Category;
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

    @Test
    void checkIfProductStockNotZero_doesNotThrowExceptionWhenStockIsZeroAndStatusIsFalse() {
        String productId = "1";
        boolean status = false;

        Product product = Product.builder()
                .id(productId)
                .stock(0)
                .build();

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        assertDoesNotThrow(() -> productBusinessRules.checkIfProductStockNotZero(productId, status));
    }

    @Test
    void checkIfProductStockNotZero_throwsStockNotZeroExceptionWhenStockIsGreaterThanZeroAndStatusIsFalse() {
        String productId = "1";
        boolean status = false;

        Product product = Product.builder()
                .id(productId)
                .stock(1)
                .build();

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        assertThrows(StockNotZeroException.class, () -> productBusinessRules.checkIfProductStockNotZero(productId, status));
    }

    @Test
    void checkIfCategoryOfProductPassive_whenCategoryIsPassiveAndProductStatusIsTrue_throwsCategoryOfProductPassiveException() {
        String productId = "1";
        boolean productStatus = true;

        Category category = new Category();
        category.setActive(false);

        Product product = new Product();
        product.setId(productId);
        product.setCategory(category);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        assertThrows(CategoryOfProductPassiveException.class, () -> productBusinessRules.checkIfCategoryOfProductPassive(productId, productStatus));
    }

    @Test
    void checkIfCategoryOfProductPassive_whenCategoryIsActiveAndProductStatusIsTrue_doesNotThrowException() {
        String productId = "1";
        boolean productStatus = true;

        Category category = new Category();
        category.setActive(true);

        Product product = new Product();
        product.setId(productId);
        product.setCategory(category);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        assertDoesNotThrow(() -> productBusinessRules.checkIfCategoryOfProductPassive(productId, productStatus));
    }
}