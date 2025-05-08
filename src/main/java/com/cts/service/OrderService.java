package com.cts.service;
import com.cts.dto.OrderDTO;
import com.cts.dto.ProductItemDTO;
import com.cts.entity.CartItem;
import com.cts.entity.Order;
import com.cts.entity.User;
import com.cts.repository.CartItemRepository;
import com.cts.repository.OrderRepository;
import com.cts.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired private OrderRepository orderRepository;
    @Autowired private CartItemRepository cartItemRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private ModelMapper modelMapper;

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

    public List<OrderDTO> getUserOrders(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));

        List<CartItem> cartItems = cartItemRepository.findByUserUserId(userId);

        List<ProductItemDTO> productDTOs = cartItems.stream().map(item -> {
            ProductItemDTO dto = new ProductItemDTO();
            dto.setProductName(item.getProduct().getName());
            dto.setQuantity(item.getQuantity());
            return dto;
        }).toList();

        return orderRepository.findByUserUserId(userId).stream().map(order -> {
            OrderDTO dto = modelMapper.map(order, OrderDTO.class);
            dto.setUsername(user.getName());
            dto.setProducts(productDTOs); // manually set the list of products
            return dto;
        }).toList();
    }
}


//    public Order placeOrder(Long userId, String shippingAddress) {
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        List<CartItem> cartItems = cartItemRepository.findByUserUserId(userId);
//
//        if (cartItems.isEmpty()) {
//            throw new RuntimeException("Cart is empty. Cannot place order.");
//        }
//
//        double totalPrice = cartItems.stream()
//                .mapToDouble(CartItem::getTotalPrice)
//                .sum();
//
//        Order order = new Order();
//        order.setUser(user);
//        order.setShippingAddress(shippingAddress);
//        order.setTotalPrice(totalPrice);
//        order.setOrderStatus("Pending");
//        order.setPaymentStatus("Pending");
//
//        Order savedOrder = orderRepository.save(order);
//
//        cartItemRepository.deleteAll(cartItems); // Clear cart after placing order
//
//        return savedOrder;
//    }

//    public List<Order> getUserOrders(Long userId) {
//        return orderRepository.findByUserUserId(userId);
//    }
    



