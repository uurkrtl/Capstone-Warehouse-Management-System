package net.ugurkartal.backend.services.abstracts;

import net.ugurkartal.backend.services.dtos.requests.StockMovementRequest;
import net.ugurkartal.backend.services.dtos.responses.StockMovementCreatedResponse;
import net.ugurkartal.backend.services.dtos.responses.StockMovementGetAllResponse;

import java.util.List;

public interface StockMovementService {
    List<StockMovementGetAllResponse> getAllStockMovements();
    StockMovementCreatedResponse addStockMovement(StockMovementRequest stockMovementRequest);
}