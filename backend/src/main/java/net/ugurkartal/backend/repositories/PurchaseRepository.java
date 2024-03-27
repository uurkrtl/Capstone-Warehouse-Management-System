package net.ugurkartal.backend.repositories;

import net.ugurkartal.backend.models.Purchase;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PurchaseRepository extends MongoRepository<Purchase, String> {
}