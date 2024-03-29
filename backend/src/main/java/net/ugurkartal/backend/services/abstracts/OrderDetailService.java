package net.ugurkartal.backend.services.abstracts;

import net.ugurkartal.backend.services.dtos.requests.OrderDetailRequest;
import net.ugurkartal.backend.services.dtos.responses.OrderDetailCreatedResponse;

public interface OrderDetailService {
    OrderDetailCreatedResponse addOrderDetail(OrderDetailRequest orderDetailRequest);
}