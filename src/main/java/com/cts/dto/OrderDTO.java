package com.cts.dto;

import java.util.List;

public class OrderDTO {
    private String username;
    private List<ProductItemDTO> products;
    public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public List<ProductItemDTO> getProducts() {
		return products;
	}
	public void setProducts(List<ProductItemDTO> products) {
		this.products = products;
	}
	public double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	private double totalPrice;
    private String orderStatus;
    private String paymentStatus;

    // Getters and Setters
}