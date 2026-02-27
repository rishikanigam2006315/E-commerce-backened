package com.rishika.ecommerce.order;

import com.rishika.ecommerce.cart.Cart;
import com.rishika.ecommerce.cart.CartRepository;
import com.rishika.ecommerce.user.User;
import com.rishika.ecommerce.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final UserRepository userRepository;

    public Order placeOrder(String address) {

        // 🔐 Logged-in user from JWT
        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Cart empty"));

        Order order = new Order();
        order.setUser(user);
        order.setAddress(address);
        order.setStatus(OrderStatus.PENDING);
        order.setCreatedAt(LocalDateTime.now());
        order.setTotalAmount(cart.getTotalPrice());

        List<OrderItem> orderItems = cart.getItems().stream().map(item -> {
            OrderItem oi = new OrderItem();
            oi.setOrder(order);
            oi.setQuantity(item.getQuantity());
            oi.setPrice(item.getPrice());
            return oi;
        }).toList();

        order.setItems(orderItems);

        cartRepository.delete(cart);

        return orderRepository.save(order);
    }
}