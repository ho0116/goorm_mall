package com.example.goorm_mall.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.goorm_mall.model.Member;
import com.example.goorm_mall.model.Product;
import com.example.goorm_mall.model.ProductLike;
import com.example.goorm_mall.service.ProductService;

import jakarta.servlet.http.HttpSession;

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

        // 가격을 int로 변환한 새로운 리스트를 생성
        List<Map<String, Object>> newProducts = new ArrayList<>();
        for (Product product : products) {
            Map<String, Object> productMap = new HashMap<>();
            productMap.put("id", product.getId());
            productMap.put("name", product.getName());
            productMap.put("description", product.getDescription());
            productMap.put("price", (int) product.getPrice());  // double에서 int로 변환
            productMap.put("quantity", product.getQuantity());
            productMap.put("images", product.getImages());

            newProducts.add(productMap);
        }

        model.addAttribute("products", newProducts);
        
        return "product/list";
    }

    @GetMapping("/{id}")
    public String viewProduct(@PathVariable Long id, Model model, Authentication authentication, HttpSession session) {
        Product product = productService.updateViewCount(id, session);
        int price =(int) product.getPrice();
        model.addAttribute("product", product);
        model.addAttribute("price", price);
        if (authentication != null) {        	
        	boolean isLiked = product.getLikes().stream().map(ProductLike::getMember)
        			.map(Member::getUsername)
        			.anyMatch((el) -> el.equals(authentication.getName()));
        	model.addAttribute("isLiked", isLiked);
        }
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
        // 가격을 int로 변환한 새로운 리스트를 생성
        List<Map<String, Object>> newProducts = new ArrayList<>();
        for (Product product : likedProducts) {
            Map<String, Object> productMap = new HashMap<>();
            productMap.put("id", product.getId());
            productMap.put("name", product.getName());
            productMap.put("description", product.getDescription());
            productMap.put("price", (int) product.getPrice());  // double에서 int로 변환
            productMap.put("quantity", product.getQuantity());
            productMap.put("images", product.getImages());

            newProducts.add(productMap);
        }
    	model.addAttribute("likedProducts", newProducts);
    	
    	return "product/liked";
    }
    
    @PostMapping("/{id}/comment")
    public String addComment(@PathVariable Long id, @RequestParam String content,
                             @AuthenticationPrincipal UserDetails userDetails) {
        productService.addComment(id, userDetails.getUsername(), content);
        return "redirect:/products/" + id;
    }
}
