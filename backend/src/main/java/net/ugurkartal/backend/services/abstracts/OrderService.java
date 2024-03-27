package net.ugurkartal.backend.services.abstracts;

import net.ugurkartal.backend.services.dtos.requests.OrderRequest;
import net.ugurkartal.backend.services.dtos.responses.OrderCreatedResponse;

public interface OrderService {
    OrderCreatedResponse addOrder(OrderRequest orderRequest);
}