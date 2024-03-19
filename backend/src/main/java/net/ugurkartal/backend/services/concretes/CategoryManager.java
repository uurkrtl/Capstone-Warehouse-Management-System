package net.ugurkartal.backend.services.concretes;

import lombok.RequiredArgsConstructor;
import net.ugurkartal.backend.core.mappers.ModelMapperService;
import net.ugurkartal.backend.models.Category;
import net.ugurkartal.backend.repositories.CategoryRepository;
import net.ugurkartal.backend.services.abstracts.CategoryService;
import net.ugurkartal.backend.services.abstracts.IdService;
import net.ugurkartal.backend.services.dtos.requests.CategoryCreateRequest;
import net.ugurkartal.backend.services.dtos.responses.CategoryCreatedResponse;
import net.ugurkartal.backend.services.rules.CategoryBusinessRules;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CategoryManager implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapperService modelMapperService;
    private final IdService idService;
    private final CategoryBusinessRules categoryBusinessRules;

    @Override
    public CategoryCreatedResponse addCategory(CategoryCreateRequest categoryCreateRequest) {
        categoryBusinessRules.checkIfCategoryNameExists(categoryCreateRequest.getName());
        Category category = modelMapperService.forRequest().map(categoryCreateRequest, Category.class);

        category.setId(idService.generateCategoryId());
        category.setCreatedAt(LocalDateTime.now());
        category.setActive(true);

        category = categoryRepository.save(category);
        return modelMapperService.forResponse().map(category, CategoryCreatedResponse.class);
    }
}