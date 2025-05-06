package com.cts.service;

import com.cts.entity.CartItem;
import com.cts.entity.Product;
import com.cts.entity.User;
import com.cts.exception.ResourceNotFoundException;
import com.cts.repository.CartItemRepository;
import com.cts.repository.ProductRepository;
import com.cts.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CartItemService {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    public CartItem addToCart(Long userId, Long productId, int quantity) {
        User user = userRepository.findById(userId)
                     .orElseThrow(() -> new RuntimeException("User not found"));

        Product product = productRepository.findById(productId)
                          .orElseThrow(() -> new RuntimeException("Product not found"));

        CartItem cartItem = new CartItem();
        cartItem.setUser(user);
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);
        cartItem.setTotalPrice(product.getPrice() * quantity);

        return cartItemRepository.save(cartItem);
    }

    public List<CartItem> getCartByUserId(Long userId) {
        return cartItemRepository.findByUserUserId(userId);
    }

    public void deleteCartItem(Long cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }
}