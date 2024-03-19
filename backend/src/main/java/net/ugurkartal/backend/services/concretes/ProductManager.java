package net.ugurkartal.backend.services.concretes;

import lombok.RequiredArgsConstructor;
import net.ugurkartal.backend.core.mappers.ModelMapperService;
import net.ugurkartal.backend.models.Category;
import net.ugurkartal.backend.models.Product;
import net.ugurkartal.backend.repositories.CategoryRepository;
import net.ugurkartal.backend.repositories.ProductRepository;
import net.ugurkartal.backend.services.abstracts.IdService;
import net.ugurkartal.backend.services.abstracts.ProductService;
import net.ugurkartal.backend.services.dtos.requests.ProductCreateRequest;
import net.ugurkartal.backend.services.dtos.responses.ProductCreatedResponse;
import net.ugurkartal.backend.services.rules.CategoryBusinessRules;
import net.ugurkartal.backend.services.rules.ProductBusinessRules;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ProductManager implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapperService modelMapperService;
    private final IdService idService;
    private final ProductBusinessRules productBusinessRules;
    private final CategoryBusinessRules categoryBusinessRules;

    @Override
    public ProductCreatedResponse addProduct(ProductCreateRequest productCreateRequest) {
        productBusinessRules.checkIfProductNameExists(productCreateRequest.getName());
        categoryBusinessRules.checkIfCategoryByIdNotFound(productCreateRequest.getCategoryId());
        Product product = modelMapperService.forRequest().map(productCreateRequest, Product.class);
        Category selectedCategory = categoryRepository.findById(productCreateRequest.getCategoryId()).orElseThrow();

        product.setId(idService.generateProductId());
        product.setActive(true);
        product.setCreatedAt(LocalDateTime.now());
        product.setCategory(selectedCategory);
        product = productRepository.save(product);

        return modelMapperService.forResponse().map(product, ProductCreatedResponse.class);
    }
}