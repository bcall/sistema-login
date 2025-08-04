package com.example.sistemalogin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {
    
    @GetMapping("/")
    public String home() {
        return "redirect:/login.html";
    }
    
    @GetMapping("/login")
    public String loginPage() {
        return "redirect:/login.html";
    }
    
    @GetMapping("/register")
    public String registerPage() {
        return "redirect:/register.html";
    }
} 