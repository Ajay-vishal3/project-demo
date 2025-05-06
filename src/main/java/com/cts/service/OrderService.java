package com.cts.service;

import com.cts.entity.CartItem;
import com.cts.entity.Order;
import com.cts.entity.OrderStatus;
import com.cts.entity.PaymentStatus;
import com.cts.entity.User;
import com.cts.exception.ResourceNotFoundException;
import com.cts.repository.CartItemRepository;
import com.cts.repository.OrderRepository;
import com.cts.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private UserRepository userRepository;

    public Order placeOrder(Long userId, String shippingAddress) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<CartItem> cartItems = cartItemRepository.findByUserUserId(userId);

        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty. Cannot place order.");
        }

        double totalPrice = cartItems.stream()
                .mapToDouble(CartItem::getTotalPrice)
                .sum();

        Order order = new Order();
        order.setUser(user);
        order.setShippingAddress(shippingAddress);
        order.setTotalPrice(totalPrice);
        order.setOrderStatus("Pending");
        order.setPaymentStatus("Pending");

        Order savedOrder = orderRepository.save(order);

        cartItemRepository.deleteAll(cartItems); // Clear cart after placing order

        return savedOrder;
    }

    public List<Order> getUserOrders(Long userId) {
        return orderRepository.findByUserUserId(userId);
    }
}