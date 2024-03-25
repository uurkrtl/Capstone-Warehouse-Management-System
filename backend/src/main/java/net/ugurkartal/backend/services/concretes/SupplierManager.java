package net.ugurkartal.backend.services.concretes;

import lombok.RequiredArgsConstructor;
import net.ugurkartal.backend.core.mappers.ModelMapperService;
import net.ugurkartal.backend.models.Supplier;
import net.ugurkartal.backend.repositories.SupplierRepository;
import net.ugurkartal.backend.services.abstracts.IdService;
import net.ugurkartal.backend.services.abstracts.SupplierService;
import net.ugurkartal.backend.services.dtos.requests.SupplierRequest;
import net.ugurkartal.backend.services.dtos.responses.SupplierCreatedResponse;
import net.ugurkartal.backend.services.dtos.responses.SupplierGetAllResponse;
import net.ugurkartal.backend.services.rules.SupplierBusinessRules;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SupplierManager implements SupplierService {
    private final SupplierRepository supplierRepository;
    private final ModelMapperService modelMapperService;
    private final IdService idService;
    private final SupplierBusinessRules supplierBusinessRules;

    @Override
    public List<SupplierGetAllResponse> getAllSuppliers() {
        List<Supplier> suppliers = supplierRepository.findAll();
        return suppliers.stream()
                .map(supplier->this.modelMapperService.forResponse()
                        .map(supplier, SupplierGetAllResponse.class)).toList();
    }

    @Override
    public SupplierCreatedResponse getSupplierById(String id) {
        supplierBusinessRules.checkIfSupplierByIdNotFound(id);
        Supplier supplier = supplierRepository.findById(id).orElseThrow();
        return modelMapperService.forResponse().map(supplier, SupplierCreatedResponse.class);
    }

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

    @Override
    public SupplierCreatedResponse updateSupplier(String id, SupplierRequest supplierRequest) {
        supplierBusinessRules.checkIfSupplierByIdNotFound(id);
        supplierBusinessRules.checkIfSupplierNameExists(supplierRequest.getName(), id);
        Supplier updatedSupplier = modelMapperService.forRequest().map(supplierRequest, Supplier.class);
        Supplier foundSupplier = supplierRepository.findById(id).orElseThrow();
        updatedSupplier.setId(foundSupplier.getId());
        updatedSupplier.setCreatedAt(foundSupplier.getCreatedAt());
        updatedSupplier.setUpdatedAt(LocalDateTime.now());
        updatedSupplier.setActive(foundSupplier.isActive());
        updatedSupplier = supplierRepository.save(updatedSupplier);
        return modelMapperService.forResponse().map(updatedSupplier, SupplierCreatedResponse.class);
    }
}