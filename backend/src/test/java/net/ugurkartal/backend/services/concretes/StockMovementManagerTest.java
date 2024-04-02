package net.ugurkartal.backend.services.concretes;

import net.ugurkartal.backend.core.mappers.ModelMapperService;
import net.ugurkartal.backend.models.Product;
import net.ugurkartal.backend.models.StockMovement;
import net.ugurkartal.backend.repositories.ProductRepository;
import net.ugurkartal.backend.repositories.StockMovementRepository;
import net.ugurkartal.backend.services.abstracts.IdService;
import net.ugurkartal.backend.services.dtos.requests.StockMovementRequest;
import net.ugurkartal.backend.services.dtos.responses.StockMovementCreatedResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class StockMovementManagerTest {
    @InjectMocks
    private StockMovementManager stockMovementManager;
    private ModelMapper modelMapper;

    @Mock
    private StockMovementRepository stockMovementRepository;

    @Mock
    private ProductRepository productRepository;

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
    void addStockMovement_whenStockMovementRequestIsValid_shouldReturnStockMovementCreatedResponse() {
        // Given
        StockMovement stockMovement = StockMovement.builder().id("1").build();
        StockMovementCreatedResponse expectedResponse = StockMovementCreatedResponse.builder().id("1").build();
        StockMovementRequest stockMovementRequest = StockMovementRequest.builder().productId("1").productId("1").build();

        // When
        when(idService.generateStockMovementId()).thenReturn("1");
        when(modelMapperService.forRequest()).thenReturn(modelMapper);
        when(modelMapperService.forResponse()).thenReturn(modelMapper);
        when(modelMapper.map(stockMovementRequest, StockMovement.class)).thenReturn(stockMovement);
        when(productRepository.findById("1")).thenReturn(Optional.of(Product.builder().id("1").build()));
        when(stockMovementRepository.findById("1")).thenReturn(Optional.of(StockMovement.builder().id("1").build()));
        when(stockMovementRepository.save(stockMovement)).thenReturn(stockMovement);
        when(modelMapper.map(stockMovement, StockMovementCreatedResponse.class)).thenReturn(expectedResponse);

        StockMovementCreatedResponse actualResponse = stockMovementManager.addStockMovement(stockMovementRequest);

        // Then
        verify(stockMovementRepository, times(1)).save(stockMovement);
        assertEquals(expectedResponse.getId(), actualResponse.getId());
    }
}