package net.ugurkartal.backend.services.abstracts;

import net.ugurkartal.backend.services.dtos.requests.OrderRequest;
import net.ugurkartal.backend.services.dtos.responses.OrderCreatedResponse;
import net.ugurkartal.backend.services.dtos.responses.OrderGetAllResponse;

import java.util.List;

public interface OrderService {
    List<OrderGetAllResponse> getAllOrders();
    OrderCreatedResponse addOrder(OrderRequest orderRequest);
    OrderCreatedResponse changeOrderStatus(String orderId, String status);
}