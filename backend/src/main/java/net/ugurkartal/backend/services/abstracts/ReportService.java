package net.ugurkartal.backend.services.abstracts;

import net.ugurkartal.backend.services.dtos.responses.ProductGetAllResponse;

import java.util.List;

public interface ReportService {
    List<ProductGetAllResponse> getProductsOutOfStock();
    List<ProductGetAllResponse> getProductsLowInStock();
}