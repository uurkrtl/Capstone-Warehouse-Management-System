package net.ugurkartal.backend.controllers;

import net.ugurkartal.backend.services.dtos.requests.ProductCreateRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        // setup code here if needed
    }

    @Test
    void addProductReturnsCreatedResponseWhenProductIsValid() throws Exception {
        ProductCreateRequest request = ProductCreateRequest.builder()
                .name("Test")
                .description("Test")
                .salePrice(10.0)
                .stock(10)
                .criticalStock(5)
                .categoryId("1")
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
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}