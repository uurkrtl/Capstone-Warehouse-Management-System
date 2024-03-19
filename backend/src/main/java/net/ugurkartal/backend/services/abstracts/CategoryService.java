package net.ugurkartal.backend.services.abstracts;

import net.ugurkartal.backend.services.dtos.requests.CategoryCreateRequest;
import net.ugurkartal.backend.services.dtos.responses.CategoryCreatedResponse;
import net.ugurkartal.backend.services.dtos.responses.CategoryGetAllResponse;

import java.util.List;

public interface CategoryService {
    List<CategoryGetAllResponse> getAllCategories();
    CategoryCreatedResponse addCategory(CategoryCreateRequest categoryCreateRequest);
}