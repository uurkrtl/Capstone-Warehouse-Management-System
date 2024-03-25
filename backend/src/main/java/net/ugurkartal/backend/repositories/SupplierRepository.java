package net.ugurkartal.backend.repositories;

import net.ugurkartal.backend.models.Supplier;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SupplierRepository extends MongoRepository<Supplier, String> {
    boolean existsByName(String supplierName);
}