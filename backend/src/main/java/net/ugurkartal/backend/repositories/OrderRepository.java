package net.ugurkartal.backend.repositories;

import net.ugurkartal.backend.models.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<Order, String>{
}