package net.ugurkartal.backend.controllers;

import lombok.RequiredArgsConstructor;
import net.ugurkartal.backend.services.abstracts.CategoryService;
import net.ugurkartal.backend.services.dtos.requests.CategoryCreateRequest;
import net.ugurkartal.backend.services.dtos.responses.CategoryCreatedResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryCreatedResponse addCategory(@RequestBody CategoryCreateRequest categoryCreateRequest) {
        return categoryService.addCategory(categoryCreateRequest);
    }
}