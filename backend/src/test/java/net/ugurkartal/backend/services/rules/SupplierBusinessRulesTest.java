package net.ugurkartal.backend.services.rules;

import net.ugurkartal.backend.core.exceptions.types.DuplicateRecordException;
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
        String existingSupplierName = "Existing Supplier";
        when(supplierRepository.existsByName(existingSupplierName)).thenReturn(true);

        assertThrows(DuplicateRecordException.class, () -> supplierBusinessRules.checkIfSupplierNameExists(existingSupplierName));
    }

    @Test
    void checkIfSupplierNameExists_whenSupplierNameDoesNotExist_doesNotThrowException() {
        String nonExistingSupplierName = "Non Existing Supplier";
        when(supplierRepository.existsByName(nonExistingSupplierName)).thenReturn(false);

        assertDoesNotThrow(() -> supplierBusinessRules.checkIfSupplierNameExists(nonExistingSupplierName));
    }
}