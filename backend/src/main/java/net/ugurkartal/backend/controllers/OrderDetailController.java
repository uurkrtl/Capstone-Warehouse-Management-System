package net.ugurkartal.backend.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.ugurkartal.backend.services.abstracts.OrderDetailService;
import net.ugurkartal.backend.services.dtos.requests.OrderDetailRequest;
import net.ugurkartal.backend.services.dtos.responses.OrderDetailCreatedResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order-details")
@RequiredArgsConstructor
public class OrderDetailController {
    private final OrderDetailService orderDetailService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDetailCreatedResponse addOrderDetail(@Valid @RequestBody OrderDetailRequest orderDetailRequest) {
        return orderDetailService.addOrderDetail(orderDetailRequest);
    }
}