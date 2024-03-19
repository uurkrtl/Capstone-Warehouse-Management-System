package net.ugurkartal.backend.services.abstracts;

import net.ugurkartal.backend.services.dtos.requests.CategoryCreateRequest;
import net.ugurkartal.backend.services.dtos.responses.CategoryCreatedResponse;

public interface CategoryService {
    CategoryCreatedResponse addCategory(CategoryCreateRequest categoryCreateRequest);
}
