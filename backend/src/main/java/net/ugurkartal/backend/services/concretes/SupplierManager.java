package net.ugurkartal.backend.services.concretes;

import lombok.RequiredArgsConstructor;
import net.ugurkartal.backend.core.mappers.ModelMapperService;
import net.ugurkartal.backend.models.Supplier;
import net.ugurkartal.backend.repositories.SupplierRepository;
import net.ugurkartal.backend.services.abstracts.IdService;
import net.ugurkartal.backend.services.abstracts.SupplierService;
import net.ugurkartal.backend.services.dtos.requests.SupplierRequest;
import net.ugurkartal.backend.services.dtos.responses.SupplierCreatedResponse;
import net.ugurkartal.backend.services.rules.SupplierBusinessRules;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SupplierManager implements SupplierService {
    private final SupplierRepository supplierRepository;
    private final ModelMapperService modelMapperService;
    private final IdService idService;
    private final SupplierBusinessRules supplierBusinessRules;

    @Override
    public SupplierCreatedResponse addSupplier(SupplierRequest supplierRequest) {
        supplierBusinessRules.checkIfSupplierNameExists(supplierRequest.getName());
        Supplier supplier = modelMapperService.forRequest().map(supplierRequest, Supplier.class);
        supplier.setId(idService.generateSupplierId());
        supplier.setCreatedAt(LocalDateTime.now());
        supplier.setActive(true);
        supplier = supplierRepository.save(supplier);
        return modelMapperService.forResponse().map(supplier, SupplierCreatedResponse.class);
    }
}