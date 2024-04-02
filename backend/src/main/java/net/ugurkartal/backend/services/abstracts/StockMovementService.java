package net.ugurkartal.backend.services.abstracts;

import net.ugurkartal.backend.services.dtos.requests.StockMovementRequest;
import net.ugurkartal.backend.services.dtos.responses.StockMovementCreatedResponse;

public interface StockMovementService {
    StockMovementCreatedResponse addStockMovement(StockMovementRequest stockMovementRequest);
}