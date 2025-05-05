package com.cts.controller;

import com.cts.entity.Order;
import com.cts.entity.OrderStatus;
import com.cts.entity.PaymentStatus;
import com.cts.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
	private final OrderService orderService;

	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}

	@PostMapping
	public ResponseEntity<Order> placeOrder(@RequestBody Order order) {
		return ResponseEntity.ok(orderService.placeOrder(order));
	}

	@GetMapping
	public ResponseEntity<List<Order>> getAllOrders() {
		return ResponseEntity.ok(orderService.getAllOrders());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Order> getOrder(@PathVariable Long id) {
		return ResponseEntity.ok(orderService.getOrderById(id));
	}

	@PutMapping("/{id}/status")
	public ResponseEntity<Order> updateOrderStatus(@PathVariable Long id, @RequestParam OrderStatus status) {
		return ResponseEntity.ok(orderService.updateOrderStatus(id, status));
	}

	@PutMapping("/{id}/payment")
	public ResponseEntity<Order> updatePaymentStatus(@PathVariable Long id, @RequestParam PaymentStatus status) {
		return ResponseEntity.ok(orderService.updatePaymentStatus(id, status));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
		orderService.deleteOrder(id);
		return ResponseEntity.noContent().build();
	}
}