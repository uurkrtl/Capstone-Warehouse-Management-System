package net.ugurkartal.backend.services.concretes;

import net.ugurkartal.backend.core.mappers.ModelMapperService;
import net.ugurkartal.backend.models.Order;
import net.ugurkartal.backend.models.enums.OrderStatus;
import net.ugurkartal.backend.repositories.OrderRepository;
import net.ugurkartal.backend.services.abstracts.IdService;
import net.ugurkartal.backend.services.dtos.requests.OrderRequest;
import net.ugurkartal.backend.services.dtos.responses.OrderCreatedResponse;
import net.ugurkartal.backend.services.dtos.responses.OrderGetAllResponse;
import net.ugurkartal.backend.services.rules.OrderBusinessRules;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.List;
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
    private OrderBusinessRules orderBusinessRules;

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
    void getAllOrders_shouldReturnsListOfOrders() {
        // Given
        List<Order> orders = List.of(
                Order.builder().id("1").build(),
                Order.builder().id("2").build()
        );

        // When
        when(modelMapperService.forResponse()).thenReturn(modelMapper);
        when(orderRepository.findAll()).thenReturn(orders);

        List<OrderGetAllResponse> response = orderManager.getAllOrders();

        // Then
        assertEquals(2, response.size());
    }

    @Test
    void getOrderById_whenOrderExists_shouldReturnOrder() {
        // Given
        Order order = Order.builder().id("1").build();
        OrderCreatedResponse expectedResponse = OrderCreatedResponse.builder().id("1").build();

        // When
        when(modelMapperService.forResponse()).thenReturn(modelMapper);
        when(modelMapper.map(order, OrderCreatedResponse.class)).thenReturn(expectedResponse);
        when(orderRepository.findById("1")).thenReturn(Optional.of(order));

        OrderCreatedResponse actual = orderManager.getOrderById("1");

        // Then
        assertEquals(expectedResponse.getId(), actual.getId());
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

    @Test
    void changeOrderStatus_whenOrderExists_shouldChangeStatusCorrectly() {
        // Given
        String id = "1";

        Order updatedOrder = Order.builder()
                .id(id)
                .orderStatus(OrderStatus.PENDING)
                .build();

        OrderCreatedResponse expectedResponse = OrderCreatedResponse.builder()
                .id(id)
                .orderStatus(OrderStatus.PENDING)
                .build();

        // When
        when(modelMapperService.forResponse()).thenReturn(modelMapper);
        when(orderRepository.findById(id)).thenReturn(Optional.of(Order.builder().id(id).build()));
        orderBusinessRules.checkIfOrderByIdNotFound(id);
        when(orderRepository.save(any(Order.class))).thenReturn(updatedOrder);
        when(modelMapper.map(updatedOrder, OrderCreatedResponse.class)).thenReturn(expectedResponse);

        OrderCreatedResponse actualResponse = orderManager.changeOrderStatus(id, OrderStatus.PENDING.name());

        // Then
        verify(orderBusinessRules, times(1)).checkIfOrderByIdNotFound(id);
        verify(orderBusinessRules, times(1)).checkIfStatusNameExists(OrderStatus.PENDING.name());
        verify(orderRepository, times(1)).save(any(Order.class));
        assertEquals(expectedResponse.getOrderStatus(), actualResponse.getOrderStatus());
    }
}