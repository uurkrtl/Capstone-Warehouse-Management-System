package net.ugurkartal.backend.controllers;

import net.ugurkartal.backend.models.Category;
import net.ugurkartal.backend.repositories.CategoryRepository;
import net.ugurkartal.backend.services.abstracts.ProductService;
import net.ugurkartal.backend.services.dtos.requests.ProductCreateRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
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
        ProductCreateRequest productCreateRequest = ProductCreateRequest.builder()
                .name("Test")
                .description("Test")
                .salePrice(10.0)
                .stock(10)
                .criticalStock(5)
                .categoryId(categoryId)
                .imageUrl("https://test.com")
                .build();

        String productId = productService.addProduct(productCreateRequest).getId();
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
        ProductCreateRequest request = ProductCreateRequest.builder()
                .name("Test")
                .description("Test")
                .salePrice(10.0)
                .stock(10)
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
        ProductCreateRequest request = ProductCreateRequest.builder()
                .name("Test")
                .description("Test")
                .salePrice(10)
                .stock(10)
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
        ProductCreateRequest request = ProductCreateRequest.builder()
                .name("Test")
                .description("Test")
                .salePrice(0)
                .stock(10)
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
}