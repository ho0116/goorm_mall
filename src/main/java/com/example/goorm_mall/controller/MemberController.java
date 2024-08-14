package com.example.goorm_mall.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.example.goorm_mall.service.MemberService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

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
}