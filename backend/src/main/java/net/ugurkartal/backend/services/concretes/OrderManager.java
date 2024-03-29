package net.ugurkartal.backend.services.concretes;

import lombok.RequiredArgsConstructor;
import net.ugurkartal.backend.core.exceptions.types.RecordNotFoundException;
import net.ugurkartal.backend.core.mappers.ModelMapperService;
import net.ugurkartal.backend.models.Order;
import net.ugurkartal.backend.models.enums.OrderStatus;
import net.ugurkartal.backend.repositories.OrderRepository;
import net.ugurkartal.backend.services.abstracts.IdService;
import net.ugurkartal.backend.services.abstracts.OrderService;
import net.ugurkartal.backend.services.dtos.requests.OrderRequest;
import net.ugurkartal.backend.services.dtos.responses.OrderCreatedResponse;
import net.ugurkartal.backend.services.dtos.responses.OrderGetAllResponse;
import net.ugurkartal.backend.services.messages.OrderMessage;
import net.ugurkartal.backend.services.rules.OrderBusinessRules;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderManager implements OrderService {
    private final OrderRepository orderRepository;
    private final ModelMapperService modelMapperService;
    private final IdService idService;
    private final OrderBusinessRules orderBusinessRules;

    @Override
    public List<OrderGetAllResponse> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(order->this.modelMapperService.forResponse()
                        .map(order, OrderGetAllResponse.class)).toList();
    }

    @Override
    public OrderCreatedResponse addOrder(OrderRequest orderRequest) {
        Order order = modelMapperService.forRequest().map(orderRequest, Order.class);
        order.setId(idService.generateOrderId());
        order.setOrderDate(LocalDateTime.now());
        order.setOrderStatus(OrderStatus.PENDING);
        order = orderRepository.save(order);
        return modelMapperService.forResponse().map(order, OrderCreatedResponse.class);
    }

    @Override
    public OrderCreatedResponse changeOrderStatus(String orderId, String status) {
        orderBusinessRules.checkIfStatusNameExists(status);
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RecordNotFoundException(OrderMessage.ORDER_STATUS_NOT_FOUND));
        order.setOrderStatus(OrderStatus.valueOf(status));
        orderRepository.save(order);
        return modelMapperService.forResponse().map(order, OrderCreatedResponse.class);
    }
}