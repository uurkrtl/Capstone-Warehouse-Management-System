package net.ugurkartal.backend.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.ugurkartal.backend.services.abstracts.OrderService;
import net.ugurkartal.backend.services.dtos.requests.OrderRequest;
import net.ugurkartal.backend.services.dtos.responses.OrderCreatedResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderCreatedResponse addOrder(@Valid @RequestBody OrderRequest orderRequest) {
        return orderService.addOrder(orderRequest);
    }
}