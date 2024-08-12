package com.example.goorm_mall.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.goorm_mall.model.Product;
import com.example.goorm_mall.service.ProductService;

@Controller
@RequestMapping("/admin/products")
public class AdminProductController {
	private final ProductService productService;
	
	public AdminProductController(ProductService productService) {
		this.productService = productService;
	}
	
	@GetMapping
    public String listSnacks(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "admin/product/list";
    }
	
	@GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("product", new Product());
        return "admin/product/form";
    }
	
	@PostMapping("/add")
    public String addSnack(@ModelAttribute Product product) {
        productService.addProduct(product.getName(), product.getDescription(), product.getPrice(), product.getQuantity());
        return "redirect:/admin/products";
    }
}
