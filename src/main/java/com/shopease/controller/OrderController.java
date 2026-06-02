package com.shopease.controller;

import com.shopease.entity.User;
import com.shopease.entity.Order;
import com.shopease.service.OrderService;
import com.shopease.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @GetMapping("/checkout")
    public String checkout(HttpSession session, Model model) {
        @SuppressWarnings("unchecked")
        Map<Long, Integer> cart = (Map<Long, Integer>) session.getAttribute("cart");
        if (cart == null || cart.isEmpty()) {
            return "redirect:/cart";
        }
        model.addAttribute("cartSize", cart.size());
        return "checkout";
    }

    @PostMapping("/place")
    public String placeOrder(@RequestParam String address,
                            @AuthenticationPrincipal UserDetails userDetails,
                            HttpSession session) {
        @SuppressWarnings("unchecked")
        Map<Long, Integer> cart = (Map<Long, Integer>) session.getAttribute("cart");
        if (cart == null || cart.isEmpty()) {
            return "redirect:/cart";
        }

        User user = userService.findByEmail(userDetails.getUsername()).orElseThrow();
        Order order = orderService.placeOrder(user, cart, address);
        
        session.removeAttribute("cart");
        return "redirect:/orders/confirmation/" + order.getId();
    }

    @GetMapping("/confirmation/{id}")
    public String confirmation(@PathVariable Long id, Model model) {
        model.addAttribute("orderId", id);
        return "order-confirmation";
    }

    @GetMapping("/my-orders")
    public String myOrders(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = userService.findByEmail(userDetails.getUsername()).orElseThrow();
        List<Order> orders = orderService.getUserOrders(user);
        model.addAttribute("orders", orders);
        return "my-orders";
    }
}