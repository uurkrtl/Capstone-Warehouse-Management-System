package net.ugurkartal.backend.controllers;

import lombok.RequiredArgsConstructor;
import net.ugurkartal.backend.services.abstracts.StockMovementService;
import net.ugurkartal.backend.services.dtos.requests.StockMovementRequest;
import net.ugurkartal.backend.services.dtos.responses.StockMovementCreatedResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stock-movements")
public class StockMovementController {
    private final StockMovementService stockMovementService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StockMovementCreatedResponse addStockMovement(StockMovementRequest stockMovementRequest) {
        return stockMovementService.addStockMovement(stockMovementRequest);
    }
}