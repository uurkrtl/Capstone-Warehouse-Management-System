package net.ugurkartal.backend.services.rules;

import lombok.RequiredArgsConstructor;
import net.ugurkartal.backend.core.exceptions.types.RecordNotFoundException;
import net.ugurkartal.backend.repositories.OrderRepository;
import net.ugurkartal.backend.repositories.ProductRepository;
import net.ugurkartal.backend.services.messages.OrderDetailMessage;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderDetailBusinessRules {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public void checkIfOrderByIdNotFound(String id) {
        if(!this.orderRepository.existsById(id)) {
            throw new RecordNotFoundException(OrderDetailMessage.ORDER_NOT_FOUND);
        }
    }

    public void checkIfProductByIdNotFound(String id) {
        if(!this.productRepository.existsById(id)) {
            throw new RecordNotFoundException(OrderDetailMessage.PRODUCT_NOT_FOUND);
        }
    }
}