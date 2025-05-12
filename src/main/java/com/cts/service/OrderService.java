package com.cts.service;
import com.cts.dto.OrderDTO;
import com.cts.dto.ProductItemDTO;
import com.cts.entity.CartItem;
import com.cts.entity.Order;
import com.cts.entity.OrderItem;
import com.cts.entity.User;
import com.cts.repository.CartItemRepository;
import com.cts.repository.OrderRepository;
import com.cts.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired private OrderRepository orderRepository;
    @Autowired private CartItemRepository cartItemRepository;
    @Autowired private UserRepository userRepository;
@Autowired private ModelMapper modelMapper;
    
    
    public OrderService(OrderRepository orderRepository, CartItemRepository cartItemRepository,
            UserRepository userRepository, ModelMapper modelMapper) {
this.orderRepository = orderRepository;
this.cartItemRepository = cartItemRepository;
this.userRepository = userRepository;
this.modelMapper = modelMapper;
}

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

        List<OrderItem> orderItems = cartItems.stream().map(cartItem -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getTotalPrice());
            return orderItem;
        }).collect(Collectors.toList());

        order.setOrderItems(orderItems);
        Order savedOrder = orderRepository.save(order);

        cartItemRepository.deleteAll(cartItems); // Clear cart after placing order

        return savedOrder;
    }

//    public List<OrderDTO> getUserOrders(Long userId) 
//    {
//        User user = userRepository.findById(userId)
//            .orElseThrow(() -> new RuntimeException("User not found"));
//
//        return orderRepository.findByUserUserId(userId).stream().map(order -> {
//            OrderDTO dto = new OrderDTO();
//            dto.setUsername(user.getName());
//            dto.setTotalPrice(order.getTotalPrice());
//            dto.setOrderStatus(order.getOrderStatus());
//            dto.setPaymentStatus(order.getPaymentStatus());
//
//            List<ProductItemDTO> productDTOs = order.getOrderItems().stream().map(orderItem -> {
//                ProductItemDTO productDTO = new ProductItemDTO();
//                productDTO.setProductName(orderItem.getProduct().getName());
//                productDTO.setQuantity(orderItem.getQuantity());
//                productDTO.setPrice(orderItem.getPrice());
//                return productDTO;
//            }).collect(Collectors.toList());
//
//            dto.setProducts(productDTOs);
//            return dto;
//        }).collect(Collectors.toList());
//    }
    public List<OrderDTO> getUserOrders(Long userId) {
        // Find user, throw exception if not found
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Get orders for the user
        List<Order> orders = orderRepository.findByUserUserId(userId);

        List<OrderDTO> orderDTOs = new ArrayList<>();

        // Loop through orders and map them manually
        for (Order order : orders) {
            OrderDTO dto = new OrderDTO();
            dto.setUsername(user.getName());
            dto.setTotalPrice(order.getTotalPrice());
            dto.setOrderStatus(order.getOrderStatus());
            dto.setPaymentStatus(order.getPaymentStatus());

            List<ProductItemDTO> productDTOs = new ArrayList<>();

            // Loop through order items and map manually
            for (OrderItem orderItem : order.getOrderItems()) {
                ProductItemDTO productDTO = new ProductItemDTO();
                productDTO.setProductName(orderItem.getProduct().getName());
                productDTO.setQuantity(orderItem.getQuantity());
                productDTO.setPrice(orderItem.getPrice());
                productDTOs.add(productDTO);
            }

            dto.setProducts(productDTOs);
            orderDTOs.add(dto);
        }

        return orderDTOs; // Return list of OrderDTOs
    }

}

