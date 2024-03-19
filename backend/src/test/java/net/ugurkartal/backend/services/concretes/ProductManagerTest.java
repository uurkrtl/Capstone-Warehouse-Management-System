package net.ugurkartal.backend.services.concretes;

import net.ugurkartal.backend.services.dtos.requests.ProductCreateRequest;
import net.ugurkartal.backend.services.dtos.responses.ProductCreatedResponse;
import net.ugurkartal.backend.models.Category;
import net.ugurkartal.backend.models.Product;
import net.ugurkartal.backend.repositories.CategoryRepository;
import net.ugurkartal.backend.repositories.ProductRepository;
import net.ugurkartal.backend.services.rules.CategoryBusinessRules;
import net.ugurkartal.backend.services.rules.ProductBusinessRules;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ProductManagerTest {

    @InjectMocks
    private ProductManager productManager;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryBusinessRules categoryBusinessRules;

    @Mock
    private ProductBusinessRules productBusinessRules;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addProductReturnsCreatedResponseWhenProductIsValid() {
        ProductCreateRequest request = new ProductCreateRequest();
        // Fill the request with valid data
        Product product = new Product();
        // Fill the product with valid data
        Category category = new Category();
        // Fill the category with valid data

        when(categoryRepository.findById(anyString())).thenReturn(Optional.of(category));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        ProductCreatedResponse actualResponse = productManager.addProduct(request);

        verify(productBusinessRules, times(1)).checkIfProductNameExists(anyString());
        verify(categoryBusinessRules, times(1)).checkIfCategoryByIdNotFound(anyString());
        verify(productRepository, times(1)).save(any(Product.class));
        assertEquals(product.getId(), actualResponse.getId());
    }

    @Test
    void addProductThrowsExceptionWhenProductIsInvalid() {
        ProductCreateRequest request = new ProductCreateRequest();
        // Fill the request with invalid data

        doThrow(new IllegalArgumentException()).when(categoryBusinessRules).checkIfCategoryByIdNotFound(anyString());

        assertThrows(IllegalArgumentException.class, () -> productManager.addProduct(request));

        verify(productBusinessRules, times(1)).checkIfProductNameExists(anyString());
        verify(categoryBusinessRules, times(1)).checkIfCategoryByIdNotFound(anyString());
    }
}