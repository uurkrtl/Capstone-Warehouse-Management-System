package net.ugurkartal.backend.services.rules;

import lombok.RequiredArgsConstructor;
import net.ugurkartal.backend.core.exceptions.types.RecordNotFoundException;
import net.ugurkartal.backend.models.enums.OrderStatus;
import net.ugurkartal.backend.repositories.OrderRepository;
import net.ugurkartal.backend.services.messages.OrderMessage;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderBusinessRules {
    private final OrderRepository orderRepository;
    public void checkIfStatusNameExists(String statusName) {
        try {
            OrderStatus.valueOf(statusName);
        } catch (IllegalArgumentException e) {
            throw new RecordNotFoundException(OrderMessage.ORDER_STATUS_NOT_FOUND);
        }
    }

    public void checkIfOrderByIdNotFound(String id) {
        if(!this.orderRepository.existsById(id)) {
            throw new RecordNotFoundException(OrderMessage.ORDER_NOT_FOUND);
        }
    }
}