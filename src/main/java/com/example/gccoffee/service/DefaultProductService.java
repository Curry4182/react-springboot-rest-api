package com.example.gccoffee.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.example.gccoffee.model.Category;
import com.example.gccoffee.model.Product;
import com.example.gccoffee.repository.ProductRepository;

public class DefaultProductService implements ProductService{

	private final ProductRepository productRepository;

	public DefaultProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	@Override
	public List<Product> getProductsByCategory(Category category) {
		return productRepository.findByCategory(category);
	}

	@Override
	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}

	@Override
	public Product createdProduct(String productName, Category category, long price) {
		return productRepository.insert(new Product(UUID.randomUUID(), productName, category, price));
	}

	@Override
	public Product createdProduct(String productName, Category category, Long price, String description) {
		var product = new Product(UUID.randomUUID(), productName, category, price, description, LocalDateTime.now(), LocalDateTime.now());
		return productRepository.insert(product);
	}
}
