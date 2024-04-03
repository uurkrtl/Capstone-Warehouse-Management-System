package net.ugurkartal.backend.repositories;

import net.ugurkartal.backend.models.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProductRepository extends MongoRepository<Product, String> {
    boolean existsByName(String productName);

    boolean existsByCategoryIdAndIsActiveTrue(String categoryId);

    List<Product> findByIsActiveAndStock(boolean isActive, int stock);
}