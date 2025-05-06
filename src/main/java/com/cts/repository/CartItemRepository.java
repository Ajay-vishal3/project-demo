package com.cts.repository;

import com.cts.entity.CartItem;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
	List<CartItem> findByUserUserId(Long userId);
}