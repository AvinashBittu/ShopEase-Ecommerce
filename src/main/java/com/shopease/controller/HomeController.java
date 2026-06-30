package com.shopease.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.shopease.entity.Product;
import com.shopease.service.ProductService;

@Controller
public class HomeController {

	@Autowired
	private ProductService productService;

	@GetMapping("/")
	public String home(Model model) {
		// Get featured products - with null safety
		List<Product> featuredProducts = productService.getFeatured();
		if (featuredProducts == null) {
			featuredProducts = new ArrayList<>();
		}
		model.addAttribute("featuredProducts", featuredProducts);

		// Categories
		List<Map<String, String>> categories = new ArrayList<>();
		categories.add(Map.of("name", "Electronics", "icon", "mobile-alt"));
		categories.add(Map.of("name", "Fashion", "icon", "tshirt"));
		categories.add(Map.of("name", "Home & Living", "icon", "home"));
		categories.add(Map.of("name", "Health & Beauty", "icon", "heartbeat"));
		categories.add(Map.of("name", "Books & Education", "icon", "book"));
		categories.add(Map.of("name", "Sports & Fitness", "icon", "futbol"));
		model.addAttribute("categories", categories);

		// Debug print
		System.out.println("=== HomeController ===");
		System.out.println("Featured products count: " + featuredProducts.size());
		System.out.println("Categories count: " + categories.size());

		return "index";
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}

//	@GetMapping("/register")
//	public String register() {
//		return "register";
//	}

	@GetMapping("/about")
	public String about() {
		return "about";
	}
	
	@GetMapping("/contact")
	public String showContactForm() {
	    return "contact";
	}

	@PostMapping("/contact")
	public String submitContact(@RequestParam String name, @RequestParam String email, @RequestParam String subject,
			@RequestParam String message, Model model) {
		// Yahan tum email send kar sakte ho ya database mein save
		System.out.println("Contact Form Submitted:");
		System.out.println("Name: " + name);
		System.out.println("Email: " + email);
		System.out.println("Subject: " + subject);
		System.out.println("Message: " + message);

		model.addAttribute("successMessage", "Thank you for contacting us! We'll get back to you soon.");
		return "contact";
	}
}