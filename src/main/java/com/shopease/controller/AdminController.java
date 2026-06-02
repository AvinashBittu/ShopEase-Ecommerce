package com.shopease.controller;

import com.shopease.entity.Order;
import com.shopease.entity.Product;
import com.shopease.service.OrderService;
import com.shopease.service.ProductService;
import com.shopease.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("totalProducts", productService.getTotalProducts());
        model.addAttribute("totalUsers", userService.getTotalUsers());
        model.addAttribute("totalOrders", orderService.getTotalOrders());
        model.addAttribute("totalRevenue", orderService.getTotalRevenue());
        model.addAttribute("lowStockCount", productService.getLowStockCount());
        
        List<Order> recentOrders = orderService.getAllOrders().stream().limit(5).toList();
        model.addAttribute("recentOrders", recentOrders);
        
        return "admin/dashboard";
    }

    @GetMapping("/products")
    public String manageProducts(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "admin/products";
    }

    @GetMapping("/products/add")
    public String showAddProductForm(Model model) {
        model.addAttribute("product", new Product(null, null, null, null, null, null, null, null, null, null));
        return "admin/add-product";
    }

    @PostMapping("/products/add")
    public String addProduct(@ModelAttribute Product product) {
        productService.save(product);
        return "redirect:/admin/products";
    }

    @GetMapping("/products/edit/{id}")
    public String showEditProductForm(@PathVariable Long id, Model model) {
        Product product = productService.getById(id).orElseThrow();
        model.addAttribute("product", product);
        return "admin/edit-product";
    }

    @PostMapping("/products/edit/{id}")
    public String updateProduct(@PathVariable Long id, @ModelAttribute Product product) {
        product.setId(id);
        productService.save(product);
        return "redirect:/admin/products";
    }

    @GetMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.delete(id);
        return "redirect:/admin/products";
    }

    @GetMapping("/orders")
    public String manageOrders(Model model) {
        model.addAttribute("orders", orderService.getAllOrders());
        return "admin/orders";
    }

    @PostMapping("/orders/{id}/status")
    public String updateOrderStatus(@PathVariable Long id, @RequestParam Order.Status status) {
        orderService.updateStatus(id, status);
        return "redirect:/admin/orders";
    }
}