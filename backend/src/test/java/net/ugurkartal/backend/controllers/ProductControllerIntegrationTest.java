package net.ugurkartal.backend.controllers;

import net.ugurkartal.backend.models.Category;
import net.ugurkartal.backend.repositories.CategoryRepository;
import net.ugurkartal.backend.services.abstracts.ProductService;
import net.ugurkartal.backend.services.dtos.requests.ProductRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@WithMockUser
class ProductControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductService productService;

    @Test
    void getAllProductsReturnsListOfProducts() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/products")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());
    }

    @Test
    void getProductByIdReturnsProductWhenProductExists() throws Exception {
        String categoryId = categoryRepository.save(Category.builder().build()).getId();
        ProductRequest productRequest = ProductRequest.builder()
                .name("Test")
                .description("Test")
                .salePrice(10.0)
                .criticalStock(5)
                .categoryId(categoryId)
                .imageUrl("https://test.com")
                .build();

        String productId = productService.addProduct(productRequest).getId();
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/products/" + productId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(productId));
    }

    @Test
    void getProductByIdReturnsNotFoundWhenProductDoesNotExist() throws Exception {
        String id = "non-existing-id";
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/products/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void addProductReturnsCreatedResponseWhenProductIsValid() throws Exception {
        String categoryId = categoryRepository.save(Category.builder().build()).getId();
        ProductRequest request = ProductRequest.builder()
                .name("Test")
                .description("Test")
                .salePrice(10.0)
                .criticalStock(5)
                .categoryId(categoryId)
                .imageUrl("https://test.com")
                .build();

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/products")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
    }

    @Test
    void addProductReturnsNotFoundExceptionWhenCategoryNotFound() throws Exception {
        ProductRequest request = ProductRequest.builder()
                .name("Test")
                .description("Test")
                .salePrice(10)
                .criticalStock(5)
                .categoryId("1")
                .imageUrl("https://test.com")
                .build();

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/products")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void addProductReturnsBadRequestWhenProductIsInvalid() throws Exception {
        ProductRequest request = ProductRequest.builder()
                .name("Test")
                .description("Test")
                .salePrice(0)
                .criticalStock(5)
                .categoryId("1")
                .imageUrl("https://test.com")
                .build();

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/products")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateProductReturnsUpdatedResponseWhenProductIsValid() throws Exception {
        String categoryId = categoryRepository.save(Category.builder().build()).getId();
        ProductRequest productRequest = ProductRequest.builder()
                .name("Test")
                .description("Test")
                .salePrice(10.0)
                .criticalStock(5)
                .categoryId(categoryId)
                .imageUrl("https://test.com")
                .build();

        String productId = productService.addProduct(productRequest).getId();

        ProductRequest request = ProductRequest.builder()
                .name("Updated Test")
                .description("Updated Test")
                .salePrice(15.0)
                .criticalStock(5)
                .categoryId(categoryId)
                .imageUrl("https://updatedtest.com")
                .build();

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/products/" + productId)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(productId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Updated Test"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Updated Test"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.salePrice").value(15.0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.criticalStock").value(5))
                .andExpect(MockMvcResultMatchers.jsonPath("$.imageUrl").value("https://updatedtest.com"));
    }

    @Test
    void changeProductStatusChangesStatusCorrectly() throws Exception {
        String categoryId = categoryRepository.save(Category.builder().build()).getId();
        ProductRequest productRequest = ProductRequest.builder()
                .name("Test")
                .description("Test")
                .salePrice(10.0)
                .criticalStock(5)
                .categoryId(categoryId)
                .imageUrl("https://test.com")
                .build();

        String productId = productService.addProduct(productRequest).getId();
        boolean newStatus = false;

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/products/status/" + productId)
                        .param("status", String.valueOf(newStatus))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.active").value(newStatus));
    }
}