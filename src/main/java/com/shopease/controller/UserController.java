package com.shopease.controller;

import com.shopease.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public String registerUser(@RequestParam String name,
                              @RequestParam String email,
                              @RequestParam String password,
                              @RequestParam(required = false) String address) {
        try {
            userService.register(name, email, password);
            return "redirect:/login?registered=true";
        } catch (Exception e) {
            return "redirect:/register?error=true";
        }
    }
}