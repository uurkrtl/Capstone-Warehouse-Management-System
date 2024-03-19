package net.ugurkartal.backend.repositories;

import net.ugurkartal.backend.models.Category;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CategoryRepository extends MongoRepository<Category, String>{
    boolean existsByName(String name);
}