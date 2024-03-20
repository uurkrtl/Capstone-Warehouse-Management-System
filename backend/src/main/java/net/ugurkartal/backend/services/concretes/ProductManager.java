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
import net.ugurkartal.backend.services.dtos.requests.ProductUpdateRequest;
import net.ugurkartal.backend.services.dtos.responses.ProductCreatedResponse;
import net.ugurkartal.backend.services.dtos.responses.ProductGetAllResponse;
import net.ugurkartal.backend.services.messages.ProductMessage;
import net.ugurkartal.backend.services.rules.CategoryBusinessRules;
import net.ugurkartal.backend.services.rules.ProductBusinessRules;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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
    public List<ProductGetAllResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(product -> modelMapperService.forResponse()
                        .map(product, ProductGetAllResponse.class)).toList();
    }

    @Override
    public ProductCreatedResponse getProductById(String id) {
        productBusinessRules.checkIfProductByIdNotFound(id);
        Optional<Product> productOptional = this.productRepository.findById(id);
        if(productOptional.isPresent()) {
            Product product = productOptional.get();
            return modelMapperService.forResponse().map(product, ProductCreatedResponse.class);
        } else {
            throw new NoSuchElementException(ProductMessage.PRODUCT_NOT_FOUND);
        }
    }

    @Override
    public ProductCreatedResponse addProduct(ProductCreateRequest productCreateRequest) {
        productBusinessRules.checkIfProductNameExists(productCreateRequest.getName());
        categoryBusinessRules.checkIfCategoryByIdNotFound(productCreateRequest.getCategoryId());
        Product product = modelMapperService.forRequest().map(productCreateRequest, Product.class);
        Category selectedCategory = categoryRepository.findById(productCreateRequest.getCategoryId()).orElseThrow();

        if (product.getImageUrl().isEmpty()) {
            product.setImageUrl("https://img.freepik.com/vektoren-premium/foto-kommt-bald-bilderrahmen_268834-398.jpg");
        }

        product.setId(idService.generateProductId());
        product.setActive(true);
        product.setCreatedAt(LocalDateTime.now());
        product.setCategory(selectedCategory);
        product = productRepository.save(product);

        return modelMapperService.forResponse().map(product, ProductCreatedResponse.class);
    }

    @Override
    public ProductCreatedResponse updateProduct(ProductUpdateRequest productUpdateRequest) {
        productBusinessRules.checkIfProductByIdNotFound(productUpdateRequest.getId());
        productBusinessRules.checkIfProductNameExists(productUpdateRequest.getName(), productUpdateRequest.getId());
        categoryBusinessRules.checkIfCategoryByIdNotFound(productUpdateRequest.getCategoryId());
        Product updatedProduct = modelMapperService.forRequest().map(productUpdateRequest, Product.class);
        Category selectedCategory = categoryRepository.findById(productUpdateRequest.getCategoryId()).orElseThrow();
        Optional<Product> foundProductOptional = this.productRepository.findById(productUpdateRequest.getId());
        if(foundProductOptional.isPresent()) {
            Product foundProduct = foundProductOptional.get();
            updatedProduct.setCreatedAt(foundProduct.getCreatedAt());
            updatedProduct.setActive(foundProduct.isActive());
        } else {
            throw new NoSuchElementException(ProductMessage.PRODUCT_NOT_FOUND);
        }
        updatedProduct.setUpdatedAt(LocalDateTime.now());
        updatedProduct.setCategory(selectedCategory);
        updatedProduct = productRepository.save(updatedProduct);
        return modelMapperService.forResponse().map(updatedProduct, ProductCreatedResponse.class);
    }
}