package com.cts.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;
	private String name;
	@Column(unique = true)
	private String email;
	private String password;
	private String shippingAddress;
	private String paymentDetails;
//	@OneToOne(mappedBy="user",cascade=CascadeType.ALL)
//	private CartItem cartItem;
}
