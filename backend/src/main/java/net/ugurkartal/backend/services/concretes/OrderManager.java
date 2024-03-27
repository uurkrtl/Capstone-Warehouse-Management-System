package net.ugurkartal.backend.services.concretes;

import lombok.RequiredArgsConstructor;
import net.ugurkartal.backend.core.mappers.ModelMapperService;
import net.ugurkartal.backend.models.Order;
import net.ugurkartal.backend.models.enums.OrderStatus;
import net.ugurkartal.backend.repositories.OrderRepository;
import net.ugurkartal.backend.services.abstracts.IdService;
import net.ugurkartal.backend.services.abstracts.OrderService;
import net.ugurkartal.backend.services.dtos.requests.OrderRequest;
import net.ugurkartal.backend.services.dtos.responses.OrderCreatedResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OrderManager implements OrderService {
    private final OrderRepository orderRepository;
    private final ModelMapperService modelMapperService;
    private final IdService idService;

    @Override
    public OrderCreatedResponse addOrder(OrderRequest orderRequest) {
        Order order = modelMapperService.forRequest().map(orderRequest, Order.class);
        order.setId(idService.generateOrderId());
        order.setOrderDate(LocalDateTime.now());
        order.setOrderStatus(OrderStatus.PENDING);
        orderRepository.save(order);
        return modelMapperService.forResponse().map(order, OrderCreatedResponse.class);
    }
}