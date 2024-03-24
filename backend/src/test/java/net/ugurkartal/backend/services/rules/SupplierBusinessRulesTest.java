package net.ugurkartal.backend.services.rules;

import net.ugurkartal.backend.core.exceptions.types.DuplicateRecordException;
import net.ugurkartal.backend.core.exceptions.types.RecordNotFoundException;
import net.ugurkartal.backend.repositories.SupplierRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class SupplierBusinessRulesTest {
    @Mock
    private SupplierRepository supplierRepository;

    @InjectMocks
    private SupplierBusinessRules supplierBusinessRules;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void checkIfSupplierNameExists_whenSupplierNameExists_throwsDuplicateRecordException() {
        // Given
        String existingSupplierName = "Existing Supplier";

        // When
        when(supplierRepository.existsByName(existingSupplierName)).thenReturn(true);

        // Then
        assertThrows(DuplicateRecordException.class, () -> supplierBusinessRules.checkIfSupplierNameExists(existingSupplierName));
    }

    @Test
    void checkIfSupplierNameExists_whenSupplierNameDoesNotExist_doesNotThrowException() {
        // Given
        String nonExistingSupplierName = "Non Existing Supplier";

        // When
        when(supplierRepository.existsByName(nonExistingSupplierName)).thenReturn(false);

        // Then
        assertDoesNotThrow(() -> supplierBusinessRules.checkIfSupplierNameExists(nonExistingSupplierName));
    }

    @Test
    void checkIfSupplierByIdNotFound_whenSupplierIdNotFound_throwsRecordNotFoundException() {
        // Given
        String nonExistingSupplierId = "1";

        // When
        when(supplierRepository.existsById(nonExistingSupplierId)).thenReturn(false);

        // Then
        assertThrows(RecordNotFoundException.class, () -> supplierBusinessRules.checkIfSupplierByIdNotFound(nonExistingSupplierId));
    }

    @Test
    void checkIfSupplierByIdNotFound_whenSupplierIdExists_doesNotThrowException() {
        // Given
        String existingSupplierId = "1";

        // When
        when(supplierRepository.existsById(existingSupplierId)).thenReturn(true);

        // Then
        assertDoesNotThrow(() -> supplierBusinessRules.checkIfSupplierByIdNotFound(existingSupplierId));
    }
}