package net.ugurkartal.backend.services.concretes;

import net.ugurkartal.backend.core.mappers.ModelMapperService;
import net.ugurkartal.backend.models.Product;
import net.ugurkartal.backend.repositories.ProductRepository;
import net.ugurkartal.backend.services.dtos.responses.ProductGetAllResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ReportManagerTest {
    @InjectMocks
    private ReportManager reportManager;
    private ModelMapper modelMapper;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ModelMapperService modelMapperService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        modelMapper = mock(ModelMapper.class);
    }

    @Test
    void getProductsOutOfStock_whenNoProductsOutOfStock_shouldReturnEmptyList() {
        // Given
        List<Product> products = List.of();

        // When
        when(productRepository.findByIsActiveAndStock(true, 0)).thenReturn(products);

        List<ProductGetAllResponse> response = reportManager.getProductsOutOfStock();

        // Then
        assertTrue(response.isEmpty());
    }

    @Test
    void getProductsOutOfStock_whenSomeProductsOutOfStock_shouldReturnThoseProducts() {
        // Given
        List<Product> products = List.of(
                Product.builder().id("1").stock(0).build(),
                Product.builder().id("2").stock(0).build()
        );

        List<ProductGetAllResponse> expectedResponse = List.of(
                new ProductGetAllResponse(),
                new ProductGetAllResponse()
        );

        // When
        when(modelMapperService.forResponse()).thenReturn(modelMapper);
        when(productRepository.findByIsActiveAndStock(true, 0)).thenReturn(products);
        when(modelMapper.map(products.get(0), ProductGetAllResponse.class)).thenReturn(expectedResponse.get(0));
        when(modelMapper.map(products.get(1), ProductGetAllResponse.class)).thenReturn(expectedResponse.get(1));

        List<ProductGetAllResponse> response = reportManager.getProductsOutOfStock();

        // Then
        assertEquals(expectedResponse, response);
    }

    @Test
    void getProductsOutOfStock_whenAllProductsOutOfStock_shouldReturnAllProducts() {
        // Given
        List<Product> products = List.of(
                Product.builder().id("1").stock(0).build(),
                Product.builder().id("2").stock(0).build(),
                Product.builder().id("3").stock(0).build()
        );

        List<ProductGetAllResponse> expectedResponse = List.of(
                new ProductGetAllResponse(),
                new ProductGetAllResponse(),
                new ProductGetAllResponse()
        );

        // When
        when(productRepository.findByIsActiveAndStock(true, 0)).thenReturn(products);
        when(modelMapperService.forResponse()).thenReturn(modelMapper);
        when(modelMapper.map(products.get(0), ProductGetAllResponse.class)).thenReturn(expectedResponse.get(0));
        when(modelMapper.map(products.get(1), ProductGetAllResponse.class)).thenReturn(expectedResponse.get(1));
        when(modelMapper.map(products.get(2), ProductGetAllResponse.class)).thenReturn(expectedResponse.get(2));

        List<ProductGetAllResponse> response = reportManager.getProductsOutOfStock();

        // Then
        assertEquals(expectedResponse, response);
    }

    @Test
    void getProductsLowInStock_whenSomeProductsLowInStock_shouldReturnThoseProducts() {
        // Given
        List<Product> products = List.of(
                Product.builder().id("1").stock(5).criticalStock(10).build(),
                Product.builder().id("2").stock(10).criticalStock(20).build(),
                Product.builder().id("3").stock(20).criticalStock(10).build()
        );

        List<ProductGetAllResponse> expectedResponse = List.of(
                new ProductGetAllResponse(),
                new ProductGetAllResponse()
        );

        // When
        when(productRepository.findAll()).thenReturn(products);
        when(modelMapperService.forResponse()).thenReturn(modelMapper);
        when(modelMapper.map(products.get(0), ProductGetAllResponse.class)).thenReturn(expectedResponse.get(0));
        when(modelMapper.map(products.get(1), ProductGetAllResponse.class)).thenReturn(expectedResponse.get(1));

        List<ProductGetAllResponse> response = reportManager.getProductsLowInStock();

        // Then
        assertEquals(expectedResponse, response);
    }
}