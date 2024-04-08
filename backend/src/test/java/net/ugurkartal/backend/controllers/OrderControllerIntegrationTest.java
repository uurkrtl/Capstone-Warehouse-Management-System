package net.ugurkartal.backend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.ugurkartal.backend.models.enums.OrderStatus;
import net.ugurkartal.backend.services.abstracts.OrderService;
import net.ugurkartal.backend.services.dtos.requests.OrderRequest;
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
@WithMockUser
class OrderControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private OrderService orderService;

    @Test
    void getAllOrders_shouldReturnsListOfOrders() throws Exception {
        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());
    }

    @Test
    void getOrderById_whenOrderExists_shouldReturnOrder() throws Exception {
        //Given
        OrderRequest orderRequest = OrderRequest.builder().customerName("Test").build();

        String orderId = orderService.addOrder(orderRequest).getId();

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/orders/" + orderId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(orderId));

    }

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

    @Test
    void changeOrderStatus_whenOrderExists_shouldChangeStatusCorrectly() throws Exception {
        //Given
        OrderRequest orderRequest = OrderRequest.builder().customerName("Test").build();

        String orderId = orderService.addOrder(orderRequest).getId();
        String newStatus = OrderStatus.CONFIRMED.name();

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/orders/status/" + orderId)
                        .param("status", newStatus)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.orderStatus").value(newStatus));
    }
}
