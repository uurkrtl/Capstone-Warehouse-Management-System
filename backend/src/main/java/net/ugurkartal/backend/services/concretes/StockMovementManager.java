package net.ugurkartal.backend.services.concretes;

import lombok.RequiredArgsConstructor;
import net.ugurkartal.backend.core.mappers.ModelMapperService;
import net.ugurkartal.backend.models.StockMovement;
import net.ugurkartal.backend.repositories.StockMovementRepository;
import net.ugurkartal.backend.services.abstracts.IdService;
import net.ugurkartal.backend.services.abstracts.StockMovementService;
import net.ugurkartal.backend.services.dtos.requests.StockMovementRequest;
import net.ugurkartal.backend.services.dtos.responses.StockMovementCreatedResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class StockMovementManager implements StockMovementService {
    private final StockMovementRepository stockMovementRepository;
    private final ModelMapperService modelMapperService;
    private final IdService idService;

    @Override
    public StockMovementCreatedResponse addStockMovement(StockMovementRequest stockMovementRequest) {
        StockMovement stockMovement = modelMapperService.forRequest().map(stockMovementRequest, StockMovement.class);
        stockMovement.setId(idService.generateStockMovementId());
        stockMovement.setCreatedAt(LocalDateTime.now());
        stockMovement = stockMovementRepository.save(stockMovement);
        return modelMapperService.forResponse().map(stockMovement, StockMovementCreatedResponse.class);
    }
}