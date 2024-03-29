package net.ugurkartal.backend.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.ugurkartal.backend.services.abstracts.CategoryService;
import net.ugurkartal.backend.services.dtos.requests.CategoryRequest;
import net.ugurkartal.backend.services.dtos.responses.CategoryCreatedResponse;
import net.ugurkartal.backend.services.dtos.responses.CategoryGetAllResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public List<CategoryGetAllResponse> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    public CategoryCreatedResponse getCategoryById(@PathVariable String id) {
        return categoryService.getCategoryById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryCreatedResponse addCategory(@Valid @RequestBody CategoryRequest categoryRequest) {
        return categoryService.addCategory(categoryRequest);
    }

    @PutMapping("/{id}")
    public CategoryCreatedResponse updateCategory(@Valid @PathVariable String id, @RequestBody CategoryRequest categoryRequest) {
        return categoryService.updateCategory(id, categoryRequest);
    }

    @PutMapping("/status/{id}")
    public CategoryCreatedResponse changeCategoryStatus(@PathVariable String id, @RequestParam boolean status) {
        return categoryService.changeCategoryStatus(id, status);
    }
}