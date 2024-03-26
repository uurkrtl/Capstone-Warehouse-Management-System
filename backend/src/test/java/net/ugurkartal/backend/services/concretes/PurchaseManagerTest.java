package net.ugurkartal.backend.services.concretes;

import net.ugurkartal.backend.core.mappers.ModelMapperService;
import net.ugurkartal.backend.models.Product;
import net.ugurkartal.backend.models.Purchase;
import net.ugurkartal.backend.models.Supplier;
import net.ugurkartal.backend.repositories.ProductRepository;
import net.ugurkartal.backend.repositories.PurchaseRepository;
import net.ugurkartal.backend.repositories.SupplierRepository;
import net.ugurkartal.backend.services.abstracts.IdService;
import net.ugurkartal.backend.services.dtos.requests.PurchaseRequest;
import net.ugurkartal.backend.services.dtos.responses.PurchaseCreatedResponse;
import net.ugurkartal.backend.services.rules.PurchaseBusinessRules;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    @Mock
    private IdService idService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        modelMapper = mock(ModelMapper.class);
    }

    @Test
    void addPurchase_whenPurchaseRequestIsValid_shouldReturnPurchaseCreatedResponse() {
        // Given
        Purchase purchase = Purchase.builder().id("1").build();
        PurchaseCreatedResponse expectedResponse = PurchaseCreatedResponse.builder().id("1").purchaseDate(LocalDate.now()).build();
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
}