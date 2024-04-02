package net.ugurkartal.backend.services.concretes;

import net.ugurkartal.backend.core.mappers.ModelMapperService;
import net.ugurkartal.backend.models.Order;
import net.ugurkartal.backend.models.OrderDetail;
import net.ugurkartal.backend.models.Product;
import net.ugurkartal.backend.repositories.OrderDetailRepository;
import net.ugurkartal.backend.repositories.OrderRepository;
import net.ugurkartal.backend.repositories.ProductRepository;
import net.ugurkartal.backend.services.abstracts.IdService;
import net.ugurkartal.backend.services.abstracts.StockMovementService;
import net.ugurkartal.backend.services.dtos.requests.OrderDetailRequest;
import net.ugurkartal.backend.services.dtos.responses.OrderDetailCreatedResponse;
import net.ugurkartal.backend.services.rules.OrderDetailBusinessRules;
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

class OrderDetailManagerTest {
    @InjectMocks
    private OrderDetailManager orderDetailManager;
    private ModelMapper modelMapper;

    @Mock
    private OrderDetailRepository orderDetailRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private OrderDetailBusinessRules orderDetailBusinessRules;

    @Mock
    private ModelMapperService modelMapperService;

    @Mock
    private IdService idService;

    @SuppressWarnings("unused")
    @Mock
    private StockMovementService stockMovementService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        modelMapper = mock(ModelMapper.class);
    }

    @Test
    void addOrderDetail_whenOrderDetailRequestIsValid_shouldReturnOrderDetailCreatedResponse() {
        // Given
        OrderDetail orderDetail = OrderDetail.builder()
                .id("1")
                .product(Product.builder().id("1").build())
                .order(Order.builder().id("1").build())
                .build();
        OrderDetailCreatedResponse expectedResponse = OrderDetailCreatedResponse.builder()
                .id("1")
                .productId("1")
                .orderId("1")
                .build();
        OrderDetailRequest orderDetailRequest = OrderDetailRequest.builder()
                .productId("1")
                .orderId("1")
                .build();

        // When
        when(idService.generateCategoryId()).thenReturn("1");
        when(modelMapperService.forRequest()).thenReturn(modelMapper);
        when(modelMapperService.forResponse()).thenReturn(modelMapper);
        when(modelMapper.map(orderDetailRequest, OrderDetail.class)).thenReturn(orderDetail);
        when(orderRepository.findById("1")).thenReturn(Optional.of(Order.builder().id("1").build()));
        when(productRepository.findById("1")).thenReturn(Optional.of(Product.builder().id("1").build()));
        when(orderDetailRepository.save(orderDetail)).thenReturn(orderDetail);
        when(modelMapper.map(orderDetail, OrderDetailCreatedResponse.class)).thenReturn(OrderDetailCreatedResponse.builder().id("1").build());

        OrderDetailCreatedResponse actualResponse = orderDetailManager.addOrderDetail(orderDetailRequest);

        // Then
        verify(orderDetailBusinessRules, times(1)).checkIfOrderByIdNotFound(anyString());
        verify(orderDetailBusinessRules, times(1)).checkIfProductByIdNotFound(anyString());
        verify(orderDetailRepository, times(1)).save(orderDetail);
        assertEquals(expectedResponse.getId(), actualResponse.getId());
    }
}