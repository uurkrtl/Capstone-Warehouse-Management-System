package net.ugurkartal.backend.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.ugurkartal.backend.services.abstracts.PurchaseService;
import net.ugurkartal.backend.services.dtos.requests.PurchaseRequest;
import net.ugurkartal.backend.services.dtos.responses.PurchaseCreatedResponse;
import net.ugurkartal.backend.services.dtos.responses.PurchaseGetAllResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/purchases")
@RequiredArgsConstructor
public class PurchaseController {
    private final PurchaseService purchaseService;

    @GetMapping
    public List<PurchaseGetAllResponse> getAllPurchases() {
        return purchaseService.getAllPurchases();
    }

    @GetMapping("/{id}")
    public PurchaseCreatedResponse getPurchaseById(@PathVariable String id) {
        return purchaseService.getPurchaseById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PurchaseCreatedResponse addPurchase(@Valid @RequestBody PurchaseRequest purchaseRequest) {
        return purchaseService.addPurchase(purchaseRequest);
    }

    @PutMapping("/{id}")
    public PurchaseCreatedResponse updatePurchase(@PathVariable String id, @Valid @RequestBody PurchaseRequest purchaseRequest) {
        return purchaseService.updatePurchase(id, purchaseRequest);
    }
}