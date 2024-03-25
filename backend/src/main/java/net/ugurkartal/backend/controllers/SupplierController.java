package net.ugurkartal.backend.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.ugurkartal.backend.services.abstracts.SupplierService;
import net.ugurkartal.backend.services.dtos.requests.SupplierRequest;
import net.ugurkartal.backend.services.dtos.responses.SupplierCreatedResponse;
import net.ugurkartal.backend.services.dtos.responses.SupplierGetAllResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/suppliers")
@RequiredArgsConstructor
public class SupplierController {
    private final SupplierService supplierService;

    @GetMapping
    public List<SupplierGetAllResponse> getAllSuppliers() {
        return supplierService.getAllSuppliers();
    }

    @GetMapping("/{id}")
    public SupplierCreatedResponse getSupplierById(@PathVariable String id) {
        return supplierService.getSupplierById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SupplierCreatedResponse addSupplier(@Valid @RequestBody SupplierRequest supplierRequest) {
        return supplierService.addSupplier(supplierRequest);
    }

    @PutMapping("/{id}")
    public SupplierCreatedResponse updateSupplier(@PathVariable String id, @Valid @RequestBody SupplierRequest supplierRequest) {
        return supplierService.updateSupplier(id, supplierRequest);
    }

    @PutMapping("/status/{id}")
    public SupplierCreatedResponse changeSupplierStatus(@PathVariable String id, @RequestParam boolean status) {
        return supplierService.changeSupplierStatus(id, status);
    }
}