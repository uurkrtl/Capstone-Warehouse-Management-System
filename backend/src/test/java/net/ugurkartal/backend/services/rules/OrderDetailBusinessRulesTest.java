package net.ugurkartal.backend.services.rules;

import net.ugurkartal.backend.core.exceptions.types.RecordNotFoundException;
import net.ugurkartal.backend.repositories.OrderRepository;
import net.ugurkartal.backend.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class OrderDetailBusinessRulesTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private OrderDetailBusinessRules orderDetailBusinessRules;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void checkIfOrderByIdNotFound_throwsRecordNotFoundException() {
        String nonExistingOrderId = "Non Existing Order Id";
        when(orderRepository.existsById(nonExistingOrderId)).thenReturn(false);

        assertThrows(RecordNotFoundException.class, () -> orderDetailBusinessRules.checkIfOrderByIdNotFound(nonExistingOrderId));
    }

    @Test
    void checkIfOrderByIdFound_doesNotThrowException() {
        String existingOrderId = "Existing Order Id";
        when(orderRepository.existsById(existingOrderId)).thenReturn(true);

        assertDoesNotThrow(() -> orderDetailBusinessRules.checkIfOrderByIdNotFound(existingOrderId));
    }

    @Test
    void checkIfProductByIdNotFound_throwsRecordNotFoundException() {
        String nonExistingProductId = "Non Existing Product Id";
        when(productRepository.existsById(nonExistingProductId)).thenReturn(false);

        assertThrows(RecordNotFoundException.class, () -> orderDetailBusinessRules.checkIfProductByIdNotFound(nonExistingProductId));
    }

    @Test
    void checkIfProductByIdFound_doesNotThrowException() {
        String existingProductId = "Existing Product Id";
        when(productRepository.existsById(existingProductId)).thenReturn(true);

        assertDoesNotThrow(() -> orderDetailBusinessRules.checkIfProductByIdNotFound(existingProductId));
    }
}