package com.cts.service;

import com.cts.entity.CartItem;
import com.cts.entity.Product;
import com.cts.exception.ResourceNotFoundException;
import com.cts.repository.CartItemRepository;
import com.cts.repository.ProductRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CartItemService {
	private final CartItemRepository cartItemRepository;
	private final ProductRepository productRepository;

	public CartItemService(CartItemRepository cartItemRepository, ProductRepository productRepository) {
		this.cartItemRepository = cartItemRepository;
		this.productRepository = productRepository;
	}

	public CartItem addCartItem(Long productId, int quantity,int userId) {
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));
		double totalPrice = product.getPrice() * quantity;
		CartItem cartItem = CartItem.builder().product(product).quantity(quantity).totalPrice(totalPrice).build();
		return cartItemRepository.save(cartItem);
	}

	public List<CartItem> getAllCartItems() {
		return cartItemRepository.findAll();
	}

	public void deleteCartItem(Long cartItemId) {
		CartItem item = cartItemRepository.findById(cartItemId)
				.orElseThrow(() -> new ResourceNotFoundException("CartItem not found with id: " + cartItemId));
		cartItemRepository.delete(item);
	}

	public CartItem updateCartItem(Long cartItemId, int newQuantity) {
		CartItem item = cartItemRepository.findById(cartItemId)
				.orElseThrow(() -> new ResourceNotFoundException("CartItem not found with id: " + cartItemId));
		item.setQuantity(newQuantity);
		item.setTotalPrice(item.getProduct().getPrice() * newQuantity);
		return cartItemRepository.save(item);
	}
}