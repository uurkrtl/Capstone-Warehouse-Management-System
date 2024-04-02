package net.ugurkartal.backend.services.concretes;

import net.ugurkartal.backend.core.mappers.ModelMapperService;
import net.ugurkartal.backend.models.Product;
import net.ugurkartal.backend.models.Purchase;
import net.ugurkartal.backend.models.Supplier;
import net.ugurkartal.backend.repositories.ProductRepository;
import net.ugurkartal.backend.repositories.PurchaseRepository;
import net.ugurkartal.backend.repositories.SupplierRepository;
import net.ugurkartal.backend.services.abstracts.IdService;
import net.ugurkartal.backend.services.abstracts.StockMovementService;
import net.ugurkartal.backend.services.dtos.requests.PurchaseRequest;
import net.ugurkartal.backend.services.dtos.responses.PurchaseCreatedResponse;
import net.ugurkartal.backend.services.dtos.responses.PurchaseGetAllResponse;
import net.ugurkartal.backend.services.rules.PurchaseBusinessRules;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class PurchaseManagerTest {
    @InjectMocks
    private PurchaseManager purchaseManager;
    private ModelMapper modelMapper;

    @Mock
    private PurchaseRepository purchaseRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private SupplierRepository supplierRepository;

    @Mock
    private PurchaseBusinessRules purchaseBusinessRules;

    @Mock
    private ModelMapperService modelMapperService;

    @SuppressWarnings("unused")
    @Mock
    private StockMovementService stockMovementService;

    @Mock
    private IdService idService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        modelMapper = mock(ModelMapper.class);
    }

    @Test
    void getAllPurchases_shouldReturnsListOfPurchases() {
        // Given
        List<Purchase> purchases = List.of(
                Purchase.builder().id("1").build(),
                Purchase.builder().id("2").build()
        );

        // When
        when(modelMapperService.forResponse()).thenReturn(modelMapper);
        when(purchaseRepository.findAll()).thenReturn(purchases);

        List<PurchaseGetAllResponse> response = purchaseManager.getAllPurchases();

        // Then
        assertEquals(2, response.size());
    }

    @Test
    void getPurchaseById_whenPurchaseExists_shouldReturnPurchase() {
        // Given
        Purchase purchase = Purchase.builder().id("1").build();
        PurchaseCreatedResponse expectedResponse = PurchaseCreatedResponse.builder().id("1").build();

        // When
        when(modelMapperService.forResponse()).thenReturn(modelMapper);
        when(modelMapper.map(purchase, PurchaseCreatedResponse.class)).thenReturn(expectedResponse);
        when(purchaseRepository.findById("1")).thenReturn(Optional.of(purchase));

        PurchaseCreatedResponse actual = purchaseManager.getPurchaseById("1");

        // Then
        assertEquals(expectedResponse.getId(), actual.getId());
    }

    @Test
    void getPurchaseById_WhenPurchaseDoesNotExist_shouldThrowsNoSuchElementException() {
        // Given & When
        when(purchaseRepository.findById("1")).thenReturn(Optional.empty());

        // Then
        assertThrows(NoSuchElementException.class, () -> purchaseManager.getPurchaseById("1"));
    }

    @Test
    void addPurchase_whenPurchaseRequestIsValid_shouldReturnPurchaseCreatedResponse() {
        // Given
        Purchase purchase = Purchase.builder().id("1").build();
        PurchaseCreatedResponse expectedResponse = PurchaseCreatedResponse.builder().id("1").purchaseDate(LocalDateTime.now()).build();
        PurchaseRequest purchaseRequest = PurchaseRequest.builder().productId("1").supplierId("1").purchaseDate(LocalDateTime.now()).build();

        // When
        when(idService.generatePurchaseId()).thenReturn("1");
        when(modelMapperService.forRequest()).thenReturn(modelMapper);
        when(modelMapperService.forResponse()).thenReturn(modelMapper);
        when(modelMapper.map(purchaseRequest, Purchase.class)).thenReturn(purchase);
        when(productRepository.findById("1")).thenReturn(Optional.of(Product.builder().id("1").build()));
        when(supplierRepository.findById("1")).thenReturn(Optional.of(Supplier.builder().id("1").build()));
        when(purchaseRepository.save(purchase)).thenReturn(purchase);
        when(modelMapper.map(purchase, PurchaseCreatedResponse.class)).thenReturn(expectedResponse);

        PurchaseCreatedResponse actualResponse = purchaseManager.addPurchase(purchaseRequest);

        // Then
        verify(purchaseBusinessRules, times(1)).checkIfSupplierByIdNotFound(anyString());
        verify(purchaseBusinessRules, times(1)).checkIfProductByIdNotFound(anyString());
        verify(purchaseRepository, times(1)).save(purchase);
        assertEquals(expectedResponse.getId(), actualResponse.getId());
    }

    @Test
    void updatePurchase_whenPurchaseRequestIsValid_shouldReturnPurchaseCreatedResponse() {
        // Given
        Purchase purchase = Purchase.builder()
                .id("1")
                .product(Product.builder().id("1").build())
                .supplier(Supplier.builder().id("1").build())
                .purchasePrice(100.0)
                .quantity(10)
                .totalPrice(1000.0)
                .purchaseDate(LocalDateTime.now())
                .build();

        PurchaseCreatedResponse expectedResponse = PurchaseCreatedResponse.builder()
                .id("1")
                .productId("1")
                .supplierId("1")
                .purchasePrice(100.0)
                .quantity(10)
                .totalPrice(1000.0)
                .purchaseDate(LocalDateTime.now())
                .build();

        PurchaseRequest purchaseRequest = PurchaseRequest.builder()
                .productId("1")
                .supplierId("1")
                .purchasePrice(100.0)
                .quantity(10)
                .purchaseDate(LocalDateTime.now())
                .build();

        // When
        when(modelMapperService.forRequest()).thenReturn(modelMapper);
        when(modelMapperService.forResponse()).thenReturn(modelMapper);
        when(modelMapper.map(purchaseRequest, Purchase.class)).thenReturn(purchase);
        when(purchaseRepository.findById("1")).thenReturn(Optional.of(purchase));
        when(productRepository.findById("1")).thenReturn(Optional.of(Product.builder().id("1").build()));
        when(supplierRepository.findById("1")).thenReturn(Optional.of(Supplier.builder().id("1").build()));
        when(purchaseRepository.save(purchase)).thenReturn(purchase);
        when(modelMapper.map(purchase, PurchaseCreatedResponse.class)).thenReturn(expectedResponse);

        PurchaseCreatedResponse actualResponse = purchaseManager.updatePurchase("1", purchaseRequest);

        // Then
        verify(purchaseBusinessRules, times(1)).checkIfProductByIdNotFound(anyString());
        verify(purchaseBusinessRules, times(1)).checkIfPurchaseByIdNotFound(anyString());
        verify(purchaseBusinessRules, times(1)).checkIfSupplierByIdNotFound(anyString());
        verify(purchaseRepository, times(1)).save(purchase);
        assertEquals(expectedResponse.getId(), actualResponse.getId());
        assertEquals(expectedResponse.getProductId(), actualResponse.getProductId());
        assertEquals(expectedResponse.getSupplierId(), actualResponse.getSupplierId());
        assertEquals(expectedResponse.getPurchasePrice(), actualResponse.getPurchasePrice());
        assertEquals(expectedResponse.getQuantity(), actualResponse.getQuantity());
        assertEquals(expectedResponse.getPurchaseDate(), actualResponse.getPurchaseDate());
    }

    @Test
    void changePurchaseStatus_whenPurchaseExists_shouldChangeStatusCorrectly() {
        // Given
        String id = "1";
        Product product = Product.builder().id("1").stock(10).build();

        Purchase updatedPurchase = Purchase.builder()
                .id("1")
                .product(product)
                .isActive(true)
                .build();

        PurchaseCreatedResponse expectedResponse = PurchaseCreatedResponse.builder()
                .id("1")
                .isActive(true)
                .build();

        // When
        when(modelMapperService.forResponse()).thenReturn(modelMapper);
        when(purchaseRepository.findById("1")).thenReturn(Optional.of(Purchase.builder().id("1").product(product).build()));
        when(purchaseRepository.save(any(Purchase.class))).thenReturn(updatedPurchase);
        when(modelMapper.map(updatedPurchase, PurchaseCreatedResponse.class)).thenReturn(expectedResponse);

        PurchaseCreatedResponse actualResponse = purchaseManager.changePurchaseStatus(id, true);

        // Then
        verify(purchaseBusinessRules, times(1)).checkIfPurchaseByIdNotFound(anyString());
        verify(purchaseRepository, times(1)).save(any(Purchase.class));
        assertEquals(expectedResponse.isActive(), actualResponse.isActive());
    }
}