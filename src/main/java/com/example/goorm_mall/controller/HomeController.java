package com.example.goorm_mall.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.goorm_mall.model.Product;
import com.example.goorm_mall.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    private final ProductService productService;

    public HomeController(ProductService productService) {
        this.productService = productService;
    }
    @GetMapping("/")
    public String home(Model model) {
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
        
        return "home";
    }
}