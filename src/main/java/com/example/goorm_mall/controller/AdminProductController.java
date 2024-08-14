package com.example.goorm_mall.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.goorm_mall.dto.ProductDTO;
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
    public String addProduct(@ModelAttribute ProductDTO productDTO) {
		productService.addProduct(productDTO.getName(), productDTO.getDescription(), productDTO.getPrice(), productDTO.getQuantity(), productDTO.getImages());
        return "redirect:/admin/products";
    }
	
	@GetMapping("/edit/{id}")
	public String editProductForm(@PathVariable Long id, Model model) {
		model.addAttribute("product", productService.getProductById(id));
		return "admin/product/form";
	}
	
	@PostMapping("/edit/{id}")
	public String updateProduct(@PathVariable Long id, @ModelAttribute Product product) {
		productService.updateProduct(id, product.getName(), product.getDescription(), product.getPrice(), product.getQuantity());
		return "redirect:/admin/products";
	}
	
	@PostMapping("/delete/{id}")
	public String deleteProduct(@PathVariable Long id) {
		productService.deleteProduct(id);
		return "redirect:/admin/products";
	}
}
