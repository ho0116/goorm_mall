package com.example.goorm_mall.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.goorm_mall.model.Product;
import com.example.goorm_mall.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
	private final ProductRepository productRepository;
	
	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}
	
	public Product getProductById(Long id) {
		return productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + id));
	}
	
	public Product addProduct(String name, String description, double price, int quantity) {
		Product product = new Product();
		product.setName(name);
		product.setDescription(description);
		product.setPrice(price);
		product.setQuantity(quantity);
		return productRepository.save(product);
	}
	
	public Product updateProduct(Long id, String name, String description, double price, int quantity) {
		Product product = getProductById(id);
		product.setName(name);
		product.setDescription(description);
		product.setPrice(price);
		product.setQuantity(quantity);
		return productRepository.save(product);
	}
	
	public void deleteProduct(Long id) {
		productRepository.deleteById(id);
	}
}
