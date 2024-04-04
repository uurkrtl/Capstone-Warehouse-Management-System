package net.ugurkartal.backend.repositories;

import net.ugurkartal.backend.models.Purchase;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PurchaseRepository extends MongoRepository<Purchase, String> {
    List<Purchase> findAllByProductId(String productId);
}