package net.ugurkartal.backend.repositories;

import net.ugurkartal.backend.models.OrderDetail;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderDetailRepository extends MongoRepository<OrderDetail, String>{
}