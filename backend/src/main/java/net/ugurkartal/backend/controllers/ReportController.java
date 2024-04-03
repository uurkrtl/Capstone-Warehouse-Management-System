package net.ugurkartal.backend.controllers;

import lombok.RequiredArgsConstructor;
import net.ugurkartal.backend.services.abstracts.ReportService;
import net.ugurkartal.backend.services.dtos.responses.ProductGetAllResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;

    @GetMapping("/products-out-of-stock")
    public List<ProductGetAllResponse> getProductsOutOfStock() {
        return reportService.getProductsOutOfStock();
    }
}