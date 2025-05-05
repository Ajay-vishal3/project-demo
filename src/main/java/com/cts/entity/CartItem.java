package com.cts.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long cartItemId;
	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;
	private int quantity;
	private double totalPrice;
	@OneToOne
	@JoinColumn(name="user_id")
	private User user;
}