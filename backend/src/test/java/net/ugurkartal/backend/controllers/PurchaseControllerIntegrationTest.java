package net.ugurkartal.backend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.ugurkartal.backend.models.Product;
import net.ugurkartal.backend.models.Purchase;
import net.ugurkartal.backend.models.Supplier;
import net.ugurkartal.backend.repositories.ProductRepository;
import net.ugurkartal.backend.repositories.PurchaseRepository;
import net.ugurkartal.backend.repositories.SupplierRepository;
import net.ugurkartal.backend.services.abstracts.PurchaseService;
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

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;
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
    private PurchaseRepository purchaseRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private PurchaseService purchaseService;

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
    void getPurchasesByProductId_whenPurchasesExistForProduct_shouldReturnThosePurchases() throws Exception {
        // Given
        Product product = productRepository.save(Product.builder().build());
        Purchase purchase1 = Purchase.builder().id("1").quantity(5).purchasePrice(10).build();
        Purchase purchase2 = Purchase.builder().id("2").quantity(10).purchasePrice(20).build();
        purchase1.setProduct(product);
        purchase2.setProduct(product);
        purchaseRepository.save(purchase1);
        purchaseRepository.save(purchase2);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/purchases/product/" + product.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", is("1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id", is("2")));
    }

    @Test
    void getPurchasesBySupplierId_whenPurchasesExistForSupplier_shouldReturnThosePurchases() throws Exception {
        // Given
        Supplier supplier = supplierRepository.save(Supplier.builder().build());
        Purchase purchase1 = Purchase.builder().id("1").build();
        Purchase purchase2 = Purchase.builder().id("2").build();
        purchase1.setSupplier(supplier);
        purchase2.setSupplier(supplier);
        purchaseRepository.save(purchase1);
        purchaseRepository.save(purchase2);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/purchases/supplier/" + supplier.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", is("1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id", is("2")));
    }

    @Test
    void getPurchaseById_whenPurchaseExists_shouldReturnPurchase() throws Exception {
        //Given
        String productId = productRepository.save(Product.builder().build()).getId();
        String supplierId = supplierRepository.save(Supplier.builder().build()).getId();
        PurchaseRequest productRequest = PurchaseRequest.builder()
                .productId(productId)
                .supplierId(supplierId)
                .quantity(10)
                .purchasePrice(100.0)
                .purchaseDate(LocalDateTime.of(2021, 1, 1, 0, 0, 0))
                .build();

        String purchaseId = purchaseService.addPurchase(productRequest).getId();

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/purchases/" + purchaseId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(purchaseId));

    }

    @Test
    void getPurchaseById_WhenPurchaseDoesNotExist_shouldThrowsNoSuchElementException() throws Exception {
        //Given
        String id = "non-existing-id";

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/purchases/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
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

    @Test
    void updatePurchase_whenPurchaseRequestIsValid_shouldReturnPurchaseCreatedResponse() throws Exception {
        // Given
        String productId = productRepository.save(Product.builder().build()).getId();
        String supplierId = supplierRepository.save(Supplier.builder().build()).getId();
        PurchaseRequest purchaseRequest = PurchaseRequest.builder()
                .productId(productId)
                .supplierId(supplierId)
                .purchasePrice(100.0)
                .quantity(10)
                .purchaseDate(LocalDateTime.now())
                .build();

        String purchaseId = purchaseService.addPurchase(purchaseRequest).getId();

        PurchaseRequest updatedPurchaseRequest = PurchaseRequest.builder()
                .productId(productId)
                .supplierId(supplierId)
                .purchasePrice(200.0)
                .quantity(20)
                .purchaseDate(LocalDateTime.now())
                .build();

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/purchases/" + purchaseId)
                        .content(objectMapper.writeValueAsString(updatedPurchaseRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(purchaseId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.productId").value(productId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.supplierId").value(supplierId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.purchasePrice").value(200.0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.quantity").value(20));
    }

    @Test
    void changePurchaseStatus_whenPurchaseExists_shouldChangeStatusCorrectly() throws Exception {
        // Given
        String productId = productRepository.save(Product.builder().build()).getId();
        String supplierId = supplierRepository.save(Supplier.builder().build()).getId();
        PurchaseRequest purchaseRequest = PurchaseRequest.builder()
                .productId(productId)
                .supplierId(supplierId)
                .purchasePrice(100.0)
                .quantity(10)
                .purchaseDate(LocalDateTime.now())
                .build();

        String purchaseId = purchaseService.addPurchase(purchaseRequest).getId();
        boolean newStatus = false;

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/purchases/status/" + purchaseId)
                        .param("status", String.valueOf(newStatus))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.active").value(newStatus));
    }
}