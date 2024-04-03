package net.ugurkartal.backend.services.concretes;

import lombok.RequiredArgsConstructor;
import net.ugurkartal.backend.core.mappers.ModelMapperService;
import net.ugurkartal.backend.models.Product;
import net.ugurkartal.backend.repositories.ProductRepository;
import net.ugurkartal.backend.services.abstracts.ReportService;
import net.ugurkartal.backend.services.dtos.responses.ProductGetAllResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportManager implements ReportService {
    private final ProductRepository productRepository;
    private final ModelMapperService modelMapperService;

    @Override
    public List<ProductGetAllResponse> getProductsOutOfStock() {
        List<Product> products = productRepository.findByIsActiveAndStock(true, 0);
        return products.stream()
                .map(product -> modelMapperService.forResponse()
                        .map(product, ProductGetAllResponse.class)).toList();
    }
}