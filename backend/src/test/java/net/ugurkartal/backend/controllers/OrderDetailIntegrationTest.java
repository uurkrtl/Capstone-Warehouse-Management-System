package net.ugurkartal.backend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.ugurkartal.backend.models.Order;
import net.ugurkartal.backend.models.Product;
import net.ugurkartal.backend.repositories.OrderRepository;
import net.ugurkartal.backend.repositories.ProductRepository;
import net.ugurkartal.backend.services.dtos.requests.OrderDetailRequest;
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
class OrderDetailIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Test
    void addOrderDetail_whenValidInput_shouldReturns201() throws Exception {
        //Given
        String orderId = orderRepository.save(Order.builder().build()).getId();
        String productId = productRepository.save(Product.builder().build()).getId();
        OrderDetailRequest orderDetailRequest = OrderDetailRequest.builder()
                .orderId(orderId)
                .productId(productId)
                .quantity(1)
                .build();

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/order-details")
                        .content(objectMapper.writeValueAsString(orderDetailRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
    }

    @Test
    void addOrderDetail_whenInvalidInputQuantity_shouldReturns400() throws Exception {
        //Given
        OrderDetailRequest orderDetailRequest = OrderDetailRequest.builder().quantity(0).build();

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/order-details")
                        .content(objectMapper.writeValueAsString(orderDetailRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}