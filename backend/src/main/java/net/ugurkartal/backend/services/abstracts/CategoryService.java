package net.ugurkartal.backend.services.abstracts;

import net.ugurkartal.backend.services.dtos.requests.CategoryCreateRequest;
import net.ugurkartal.backend.services.dtos.responses.CategoryCreatedResponse;
import net.ugurkartal.backend.services.dtos.responses.CategoriesGetAllResponse;

import java.util.List;

public interface CategoryService {
    CategoryCreatedResponse addCategory(CategoryCreateRequest categoryCreateRequest);
    List<CategoriesGetAllResponse> getAllCategories();
}
