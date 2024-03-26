package net.ugurkartal.backend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.ugurkartal.backend.models.Product;
import net.ugurkartal.backend.models.Supplier;
import net.ugurkartal.backend.repositories.ProductRepository;
import net.ugurkartal.backend.repositories.SupplierRepository;
import net.ugurkartal.backend.services.dtos.requests.PurchaseRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class PurchaseControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Test
    void getAllPurchases_shouldReturnsListOfPurchases() throws Exception {
        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/purchases")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());
    }

    @Test
    void addPurchase_whenValidInput_shouldReturns201() throws Exception {
        //Given
        String productId = productRepository.save(Product.builder().build()).getId();
        String supplierId = supplierRepository.save(Supplier.builder().build()).getId();
        PurchaseRequest purchaseRequest = PurchaseRequest.builder()
                .productId(productId)
                .supplierId(supplierId)
                .quantity(10)
                .purchasePrice(100.0)
                .purchaseDate(LocalDateTime.of(2021, 1, 1, 0, 0, 0))
                .build();

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/purchases")
                        .content(objectMapper.writeValueAsString(purchaseRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
    }

    @Test
    void addPurchase_whenInvalidInputName_shouldReturns400() throws Exception {
        //Given
        String productId = productRepository.save(Product.builder().build()).getId();
        String supplierId = supplierRepository.save(Supplier.builder().build()).getId();
        PurchaseRequest purchaseRequest = PurchaseRequest.builder()
                .productId(productId)
                .supplierId(supplierId)
                .quantity(0)
                .purchasePrice(100.0)
                .purchaseDate(LocalDateTime.of(2021, 1, 1, 0, 0, 0))
                .build();

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/purchases")
                        .content(objectMapper.writeValueAsString(purchaseRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}