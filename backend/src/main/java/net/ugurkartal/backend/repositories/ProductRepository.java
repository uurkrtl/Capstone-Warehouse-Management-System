package net.ugurkartal.backend.repositories;

import net.ugurkartal.backend.models.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {
    boolean existsByName(String productName);
}