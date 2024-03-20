package net.ugurkartal.backend.services.concretes;

import net.ugurkartal.backend.core.mappers.ModelMapperService;
import net.ugurkartal.backend.services.abstracts.IdService;
import net.ugurkartal.backend.services.dtos.requests.ProductCreateRequest;
import net.ugurkartal.backend.services.dtos.responses.ProductCreatedResponse;
import net.ugurkartal.backend.models.Category;
import net.ugurkartal.backend.models.Product;
import net.ugurkartal.backend.repositories.CategoryRepository;
import net.ugurkartal.backend.repositories.ProductRepository;
import net.ugurkartal.backend.services.dtos.responses.ProductGetAllResponse;
import net.ugurkartal.backend.services.rules.CategoryBusinessRules;
import net.ugurkartal.backend.services.rules.ProductBusinessRules;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductManagerTest {

    @InjectMocks
    private ProductManager productManager;
    private ModelMapper modelMapper;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryBusinessRules categoryBusinessRules;

    @Mock
    private ProductBusinessRules productBusinessRules;

    @Mock
    private ModelMapperService modelMapperService;

    @Mock
    private IdService idService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        modelMapper = mock(ModelMapper.class);
    }

    @Test
    void getAllProductsReturnsListOfProducts() {
        List<Product> products = List.of(
                Product.builder().id("1").build(),
                Product.builder().id("2").build()
        );

        when(modelMapperService.forResponse()).thenReturn(modelMapper);
        when(productRepository.findAll()).thenReturn(products);

        List<ProductGetAllResponse> response = productManager.getAllProducts();

        assertEquals(2, response.size());
    }

    @Test
    void getProductByIdReturnsProductWhenProductExists() {
        Product product = Product.builder()
                .id("1")
                .name("Test")
                .description("Test")
                .salePrice(10.0)
                .stock(10)
                .criticalStock(5)
                .imageUrl("https://test.com")
                .category(Category.builder().id("1").build())
                .build();

        ProductCreatedResponse expectedResponse = ProductCreatedResponse.builder()
                .id("1")
                .name("Test")
                .description("Test")
                .salePrice(10.0)
                .stock(10)
                .criticalStock(5)
                .imageUrl("https://test.com")
                .category(Category.builder().id("1").build())
                .build();

        String id = "1";
        when(modelMapperService.forResponse()).thenReturn(modelMapper);
        when(modelMapper.map(product, ProductCreatedResponse.class)).thenReturn(ProductCreatedResponse.builder().id("1").build());
        when(productRepository.findById(id)).thenReturn(Optional.of(product));

        ProductCreatedResponse actual = productManager.getProductById(id);

        assertEquals(expectedResponse.getId(), actual.getId());
    }

    @Test
    void getProductByIdThrowsNoSuchElementExceptionWhenProductDoesNotExist() {
        String id = "1";
        when(productRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> productManager.getProductById(id));
    }

    @Test
    void addProductReturnsCreatedResponseWhenProductIsValid() {
        Product product = Product.builder()
                .id("1")
                .name("Test")
                .description("Test")
                .salePrice(10.0)
                .stock(10)
                .criticalStock(5)
                .imageUrl("https://test.com")
                .category(Category.builder().id("1").build())
                .build();

        ProductCreatedResponse expectedResponse = ProductCreatedResponse.builder()
                .id("1")
                .name("Test")
                .description("Test")
                .salePrice(10.0)
                .stock(10)
                .criticalStock(5)
                .imageUrl("https://test.com")
                .category(Category.builder().id("1").build())
                .build();

        ProductCreateRequest request = ProductCreateRequest.builder()
                .name("Test")
                .description("Test")
                .salePrice(10.0)
                .stock(10)
                .criticalStock(5)
                .categoryId("1")
                .imageUrl("https://test.com")
                .build();

        when(idService.generateCategoryId()).thenReturn("1");
        when(modelMapperService.forRequest()).thenReturn(modelMapper);
        when(modelMapperService.forResponse()).thenReturn(modelMapper);
        when(modelMapper.map(request, Product.class)).thenReturn(product);
        when(categoryRepository.findById("1")).thenReturn(Optional.of(Category.builder().id("1").build()));
        when(productRepository.save(product)).thenReturn(product);
        when(modelMapper.map(product, ProductCreatedResponse.class)).thenReturn(ProductCreatedResponse.builder().id("1").build());

        ProductCreatedResponse actualResponse = productManager.addProduct(request);

        verify(productBusinessRules, times(1)).checkIfProductNameExists(anyString());
        verify(categoryBusinessRules, times(1)).checkIfCategoryByIdNotFound(anyString());
        verify(productRepository, times(1)).save(product);
        assertEquals(expectedResponse.getId(), actualResponse.getId());
    }

    @Test
    void addProductThrowsNoSuchElementExceptionWhenCategoryIsInvalid() {
        ProductCreateRequest request = ProductCreateRequest.builder()
                .name("Test")
                .description("Test")
                .salePrice(10.0)
                .stock(10)
                .criticalStock(5)
                .categoryId("2")
                .imageUrl("https://test.com")
                .build();

        Product product = Product.builder()
                .id("1")
                .name("Test")
                .description("Test")
                .salePrice(10.0)
                .stock(10)
                .criticalStock(5)
                .imageUrl("https://test.com")
                .category(Category.builder().id("1").build())
                .build();

        when(idService.generateCategoryId()).thenReturn("1");
        when(modelMapperService.forRequest()).thenReturn(modelMapper);
        when(modelMapperService.forResponse()).thenReturn(modelMapper);
        when(modelMapper.map(request, Product.class)).thenReturn(product);
        when(categoryRepository.findById("1")).thenReturn(Optional.of(Category.builder().id("1").build()));
        when(productRepository.save(product)).thenReturn(product);
        when(modelMapper.map(product, ProductCreatedResponse.class)).thenReturn(ProductCreatedResponse.builder().id("1").build());

        assertThrows(NoSuchElementException.class, () -> productManager.addProduct(request));
    }
}