package net.ugurkartal.backend.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.ugurkartal.backend.services.abstracts.ProductService;
import net.ugurkartal.backend.services.dtos.requests.ProductCreateRequest;
import net.ugurkartal.backend.services.dtos.responses.ProductCreatedResponse;
import net.ugurkartal.backend.services.dtos.responses.ProductGetAllResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public List<ProductGetAllResponse> getAllProducts() {
        return productService.getAllProducts();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductCreatedResponse addProduct(@Valid @RequestBody ProductCreateRequest productCreateRequest) {
        return productService.addProduct(productCreateRequest);
    }
}