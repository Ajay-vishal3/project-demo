package com.cts;


import com.cts.dto.OrderDTO;
import com.cts.dto.ProductItemDTO;
import com.cts.entity.CartItem;
import com.cts.entity.Order;
import com.cts.entity.Product;
import com.cts.entity.User;
import com.cts.repository.CartItemRepository;
import com.cts.repository.OrderRepository;
import com.cts.repository.UserRepository;
import com.cts.service.OrderService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {
    
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private CartItemRepository cartItemRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ModelMapper modelMapper;
    @InjectMocks
    private OrderService orderService;

//    @BeforeEach
//    public void setUp() {
//        orderRepository = mock(OrderRepository.class);
//        cartItemRepository = mock(CartItemRepository.class);
//        userRepository = mock(UserRepository.class);
//        modelMapper = new ModelMapper();
//        orderService = new OrderService(orderRepository, cartItemRepository, userRepository, modelMapper);
//    }

    @Test
    public void testPlaceOrder_Success() {
        Long userId = 1L;
        String address = "123 Main St";

        User user = new User();
        user.setUserId(userId);
        user.setName("John");

        Product product = new Product();
        product.setName("Product A");

        CartItem item = new CartItem();
        item.setProduct(product);
        item.setQuantity(2);
        item.setTotalPrice(50);
        item.setUser(user);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(cartItemRepository.findByUserUserId(userId)).thenReturn(List.of(item));
        when(orderRepository.save(any(Order.class))).thenAnswer(i -> i.getArguments()[0]);

        Order order = orderService.placeOrder(userId, address);

        assertNotNull(order);
        assertEquals(address, order.getShippingAddress());
        verify(cartItemRepository, times(1)).deleteAll(List.of(item));
    }

    @Test
    public void testPlaceOrder_UserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        Exception exception = assertThrows(RuntimeException.class, () -> {
            orderService.placeOrder(1L, "Address");
        });
        assertEquals("User not found", exception.getMessage());
    }

    @Test
    public void testPlaceOrder_EmptyCart() {
        User user = new User();
        user.setUserId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(cartItemRepository.findByUserUserId(1L)).thenReturn(Collections.emptyList());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            orderService.placeOrder(1L, "Address");
        });
        assertEquals("Cart is empty. Cannot place order.", exception.getMessage());
    }

    @Test
    public void testGetUserOrders() {
        Long userId = 1L;
        User user = new User();
        user.setUserId(userId);
        user.setName("Alice");

        Product product = new Product();
        product.setName("Product X");

        CartItem item = new CartItem();
        item.setProduct(product);
        item.setQuantity(1);
        item.setUser(user);
        item.setTotalPrice(100);

        Order order = new Order();
        order.setUser(user);
        order.setTotalPrice(100);
        order.setOrderStatus("Pending");
        order.setPaymentStatus("Pending");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(orderRepository.findByUserUserId(userId)).thenReturn(List.of(order));

        List<OrderDTO> orderDTOs = orderService.getUserOrders(userId);

        assertNotNull(orderDTOs);
        assertEquals(1, orderDTOs.size());
        assertEquals("Alice", orderDTOs.get(0).getUsername());
    }
}

