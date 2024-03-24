package net.ugurkartal.backend.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.ugurkartal.backend.services.abstracts.SupplierService;
import net.ugurkartal.backend.services.dtos.requests.SupplierRequest;
import net.ugurkartal.backend.services.dtos.responses.SupplierCreatedResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/suppliers")
@RequiredArgsConstructor
public class SupplierController {
    private final SupplierService supplierService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SupplierCreatedResponse addSupplier(@Valid @RequestBody SupplierRequest supplierRequest) {
        return supplierService.addSupplier(supplierRequest);
    }
}