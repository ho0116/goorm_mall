package com.example.goorm_mall.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.goorm_mall.model.Product;
import com.example.goorm_mall.service.ProductService;

@Controller
@RequestMapping("/products")
public class ProductController {
	private final ProductService productService;
	
	public ProductController(ProductService productService) {
		this.productService = productService;
	}
	
	@GetMapping
	public String listProducts(Model model) {
		List<Product> products = productService.getAllProducts();
		model.addAttribute("products", products);
		
		return "product/list";
	}
	
	@GetMapping("/{id}")
	public String viewProduct(@PathVariable Long id, Model model, Authentication authentication) {
		// Product product = productService.updateViewCount(id);
		// model.addAttribute("product", product);
		
		return "snack/view";
	}
	
}
