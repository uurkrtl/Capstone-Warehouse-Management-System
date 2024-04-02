package net.ugurkartal.backend.controllers;

import lombok.RequiredArgsConstructor;
import net.ugurkartal.backend.services.abstracts.StockMovementService;
import net.ugurkartal.backend.services.dtos.requests.StockMovementRequest;
import net.ugurkartal.backend.services.dtos.responses.StockMovementCreatedResponse;
import net.ugurkartal.backend.services.dtos.responses.StockMovementGetAllResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stock-movements")
public class StockMovementController {
    private final StockMovementService stockMovementService;

    @GetMapping
    public List<StockMovementGetAllResponse> getAllStockMovements() {
        return stockMovementService.getAllStockMovements();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StockMovementCreatedResponse addStockMovement(@RequestBody StockMovementRequest stockMovementRequest) {
        return stockMovementService.addStockMovement(stockMovementRequest);
    }
}