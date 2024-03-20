package net.ugurkartal.backend.services.rules;

import net.ugurkartal.backend.core.exceptions.types.RecordNotFoundException;
import net.ugurkartal.backend.repositories.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class CategoryBusinessRulesTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryBusinessRules categoryBusinessRules;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void categoryNameExists_throwsRecordNotFoundException() {
        String existingCategoryName = "Existing Category";
        when(categoryRepository.existsByName(existingCategoryName)).thenReturn(true);

        assertThrows(RecordNotFoundException.class, () -> categoryBusinessRules.checkIfCategoryNameExists(existingCategoryName));
    }

    @Test
    void categoryNameDoesNotExist_doesNotThrowException() {
        String nonExistingCategoryName = "Non Existing Category";
        when(categoryRepository.existsByName(nonExistingCategoryName)).thenReturn(false);

        assertDoesNotThrow(() -> categoryBusinessRules.checkIfCategoryNameExists(nonExistingCategoryName));
    }

    @Test
    void categoryByIdNotFound_throwsRecordNotFoundException() {
        String nonExistingCategoryId = "Non Existing Category Id";
        when(categoryRepository.existsById(nonExistingCategoryId)).thenReturn(false);

        assertThrows(RecordNotFoundException.class, () -> categoryBusinessRules.checkIfCategoryByIdNotFound(nonExistingCategoryId));
    }

    @Test
    void categoryByIdFound_doesNotThrowException() {
        String existingCategoryId = "Existing Category Id";
        when(categoryRepository.existsById(existingCategoryId)).thenReturn(true);

        assertDoesNotThrow(() -> categoryBusinessRules.checkIfCategoryByIdNotFound(existingCategoryId));
    }
}