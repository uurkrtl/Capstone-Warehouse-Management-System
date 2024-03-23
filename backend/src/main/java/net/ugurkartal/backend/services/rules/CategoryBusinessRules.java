package net.ugurkartal.backend.services.rules;

import lombok.RequiredArgsConstructor;
import net.ugurkartal.backend.core.exceptions.types.DuplicateRecordException;
import net.ugurkartal.backend.core.exceptions.types.HaveActiveProductException;
import net.ugurkartal.backend.core.exceptions.types.RecordNotFoundException;
import net.ugurkartal.backend.models.Category;
import net.ugurkartal.backend.repositories.CategoryRepository;
import net.ugurkartal.backend.repositories.ProductRepository;
import net.ugurkartal.backend.services.messages.CategoryMessage;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryBusinessRules {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public void checkIfCategoryByIdNotFound(String id) {
        if(!this.categoryRepository.existsById(id)) {
            throw new RecordNotFoundException(CategoryMessage.CATEGORY_NOT_FOUND);
        }
    }

    public void checkIfCategoryNameExists(String name) {
        if(this.categoryRepository.existsByName(name)) {
            throw new RecordNotFoundException(CategoryMessage.CATEGORY_NAME_EXISTS);
        }
    }

    public void checkIfCategoryNameExists(String categoryName, String id) {
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        if(categoryOptional.isPresent()) {
            Category category = categoryOptional.get();
            if(!category.getName().equals(categoryName) && categoryRepository.existsByName(categoryName)) {
                throw new DuplicateRecordException(CategoryMessage.CATEGORY_NAME_EXISTS);
            }
        }
    }

    public void checkIfCategoryHasActiveProducts(String categoryId) {
        if(this.productRepository.existsByCategoryIdAndIsActiveTrue(categoryId)) {
            throw new HaveActiveProductException(CategoryMessage.CATEGORY_HAS_ACTIVE_PRODUCTS);
        }
    }
}