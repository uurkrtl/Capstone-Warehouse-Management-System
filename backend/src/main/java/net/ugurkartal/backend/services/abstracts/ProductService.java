package net.ugurkartal.backend.services.abstracts;

import net.ugurkartal.backend.services.dtos.requests.ProductRequest;
import net.ugurkartal.backend.services.dtos.responses.ProductCreatedResponse;
import net.ugurkartal.backend.services.dtos.responses.ProductGetAllResponse;

import java.util.List;

public interface ProductService {
    List<ProductGetAllResponse> getAllProducts();
    List<ProductGetAllResponse> getProductsByCategoryId(String categoryId);
    ProductCreatedResponse getProductById(String id);
    ProductCreatedResponse addProduct(ProductRequest productRequest);
    ProductCreatedResponse updateProduct(String id, ProductRequest productRequest);
    ProductCreatedResponse changeProductStatus(String id, boolean status);
    void updateStock(String id, int quantity);
}