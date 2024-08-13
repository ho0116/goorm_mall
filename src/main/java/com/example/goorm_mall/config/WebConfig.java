package com.example.goorm_mall.config;

import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;

@ControllerAdvice
public class WebConfig implements WebMvcConfigurer {
    @ModelAttribute
    public void addAttributes(Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            model.addAttribute("username", authentication.getName());
            boolean isAdmin = new ArrayList<>(authentication.getAuthorities())
                    .get(0).getAuthority().equals("ROLE_ADMIN");
            model.addAttribute("isAdmin", isAdmin);
        }
    }
}