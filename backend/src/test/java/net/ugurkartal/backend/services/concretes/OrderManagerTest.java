package net.ugurkartal.backend.services.concretes;

import net.ugurkartal.backend.core.mappers.ModelMapperService;
import net.ugurkartal.backend.models.Order;
import net.ugurkartal.backend.repositories.OrderRepository;
import net.ugurkartal.backend.services.abstracts.IdService;
import net.ugurkartal.backend.services.dtos.requests.OrderRequest;
import net.ugurkartal.backend.services.dtos.responses.OrderCreatedResponse;
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

class OrderManagerTest {
    @InjectMocks
    private OrderManager orderManager;
    private ModelMapper modelMapper;

    @Mock
    private OrderRepository orderRepository;

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
    void addOrder_whenOrderRequestIsValid_shouldReturnOrderCreatedResponse() {
        // Given
        Order order = Order.builder().id("1").build();
        OrderCreatedResponse expectedResponse = OrderCreatedResponse.builder().id("1").build();
        OrderRequest orderRequest = OrderRequest.builder().customerName("Test").build();

        // When
        when(idService.generateOrderId()).thenReturn("1");
        when(modelMapperService.forRequest()).thenReturn(modelMapper);
        when(modelMapperService.forResponse()).thenReturn(modelMapper);
        when(modelMapper.map(orderRequest, Order.class)).thenReturn(order);
        when(orderRepository.findById("1")).thenReturn(Optional.of(Order.builder().id("1").build()));
        when(orderRepository.save(order)).thenReturn(order);
        when(modelMapper.map(order, OrderCreatedResponse.class)).thenReturn(expectedResponse);

        OrderCreatedResponse actualResponse = orderManager.addOrder(orderRequest);

        // Then
        verify(orderRepository, times(1)).save(order);
        assertEquals(expectedResponse.getId(), actualResponse.getId());
    }
}