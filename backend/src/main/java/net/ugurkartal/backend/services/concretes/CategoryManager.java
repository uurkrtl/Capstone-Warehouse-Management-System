package net.ugurkartal.backend.services.concretes;

import lombok.RequiredArgsConstructor;
import net.ugurkartal.backend.core.mappers.ModelMapperService;
import net.ugurkartal.backend.models.Category;
import net.ugurkartal.backend.repositories.CategoryRepository;
import net.ugurkartal.backend.services.abstracts.CategoryService;
import net.ugurkartal.backend.services.abstracts.IdService;
import net.ugurkartal.backend.services.dtos.requests.CategoryRequest;
import net.ugurkartal.backend.services.dtos.responses.CategoryCreatedResponse;
import net.ugurkartal.backend.services.dtos.responses.CategoryGetAllResponse;
import net.ugurkartal.backend.services.rules.CategoryBusinessRules;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryManager implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapperService modelMapperService;
    private final IdService idService;
    private final CategoryBusinessRules categoryBusinessRules;

    @Override
    public List<CategoryGetAllResponse> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(category->this.modelMapperService.forResponse()
                        .map(category, CategoryGetAllResponse.class)).toList();
    }

    @Override
    public CategoryCreatedResponse addCategory(CategoryRequest categoryRequest) {
        categoryBusinessRules.checkIfCategoryNameExists(categoryRequest.getName());
        Category category = modelMapperService.forRequest().map(categoryRequest, Category.class);

        category.setId(idService.generateCategoryId());
        category.setCreatedAt(LocalDateTime.now());
        category.setActive(true);

        category = categoryRepository.save(category);
        return modelMapperService.forResponse().map(category, CategoryCreatedResponse.class);
    }
}