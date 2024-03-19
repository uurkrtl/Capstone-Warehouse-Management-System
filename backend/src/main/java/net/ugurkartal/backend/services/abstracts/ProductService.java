package net.ugurkartal.backend.services.abstracts;

import net.ugurkartal.backend.services.dtos.requests.ProductCreateRequest;
import net.ugurkartal.backend.services.dtos.responses.ProductCreatedResponse;

public interface ProductService {
    ProductCreatedResponse addProduct(ProductCreateRequest productCreateRequest);
}