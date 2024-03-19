package net.ugurkartal.backend.services.rules;

import lombok.RequiredArgsConstructor;
import net.ugurkartal.backend.core.exceptions.types.RecordNotFoundException;
import net.ugurkartal.backend.repositories.CategoryRepository;
import net.ugurkartal.backend.services.messages.CategoryMessage;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryBusinessRules {
    private final CategoryRepository categoryRepository;

    public void checkIfCategoryByIdNotFound(String id) {
        if(!this.categoryRepository.existsById(id)) {
            throw new RecordNotFoundException(CategoryMessage.CATEGORY_NOT_FOUND);
        }
    }
}