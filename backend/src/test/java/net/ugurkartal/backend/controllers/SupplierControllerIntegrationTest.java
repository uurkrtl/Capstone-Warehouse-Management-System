package net.ugurkartal.backend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.ugurkartal.backend.services.abstracts.SupplierService;
import net.ugurkartal.backend.services.dtos.requests.SupplierRequest;
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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@WithMockUser(roles = {"ADMIN"})
class SupplierControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SupplierService supplierService;

    @Test
    void getAllSuppliers_shouldReturnsListOfSuppliers() throws Exception {
        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/suppliers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());
    }

    @Test
    void addSupplier_whenValidInput_shouldReturns201() throws Exception {
        //Given
        SupplierRequest supplierRequest = SupplierRequest.builder()
                .name("Supplier")
                .contactName("Contact Name")
                .email("test@test.com")
                .phone("1234567890")
                .build();

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/suppliers")
                        .content(objectMapper.writeValueAsString(supplierRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
    }

    @Test
    void getSupplierById_whenSupplierExists_shouldReturnSupplier() throws Exception {
        //Given
        SupplierRequest supplierRequest = SupplierRequest.builder()
                .name("Supplier")
                .contactName("Contact Name")
                .email("test@test.com")
                .phone("1234567890")
                .build();

        String supplierId = supplierService.addSupplier(supplierRequest).getId();

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/suppliers/" + supplierId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(supplierId));

    }

    @Test
    void getSupplierById_WhenSupplierDoesNotExist_shouldThrowsNoSuchElementException() throws Exception {
        //Given
        String id = "non-existing-id";

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/suppliers/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void addSupplier_whenInvalidInputName_shouldReturns400() throws Exception {
        //Given
        SupplierRequest supplierRequest = SupplierRequest.builder()
                .name("S")
                .contactName("Contact Name")
                .email("test@test.com")
                .phone("1234567890")
                .build();

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/suppliers")
                        .content(objectMapper.writeValueAsString(supplierRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateSupplier_whenSupplierRequestIsValid_shouldReturnSupplierCreatedResponse() throws Exception {
        //Given
        SupplierRequest supplierRequest = SupplierRequest.builder()
                .name("Test")
                .contactName("Test")
                .email("mail@mail.com")
                .phone("1234567890")
                .build();

        String supplierId = supplierService.addSupplier(supplierRequest).getId();

        SupplierRequest request = SupplierRequest.builder()
                .name("Updated Test")
                .contactName("Updated Test")
                .email("updated_mail@mail.com")
                .phone("Updated 1234567890")
                .build();

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/suppliers/" + supplierId)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(supplierId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Updated Test"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.contactName").value("Updated Test"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("updated_mail@mail.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phone").value("Updated 1234567890"));
    }

    @Test
    void changeSupplierStatus_whenSupplierExists_shouldChangeStatusCorrectly() throws Exception {
        //Given
        SupplierRequest supplierRequest = SupplierRequest.builder()
                .name("Test")
                .contactName("Test")
                .email("mail@mail.com")
                .phone("1234567890")
                .build();

        String supplierId = supplierService.addSupplier(supplierRequest).getId();
        boolean newStatus = false;

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/suppliers/status/" + supplierId)
                        .param("status", String.valueOf(newStatus))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.active").value(newStatus));
    }

    @Test
    @WithMockUser
    void changeSupplierStatus_whenRoleUser_shouldReurn403() throws Exception {
        //Given
        SupplierRequest supplierRequest = SupplierRequest.builder()
                .name("Test")
                .contactName("Test")
                .email("mail@mail.com")
                .phone("1234567890")
                .build();

        String supplierId = supplierService.addSupplier(supplierRequest).getId();
        boolean newStatus = false;

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/suppliers/status/" + supplierId)
                        .param("status", String.valueOf(newStatus))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }
}
