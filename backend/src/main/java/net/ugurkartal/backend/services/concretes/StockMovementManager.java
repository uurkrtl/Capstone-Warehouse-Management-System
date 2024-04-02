package net.ugurkartal.backend.services.concretes;

import lombok.RequiredArgsConstructor;
import net.ugurkartal.backend.core.mappers.ModelMapperService;
import net.ugurkartal.backend.models.Product;
import net.ugurkartal.backend.models.StockMovement;
import net.ugurkartal.backend.repositories.ProductRepository;
import net.ugurkartal.backend.repositories.StockMovementRepository;
import net.ugurkartal.backend.services.abstracts.IdService;
import net.ugurkartal.backend.services.abstracts.ProductService;
import net.ugurkartal.backend.services.abstracts.StockMovementService;
import net.ugurkartal.backend.services.dtos.requests.StockMovementRequest;
import net.ugurkartal.backend.services.dtos.responses.StockMovementCreatedResponse;
import net.ugurkartal.backend.services.dtos.responses.StockMovementGetAllResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StockMovementManager implements StockMovementService {
    private final StockMovementRepository stockMovementRepository;
    private final ProductRepository productRepository;
    private final ProductService productService;
    private final ModelMapperService modelMapperService;
    private final IdService idService;

    @Override
    public List<StockMovementGetAllResponse> getAllStockMovements() {
        List<StockMovement> stockMovements = stockMovementRepository.findAll();
        return stockMovements.stream()
                .map(stockMovement -> this.modelMapperService.forResponse()
                        .map(stockMovement, StockMovementGetAllResponse.class)).toList();
    }

    @Override
    public StockMovementCreatedResponse addStockMovement(StockMovementRequest stockMovementRequest) {
        if (!stockMovementRequest.isType()) {
            stockMovementRequest.setQuantity(stockMovementRequest.getQuantity() * -1);
        }

        productService.updateStock(stockMovementRequest.getProductId(), stockMovementRequest.getQuantity()); // Update stock in product class

        StockMovement stockMovement = modelMapperService.forRequest().map(stockMovementRequest, StockMovement.class);
        Product selectedProduct = productRepository.findById(stockMovementRequest.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));
        stockMovement.setId(idService.generateStockMovementId());
        stockMovement.setCreatedAt(LocalDateTime.now());
        stockMovement.setProduct(selectedProduct);
        stockMovement = stockMovementRepository.save(stockMovement);
        return modelMapperService.forResponse().map(stockMovement, StockMovementCreatedResponse.class);
    }
}