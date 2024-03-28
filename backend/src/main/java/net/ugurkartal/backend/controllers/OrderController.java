package net.ugurkartal.backend.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.ugurkartal.backend.services.abstracts.OrderService;
import net.ugurkartal.backend.services.dtos.requests.OrderRequest;
import net.ugurkartal.backend.services.dtos.responses.OrderCreatedResponse;
import net.ugurkartal.backend.services.dtos.responses.OrderGetAllResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping
    public List<OrderGetAllResponse> getAllOrders() {
        return orderService.getAllOrders();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderCreatedResponse addOrder(@Valid @RequestBody OrderRequest orderRequest) {
        return orderService.addOrder(orderRequest);
    }

    @PutMapping("/status/{id}")
    public OrderCreatedResponse changeOrderStatus(@PathVariable String id, @RequestParam String status) {
        return orderService.changeOrderStatus(id, status);
    }
}