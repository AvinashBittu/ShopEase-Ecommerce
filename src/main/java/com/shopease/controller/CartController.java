package com.shopease.controller;

import com.shopease.entity.Product;
import com.shopease.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private ProductService productService;

    @SuppressWarnings("unchecked")
    @GetMapping
    public String viewCart(HttpSession session, Model model) {
        Map<Long, Integer> cart = (Map<Long, Integer>) session.getAttribute("cart");
        if (cart == null) {
            cart = new HashMap<>();
        }
        
        Map<Product, Integer> cartItems = new HashMap<>();
        BigDecimal total = BigDecimal.ZERO;
        
        for (Map.Entry<Long, Integer> entry : cart.entrySet()) {
            Product product = productService.getById(entry.getKey()).orElse(null);
            if (product != null) {
                cartItems.put(product, entry.getValue());
                total = total.add(product.getPrice().multiply(BigDecimal.valueOf(entry.getValue())));
            }
        }
        
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("total", total);
        model.addAttribute("cartSize", cart.size());
        
        return "cart";
    }

    @SuppressWarnings("unchecked")
    @PostMapping("/add/{id}")
    public String addToCart(@PathVariable Long id, 
                           @RequestParam(defaultValue = "1") int quantity,
                           HttpSession session) {
        Map<Long, Integer> cart = (Map<Long, Integer>) session.getAttribute("cart");
        if (cart == null) {
            cart = new HashMap<>();
        }
        
        cart.put(id, cart.getOrDefault(id, 0) + quantity);
        session.setAttribute("cart", cart);
        
        return "redirect:/cart";
    }

    @SuppressWarnings("unchecked")
    @PostMapping("/update/{id}")
    public String updateCart(@PathVariable Long id, @RequestParam int quantity, HttpSession session) {
        Map<Long, Integer> cart = (Map<Long, Integer>) session.getAttribute("cart");
        if (cart != null) {
            if (quantity <= 0) {
                cart.remove(id);
            } else {
                cart.put(id, quantity);
            }
            session.setAttribute("cart", cart);
        }
        return "redirect:/cart";
    }

    @SuppressWarnings("unchecked")
    @PostMapping("/remove/{id}")
    public String removeFromCart(@PathVariable Long id, HttpSession session) {
        Map<Long, Integer> cart = (Map<Long, Integer>) session.getAttribute("cart");
        if (cart != null) {
            cart.remove(id);
            session.setAttribute("cart", cart);
        }
        return "redirect:/cart";
    }

    @SuppressWarnings("unchecked")
    @GetMapping("/count")
    @ResponseBody
    public ResponseEntity<Integer> getCartCount(HttpSession session) {
        Map<Long, Integer> cart = (Map<Long, Integer>) session.getAttribute("cart");
        int count = cart != null ? cart.values().stream().mapToInt(Integer::intValue).sum() : 0;
        return ResponseEntity.ok(count);
    }
}