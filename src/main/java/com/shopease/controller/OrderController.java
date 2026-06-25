package com.shopease.controller;



import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.json.JSONObject;
import com.razorpay.RazorpayClient;

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
	
	@Autowired
	private RazorpayClient razorpayClient;
	
	@Value("${razorpay.key}")
	private String razorpayKey;

	@GetMapping("/checkout")
	public String checkout(HttpSession session, Model model) {
		@SuppressWarnings("unchecked")
		Map<Long, Integer> cart = (Map<Long, Integer>) session.getAttribute("cart");
		if (cart == null || cart.isEmpty()) {
			return "redirect:/cart";
		}

		
		BigDecimal total = BigDecimal.ZERO;
		for (Map.Entry<Long, Integer> entry : cart.entrySet()) {
			Product product = productService.getById(entry.getKey()).orElse(null);
			if (product != null) {
				total = total.add(product.getPrice().multiply(BigDecimal.valueOf(entry.getValue())));
			}
		}

		model.addAttribute("cartSize", cart.size());
		model.addAttribute("total", total); 

		return "checkout";
	}

	@PostMapping("/place")
	public String placeOrder(@RequestParam String address, @RequestParam(required = false) String landmark,
			@RequestParam(required = false) String deliveryInstructions,
			@RequestParam(required = false) Double latitude, @RequestParam(required = false) Double longitude,
			@RequestParam(required = false) String paymentMethod,
			@AuthenticationPrincipal UserDetails userDetails, HttpSession session, Model model) {
		
		 System.out.println("========== ALL PARAMETERS ==========");
		    System.out.println("address: " + address);
		    System.out.println("landmark: " + landmark);
		    System.out.println("deliveryInstructions: " + deliveryInstructions);
		    System.out.println("latitude: " + latitude);
		    System.out.println("longitude: " + longitude);
		    System.out.println("paymentMethod: " + paymentMethod);
		    System.out.println("user: " + userDetails.getUsername());
		    System.out.println("====================================");
		
		@SuppressWarnings("unchecked")
		Map<Long, Integer> cart = (Map<Long, Integer>) session.getAttribute("cart");
		if (cart == null || cart.isEmpty()) {
			return "redirect:/cart";
		}

		User user = userService.findByEmail(userDetails.getUsername()).orElseThrow();
		
		   // Calculate total
	    BigDecimal total = BigDecimal.ZERO;
	    for (Map.Entry<Long, Integer> entry : cart.entrySet()) {
	        Product product = productService.getById(entry.getKey()).orElse(null);
	        if (product != null) {
	            total = total.add(product.getPrice().multiply(BigDecimal.valueOf(entry.getValue())));
	        }
	    }
	    
	    System.out.println("Total Amount: " + total);
	    
	    // Online Payment
	    if ("ONLINE".equals(paymentMethod)) {
	    	System.out.println("=== ONLINE PAYMENT SELECTED ===");
	        try {
	            // Create Razorpay Order
	            int amountInPaise = total.multiply(new BigDecimal("100")).intValue();
	            JSONObject orderRequest = new JSONObject();
	            orderRequest.put("amount", amountInPaise);
	            orderRequest.put("currency", "INR");
	            orderRequest.put("receipt", "order_" + System.currentTimeMillis());

	            com.razorpay.Order razorpayOrder = razorpayClient.orders.create(orderRequest);
	            String razorpayOrderId  = razorpayOrder.get("id");
	            
	            System.out.println("Razorpay Order ID: " + razorpayOrderId);

	            // Save order in database (pending payment)
	            Order order = orderService.placeOrder(user, cart, address, landmark, deliveryInstructions, latitude, longitude);
	            

	            model.addAttribute("razorpayOrderId", razorpayOrderId);
	            model.addAttribute("razorpayKey", razorpayKey);
	            model.addAttribute("total", total);
	            model.addAttribute("orderId", order.getId());

	            return "payment";
	        } catch (Exception e) {
	        	System.out.println("Payment Error: " + e.getMessage()); 
	            model.addAttribute("error", "Payment initialization failed: " + e.getMessage());
	            return "checkout";
	        }
	    }
	    
	    
	    // Cash on Delivery
	    System.out.println("=== COD SELECTED ===");
		Order order = orderService.placeOrder(user, cart, address, landmark, deliveryInstructions, latitude, longitude);

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