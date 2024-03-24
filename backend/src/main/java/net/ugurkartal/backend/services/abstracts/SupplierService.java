package net.ugurkartal.backend.services.abstracts;

import net.ugurkartal.backend.services.dtos.requests.SupplierRequest;
import net.ugurkartal.backend.services.dtos.responses.SupplierCreatedResponse;
import net.ugurkartal.backend.services.dtos.responses.SupplierGetAllResponse;

import java.util.List;

public interface SupplierService {
    List<SupplierGetAllResponse> getAllSuppliers();
    SupplierCreatedResponse addSupplier(SupplierRequest supplierRequest);
}