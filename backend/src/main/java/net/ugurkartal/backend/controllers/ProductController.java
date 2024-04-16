package net.ugurkartal.backend.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.ugurkartal.backend.services.abstracts.ProductService;
import net.ugurkartal.backend.services.dtos.requests.ProductRequest;
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

    @GetMapping("/by-category/{categoryId}")
    public List<ProductGetAllResponse> getProductsByCategoryId(@PathVariable String categoryId) {
        return productService.getProductsByCategoryId(categoryId);
    }

    @GetMapping("/{id}")
    public ProductCreatedResponse getProductById(@PathVariable String id) {
        return productService.getProductById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductCreatedResponse addProduct(@Valid @RequestBody ProductRequest productRequest) {
        return productService.addProduct(productRequest);
    }

    @PutMapping("/{id}")
    public ProductCreatedResponse updateProduct(@PathVariable String id, @Valid @RequestBody ProductRequest productRequest) {
        return productService.updateProduct(id, productRequest);
    }

    @PutMapping("/status/{id}")
    public ProductCreatedResponse changeProductStatus(@PathVariable String id, @RequestParam boolean status) {
        return productService.changeProductStatus(id, status);
    }
}