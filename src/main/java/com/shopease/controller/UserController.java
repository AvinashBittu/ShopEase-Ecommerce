//package com.shopease.controller;
//
//import com.shopease.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//@Controller
//public class UserController {
//
//    @Autowired
//    private UserService userService;
//
//    @PostMapping("/register")
//    public String registerUser(@RequestParam String name,
//                              @RequestParam String email,
//                              @RequestParam String password,
//                              @RequestParam(required = false) String address) {
//        try {
//            userService.register(name, email, password);
//            return "redirect:/login?registered=true";
//        } catch (Exception e) {
//            return "redirect:/register?error=true";
//        }
//    }
//}

package com.shopease.controller;

import com.shopease.entity.User;
import com.shopease.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid User user, 
                               BindingResult result,
                               Model model) {
       
        if (result.hasErrors()) {
            model.addAttribute("errors", result.getAllErrors());
            return "register";
        }

        
        if (userService.findByEmail(user.getEmail()).isPresent()) {
            model.addAttribute("error", "Email already registered!");
            return "register";
        }

       
        try {
            userService.register(user.getName(), user.getEmail(), user.getPassword());
            return "redirect:/login?registered=true";
        } catch (Exception e) {
            model.addAttribute("error", "Registration failed: " + e.getMessage());
            return "register";
        }
    }
}