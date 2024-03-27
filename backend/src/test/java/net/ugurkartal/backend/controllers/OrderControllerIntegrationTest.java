package net.ugurkartal.backend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.ugurkartal.backend.services.dtos.requests.OrderRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class OrderControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void addOrder_whenValidInput_shouldReturns201() throws Exception {
        //Given
        OrderRequest orderRequest = OrderRequest.builder().customerName("Customer").build();

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/orders")
                        .content(objectMapper.writeValueAsString(orderRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
    }

    @Test
    void addOrder_whenInvalidInputName_shouldReturns400() throws Exception {
        //Given
        OrderRequest orderRequest = OrderRequest.builder().customerName("S").build();

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/orders")
                        .content(objectMapper.writeValueAsString(orderRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
