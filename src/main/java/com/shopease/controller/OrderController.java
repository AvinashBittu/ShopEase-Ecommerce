package com.shopease.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.shopease.entity.Order;
import com.shopease.entity.Product;
import com.shopease.entity.User;
import com.shopease.service.OrderService;
import com.shopease.service.ProductService;
import com.shopease.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/orders")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@Autowired
	private UserService userService;

	@Autowired
	private ProductService productService;

//    @GetMapping("/checkout")
//    public String checkout(HttpSession session, Model model) {
//        @SuppressWarnings("unchecked")
//        Map<Long, Integer> cart = (Map<Long, Integer>) session.getAttribute("cart");
//        if (cart == null || cart.isEmpty()) {
//            return "redirect:/cart";
//        }
//        model.addAttribute("cartSize", cart.size());
//        return "checkout";
//    }

	@GetMapping("/checkout")
	public String checkout(HttpSession session, Model model) {
		@SuppressWarnings("unchecked")
		Map<Long, Integer> cart = (Map<Long, Integer>) session.getAttribute("cart");
		if (cart == null || cart.isEmpty()) {
			return "redirect:/cart";
		}

		// ✅ Calculate total
		BigDecimal total = BigDecimal.ZERO;
		for (Map.Entry<Long, Integer> entry : cart.entrySet()) {
			Product product = productService.getById(entry.getKey()).orElse(null);
			if (product != null) {
				total = total.add(product.getPrice().multiply(BigDecimal.valueOf(entry.getValue())));
			}
		}

		model.addAttribute("cartSize", cart.size());
		model.addAttribute("total", total); // ✅ Yeh important hai!

		return "checkout";
	}

	@PostMapping("/place")
	public String placeOrder(@RequestParam String address, @AuthenticationPrincipal UserDetails userDetails,
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