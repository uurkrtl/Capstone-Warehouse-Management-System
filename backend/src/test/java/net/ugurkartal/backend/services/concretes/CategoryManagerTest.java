package net.ugurkartal.backend.services.concretes;

import net.ugurkartal.backend.core.mappers.ModelMapperService;
import net.ugurkartal.backend.models.Category;
import net.ugurkartal.backend.repositories.CategoryRepository;
import net.ugurkartal.backend.services.abstracts.IdService;
import net.ugurkartal.backend.services.dtos.requests.CategoryRequest;
import net.ugurkartal.backend.services.dtos.responses.CategoryCreatedResponse;
import net.ugurkartal.backend.services.dtos.responses.CategoryGetAllResponse;
import net.ugurkartal.backend.services.rules.CategoryBusinessRules;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class CategoryManagerTest {

    @InjectMocks
    private CategoryManager categoryManager;
    private ModelMapper modelMapper;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryBusinessRules categoryBusinessRules;

    @Mock
    private ModelMapperService modelMapperService;

    @Mock
    private IdService idService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        modelMapper = mock(ModelMapper.class);
    }

    @Test
    void getAllCategoriesReturnsListOfCategories() {
        List<Category> categories = List.of(
                Category.builder().id("1").build(),
                Category.builder().id("2").build()
        );

        when(modelMapperService.forResponse()).thenReturn(modelMapper);
        when(categoryRepository.findAll()).thenReturn(categories);

        List<CategoryGetAllResponse> response = categoryManager.getAllCategories();

        assertEquals(2, response.size());
    }

    @Test
    void addCategoryReturnsCreatedCategoryWhenCategoryIsValid() {
        Category category = Category.builder().id("1").build();
        CategoryCreatedResponse expectedResponse = CategoryCreatedResponse.builder().id("1").build();
        CategoryRequest request = CategoryRequest.builder()
                .name("Test")
                .description("Test")
                .build();

        when(idService.generateCategoryId()).thenReturn("1");
        when(modelMapperService.forRequest()).thenReturn(modelMapper);
        when(modelMapperService.forResponse()).thenReturn(modelMapper);
        when(modelMapper.map(request, Category.class)).thenReturn(category);
        when(categoryRepository.save(category)).thenReturn(category);
        when(modelMapper.map(category, CategoryCreatedResponse.class)).thenReturn(expectedResponse);

        CategoryCreatedResponse actualResponse = categoryManager.addCategory(request);

        verify(categoryBusinessRules, times(1)).checkIfCategoryNameExists(anyString());
        verify(categoryRepository, times(1)).save(category);
        assertEquals(expectedResponse.getId(), actualResponse.getId());
    }
}