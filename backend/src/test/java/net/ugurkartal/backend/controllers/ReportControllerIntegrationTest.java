package net.ugurkartal.backend.controllers;

import net.ugurkartal.backend.models.Product;
import net.ugurkartal.backend.repositories.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReportControllerIntegrationTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getProductsOutOfStock_whenNoProductsOutOfStock_shouldReturnEmptyList() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/reports/products-out-of-stock")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isEmpty());
    }

    @Test
    void getProductsOutOfStock_whenSomeProductsOutOfStock_shouldReturnList() throws Exception {
        // Given
        Product product1 = Product.builder().id("1").name("Product 1").stock(0).isActive(true).build();
        Product product2 = Product.builder().id("2").name("Product 2").stock(1).isActive(true).build();
        Product product3 = Product.builder().id("3").name("Product 3").stock(0).isActive(true).build();
        Product product4 = Product.builder().id("4").name("Product 4").stock(0).isActive(false).build();
        productRepository.save(product1);
        productRepository.save(product2);
        productRepository.save(product3);
        productRepository.save(product4);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/reports/products-out-of-stock")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", is("1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id", is("3")));
    }

    @Test
    void getProductsLowInStock_whenSomeProductsLowInStock_shouldReturnThoseProducts() throws Exception {
        // Given
        Product product1 = Product.builder().id("1").name("Product 1").stock(5).criticalStock(10).isActive(true).build();
        Product product2 = Product.builder().id("2").name("Product 2").stock(10).criticalStock(20).isActive(true).build();
        productRepository.save(product1);
        productRepository.save(product2);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/reports/products-low-stock")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", is("1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id", is("2")));
    }
}