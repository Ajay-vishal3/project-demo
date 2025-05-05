package com.cts.service;

import com.cts.entity.Order;
import com.cts.entity.OrderStatus;
import com.cts.entity.PaymentStatus;
import com.cts.exception.ResourceNotFoundException;
import com.cts.repository.OrderRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class OrderService {
	private final OrderRepository orderRepository;

	public OrderService(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}

	public Order placeOrder(Order order) {
		order.setOrderStatus(OrderStatus.PENDING);
		order.setPaymentStatus(PaymentStatus.PENDING);
		return orderRepository.save(order);
	}

	public List<Order> getAllOrders() {
		return orderRepository.findAll();
	}

	public Order getOrderById(Long id) {
		return orderRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
	}

	public Order updateOrderStatus(Long id, OrderStatus status) {
		Order order = getOrderById(id);
		order.setOrderStatus(status);
		return orderRepository.save(order);
	}

	public Order updatePaymentStatus(Long id, PaymentStatus status) {
		Order order = getOrderById(id);
		order.setPaymentStatus(status);
		return orderRepository.save(order);
	}

	public void deleteOrder(Long id) {
		Order order = getOrderById(id);
		orderRepository.delete(order);
	}
}