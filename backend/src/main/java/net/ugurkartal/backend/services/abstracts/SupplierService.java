package net.ugurkartal.backend.services.abstracts;

import net.ugurkartal.backend.services.dtos.requests.SupplierRequest;
import net.ugurkartal.backend.services.dtos.responses.SupplierCreatedResponse;

public interface SupplierService {
    SupplierCreatedResponse addSupplier(SupplierRequest supplierRequest);
}