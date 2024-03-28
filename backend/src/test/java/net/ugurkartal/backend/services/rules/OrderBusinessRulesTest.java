package net.ugurkartal.backend.services.rules;

import net.ugurkartal.backend.core.exceptions.types.RecordNotFoundException;
import net.ugurkartal.backend.models.enums.OrderStatus;
import net.ugurkartal.backend.repositories.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class OrderBusinessRulesTest {
    @Mock
    private OrderRepository orderRepository;
    @InjectMocks
    private OrderBusinessRules orderBusinessRules;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void checkIfStatusNameExists_shouldPassWhenStatusNameExists() {
        String existingStatusName = OrderStatus.PENDING.name();
        orderBusinessRules.checkIfStatusNameExists(existingStatusName);
    }

    @Test
    void checkIfStatusNameExists_shouldThrowExceptionWhenStatusNameDoesNotExist() {
        String nonExistingStatusName = "NON_EXISTING_STATUS";
        assertThrows(RecordNotFoundException.class, () -> orderBusinessRules.checkIfStatusNameExists(nonExistingStatusName));
    }

    @Test
    void checkIfOrderByIdNotFound_whenOrderIdNotFound_throwsRecordNotFoundException() {
        // Given
        String nonExistingOrderId = "1";

        // When
        when(orderRepository.existsById(nonExistingOrderId)).thenReturn(false);

        // Then
        assertThrows(RecordNotFoundException.class, () -> orderBusinessRules.checkIfOrderByIdNotFound(nonExistingOrderId));
    }

    @Test
    void checkIfOrderByIdNotFound_whenOrderIdExists_doesNotThrowException() {
        // Given
        String existingOrderId = "1";

        // When
        when(orderRepository.existsById(existingOrderId)).thenReturn(true);

        // Then
        assertDoesNotThrow(() -> orderBusinessRules.checkIfOrderByIdNotFound(existingOrderId));
    }
}