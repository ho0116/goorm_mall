package com.example.goorm_mall.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.goorm_mall.model.Payment;
import com.example.goorm_mall.model.Product;
import com.example.goorm_mall.service.MemberService;
import com.example.goorm_mall.service.PaymentService;
import com.example.goorm_mall.service.ProductService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final ProductService productService;
    private final PaymentService paymentService;

    @GetMapping("/join")
    public String joinForm() {
        return "join";
    }

    @PostMapping("/join")
    public String join(@RequestParam String username, @RequestParam String name,
                       @RequestParam String email, @RequestParam String password, @RequestParam String rePassword) {
        try {
            memberService.join(username, name, email, password, rePassword);
            return "redirect:/login";
        } catch (IllegalStateException e) {
            return "redirect:/join?error";
        }
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }
    
    @GetMapping("/info")
    public String info(Model model, @AuthenticationPrincipal UserDetails userDetails) {
    	
    	// 유저가 좋아요한 상품 목록을 가져온다.
    	List<Product> likedProducts = productService.getLikedProductsByUsername(userDetails.getUsername());
    	
    	model.addAttribute("likedProducts", likedProducts);
    	
    	// 유저가 결제한 상품 목록을 가져온다.
    	List<Payment> completePayments = paymentService.getCompletedPaymentsByUsername(userDetails.getUsername());
    	
    	model.addAttribute("completePayments", completePayments);
    	
    	return "info";
    }
}