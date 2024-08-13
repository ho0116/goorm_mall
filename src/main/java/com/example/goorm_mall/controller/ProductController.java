package com.example.goorm_mall.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.goorm_mall.model.Member;
import com.example.goorm_mall.model.Product;
import com.example.goorm_mall.model.ProductLike;
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
        Product product = productService.updateViewCount(id);
        model.addAttribute("product", product);
        boolean isLiked = product.getLikes().stream().map(ProductLike::getMember)
                .map(Member::getUsername)
                .anyMatch((el) -> el.equals(authentication.getName()));
        model.addAttribute("isLiked", isLiked);
        return "product/view";
    }

    @PostMapping("/{id}/like")
    public String addLike(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        productService.addLike(id, userDetails.getUsername());
        return "redirect:/products/" + id;
    }

    @PostMapping("/{id}/unlike")
    public String removeLike(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        productService.removeLike(id, userDetails.getUsername());
        return "redirect:/products/" + id;
    }
    
    @GetMapping("/liked")
    public String likedProducts(Model model, @AuthenticationPrincipal UserDetails userDetails) {
    	List<Product> likedProducts = productService.getLikedProductsByUsername(userDetails.getUsername());
    	model.addAttribute("likedProducts", likedProducts);
    	return "product/liked";
    }
}
