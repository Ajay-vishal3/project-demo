package com.cts.service;

import com.cts.entity.Product;
import com.cts.exception.ResourceNotFoundException;
import com.cts.repository.ProductRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductService {
	private final ProductRepository productRepository;

	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	public Product createProduct(Product product) {
		return productRepository.save(product);
	}

	public Product getProductById(Long id) {
		return productRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
	}

	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}

	public Product updateProduct(Long id, Product product) {
		Product existing = getProductById(id);
		existing.setName(product.getName());
		existing.setDescription(product.getDescription());
		existing.setPrice(product.getPrice());
		existing.setCategory(product.getCategory());
		existing.setImageUrl(product.getImageUrl());
		return productRepository.save(existing);
	}

	public void deleteProduct(Long id) {
		Product existing = getProductById(id);
		productRepository.delete(existing);
	}
}