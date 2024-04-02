package net.ugurkartal.backend.repositories;

import net.ugurkartal.backend.models.StockMovement;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StockMovementRepository extends MongoRepository<StockMovement, String>{
}