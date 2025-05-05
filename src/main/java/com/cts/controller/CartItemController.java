package com.cts.controller;

import com.cts.entity.CartItem;
import com.cts.service.CartItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartItemController {
	private final CartItemService cartItemService;

	public CartItemController(CartItemService cartItemService) {
		this.cartItemService = cartItemService;
	}

	@PostMapping("/add")
	public ResponseEntity<CartItem> addItem(@RequestParam Long productId, @RequestParam int quantity,@RequestParam int userId) {
		return ResponseEntity.ok(cartItemService.addCartItem(productId, quantity,userId));
	}

	@GetMapping
	public ResponseEntity<List<CartItem>> viewCart() {
		return ResponseEntity.ok(cartItemService.getAllCartItems());
	}

	@PutMapping("/{id}")
	public ResponseEntity<CartItem> updateItem(@PathVariable Long id, @RequestParam int quantity) {
		return ResponseEntity.ok(cartItemService.updateCartItem(id, quantity));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
		cartItemService.deleteCartItem(id);
		return ResponseEntity.noContent().build();
	}
}
