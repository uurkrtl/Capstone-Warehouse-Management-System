package net.ugurkartal.backend.services.abstracts;

import net.ugurkartal.backend.services.dtos.requests.OrderDetailRequest;
import net.ugurkartal.backend.services.dtos.responses.OrderDetailCreatedResponse;
import net.ugurkartal.backend.services.dtos.responses.OrderDetailGetAllResponse;

import java.util.List;

public interface OrderDetailService {
    List<OrderDetailGetAllResponse> getOrdersByOrderId(String orderId);
    OrderDetailCreatedResponse addOrderDetail(OrderDetailRequest orderDetailRequest);
}