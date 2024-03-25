package net.ugurkartal.backend.services.rules;

import lombok.RequiredArgsConstructor;
import net.ugurkartal.backend.core.exceptions.types.DuplicateRecordException;
import net.ugurkartal.backend.core.exceptions.types.RecordNotFoundException;
import net.ugurkartal.backend.models.Supplier;
import net.ugurkartal.backend.repositories.SupplierRepository;
import net.ugurkartal.backend.services.messages.SupplierMessage;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SupplierBusinessRules {
    private final SupplierRepository supplierRepository;

    public void checkIfSupplierNameExists(String supplierName) {
        if(this.supplierRepository.existsByName(supplierName)) {
            throw new DuplicateRecordException(SupplierMessage.SUPPLIER_NAME_ALREADY_EXISTS);
        }
    }

    public void checkIfSupplierNameExists(String supplierName, String id) {
        Supplier supplier = this.supplierRepository.findById(id).orElseThrow();
        if(!supplier.getName().equals(supplierName) && this.supplierRepository.existsByName(supplierName)) {
            throw new DuplicateRecordException(SupplierMessage.SUPPLIER_NAME_ALREADY_EXISTS);
        }
    }

    public void checkIfSupplierByIdNotFound(String id) {
        if(!this.supplierRepository.existsById(id)) {
            throw new RecordNotFoundException(SupplierMessage.SUPPLIER_NOT_FOUND);
        }
    }
}