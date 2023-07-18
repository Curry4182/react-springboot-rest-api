package com.example.gccoffee.service;

import java.util.List;

import com.example.gccoffee.model.Category;
import com.example.gccoffee.model.Product;

public interface ProductService {
	List<Product> getProductsByCategory(Category category);
	List<Product> getAllProducts();
	Product createdProduct(String productName, Category category, long price);
	Product createdProduct(String productName, Category category, Long price, String description);
}
