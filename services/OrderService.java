package com.ecommerce.EcomApplication.services;

import com.ecommerce.EcomApplication.dtos.OrderItemDTO;
import com.ecommerce.EcomApplication.dtos.OrderResponse;
import com.ecommerce.EcomApplication.models.*;
import com.ecommerce.EcomApplication.repos.OrderRepository;
import com.ecommerce.EcomApplication.repos.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final CartService cartService;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;


    public Optional<OrderResponse> createOrder(String userId) {
        List<CartItem> cartItems = cartService.getCartByUserId(userId);
        if(cartItems.isEmpty()){
            return Optional.empty();
        }
        Optional<User> optionalUser = userRepository.findById(Long.valueOf(userId));
        if(optionalUser.isEmpty()){
            return Optional.empty();
        }
        User user = optionalUser.get();
        BigDecimal totalPrice = cartItems.stream()
                .map(CartItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Order order = new Order();
        order.setUser(user);
        order.setStatus(OrderStatus.CONFIRMED);
        order.setTotal(totalPrice);
        List<OrderItem> orderItems = cartItems.stream()
                .map(item -> new OrderItem(null, item.getProduct(), item.getQuantity(), item.getPrice(), order)).toList();
        order.setItems(orderItems);
        Order savedOrder = orderRepository.save(order);
        cartService.clearCart(userId);
        return Optional.of(mapToOrderResponse(savedOrder));

    }

    private OrderResponse mapToOrderResponse(Order savedOrder) {
        return  new OrderResponse(
                savedOrder.getId(),
                savedOrder.getTotal(),
                savedOrder.getStatus(),
                savedOrder.getItems().stream()
                        .map(item -> new OrderItemDTO(
                                item.getId(),
                                item.getProduct().getId(),
                                item.getQuantity(),
                                item.getPrice(),
                                item.getPrice().multiply(new BigDecimal(item.getQuantity()))
                        )).toList(),
                savedOrder.getCreatedAt()
        );
    }
}
