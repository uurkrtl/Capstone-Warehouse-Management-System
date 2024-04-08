package net.ugurkartal.backend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.ugurkartal.backend.models.Product;
import net.ugurkartal.backend.models.enums.StockMovementReason;
import net.ugurkartal.backend.repositories.ProductRepository;
import net.ugurkartal.backend.services.dtos.requests.StockMovementRequest;
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

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@WithMockUser
class StockMovementControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProductRepository productRepository;

    @Test
    void getAllStockMovements_shouldReturnsListOfStockMovements() throws Exception {
        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/stock-movements")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());
    }

    @Test
    void addStockMovement_whenValidInput_shouldReturns201() throws Exception {
        //Given
        String productId = productRepository.save(Product.builder().build()).getId();
        StockMovementRequest stockMovementRequest = StockMovementRequest.builder()
                .productId(productId)
                .quantity(5)
                .type(true)
                .reason(StockMovementReason.SALE)
                .build();

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/stock-movements")
                        .content(objectMapper.writeValueAsString(stockMovementRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
    }
}