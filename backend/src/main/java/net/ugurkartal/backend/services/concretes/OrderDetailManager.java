package net.ugurkartal.backend.services.concretes;

import lombok.RequiredArgsConstructor;
import net.ugurkartal.backend.core.mappers.ModelMapperService;
import net.ugurkartal.backend.models.Order;
import net.ugurkartal.backend.models.OrderDetail;
import net.ugurkartal.backend.models.Product;
import net.ugurkartal.backend.repositories.OrderDetailRepository;
import net.ugurkartal.backend.repositories.OrderRepository;
import net.ugurkartal.backend.repositories.ProductRepository;
import net.ugurkartal.backend.services.abstracts.IdService;
import net.ugurkartal.backend.services.abstracts.OrderDetailService;
import net.ugurkartal.backend.services.dtos.requests.OrderDetailRequest;
import net.ugurkartal.backend.services.dtos.responses.OrderDetailCreatedResponse;
import net.ugurkartal.backend.services.rules.OrderDetailBusinessRules;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderDetailManager implements OrderDetailService {
    private final OrderDetailRepository orderDetailRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final ModelMapperService modelMapperService;
    private final IdService idService;
    private final OrderDetailBusinessRules orderDetailBusinessRules;

    @Override
    public OrderDetailCreatedResponse addOrderDetail(OrderDetailRequest orderDetailRequest) {
        orderDetailBusinessRules.checkIfOrderByIdNotFound(orderDetailRequest.getOrderId());
        orderDetailBusinessRules.checkIfProductByIdNotFound(orderDetailRequest.getProductId());
        OrderDetail orderDetail = modelMapperService.forRequest().map(orderDetailRequest, OrderDetail.class);
        Order foundOrder = orderRepository.findById(orderDetailRequest.getOrderId()).orElseThrow();
        Product foundProduct = productRepository.findById(orderDetailRequest.getProductId()).orElseThrow();

        orderDetail.setId(idService.generateOrderDetailId());
        orderDetail.setOrder(foundOrder);
        orderDetail.setProduct(foundProduct);
        orderDetail.setPrice(productRepository.findById(orderDetailRequest.getProductId()).orElseThrow().getSalePrice());
        orderDetail.setTotalPrice(orderDetail.getPrice() * orderDetail.getQuantity());
        orderDetail.setActive(true);
        orderDetail = orderDetailRepository.save(orderDetail);
        return modelMapperService.forResponse().map(orderDetail, OrderDetailCreatedResponse.class);
    }
}