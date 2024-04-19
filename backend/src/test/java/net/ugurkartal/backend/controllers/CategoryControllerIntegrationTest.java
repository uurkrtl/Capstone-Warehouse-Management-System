package net.ugurkartal.backend.controllers;

import net.ugurkartal.backend.services.abstracts.CategoryService;
import net.ugurkartal.backend.services.dtos.requests.CategoryRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@WithMockUser(roles = {"ADMIN"})
class CategoryControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CategoryService categoryService;

    @Test
    void getAllCategoriesReturnsListOfCategories() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());
    }

    @Test
    void getCategoryByIdReturnsNotFoundWhenCategoryDoesNotExist() throws Exception {
        String id = "non-existing-id";
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/categories/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void getCategoryByIdReturnsCategoryWhenCategoryExists() throws Exception {
        CategoryRequest categoryRequest = CategoryRequest.builder()
                .name("Test")
                .description("Test")
                .build();

        String categoryId = categoryService.addCategory(categoryRequest).getId();
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/categories/" + categoryId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(categoryId));
    }

    @Test
    void addCategoryReturnsCreatedResponseWhenCategoryIsValid() throws Exception {
        CategoryRequest request = CategoryRequest.builder()
                .name("Test")
                .build();

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/categories")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
    }

    @Test
    void updateCategoryReturnsUpdatedResponseWhenCategoryIsValid() throws Exception {
        CategoryRequest categoryRequest = CategoryRequest.builder()
                .name("Test")
                .description("Test")
                .build();

        String categoryId = categoryService.addCategory(categoryRequest).getId();

        CategoryRequest request = CategoryRequest.builder()
                .name("Updated Test")
                .description("Updated Test")
                .build();

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/categories/" + categoryId)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(categoryId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Updated Test"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Updated Test"));
    }

    @Test
    void changeCategoryStatusChangesStatusCorrectly() throws Exception {
        CategoryRequest categoryRequest = CategoryRequest.builder()
                .name("Test")
                .description("Test")
                .build();

        String categoryId = categoryService.addCategory(categoryRequest).getId();
        boolean newStatus = false;

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/categories/status/" + categoryId)
                        .param("status", String.valueOf(newStatus))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.active").value(newStatus));
    }

    @Test
    @WithMockUser
    void changeCategoryStatus_whenRoleUser_thenReturn403() throws Exception {
        CategoryRequest categoryRequest = CategoryRequest.builder()
                .name("Test")
                .description("Test")
                .build();

        String categoryId = categoryService.addCategory(categoryRequest).getId();
        boolean newStatus = false;

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/categories/status/" + categoryId)
                        .param("status", String.valueOf(newStatus))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }
}