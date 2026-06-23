package com.shopease.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shopease.entity.Order;
import com.shopease.entity.OrderItem;
import com.shopease.entity.Product;
import com.shopease.entity.User;
import com.shopease.repository.OrderRepository;
import com.shopease.repository.ProductRepository;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private ProductRepository productRepository;

	@Transactional
	public Order placeOrder(User user, Map<Long, Integer> cartItems, String address, String landmark, String deliveryInstructions, Double latitude, Double longitude) {
		List<OrderItem> items = new ArrayList<>();
		BigDecimal total = BigDecimal.ZERO;

		Order order = new Order();

		order.setUser(user);
		order.setDeliveryAddress(address);
		order.setStatus(Order.Status.PENDING);
		order.setTotalAmount(BigDecimal.ZERO);
		
	    order.setLandmark(landmark);
	    order.setDeliveryInstructions(deliveryInstructions);
	    
	    order.setLatitude(latitude);
	    order.setLongitude(longitude);
	    
		order = orderRepository.save(order);

		for (Map.Entry<Long, Integer> entry : cartItems.entrySet()) {
			Product product = productRepository.findById(entry.getKey())
					.orElseThrow(() -> new RuntimeException("Product not found"));

			int qty = entry.getValue();
			BigDecimal itemTotal = product.getPrice().multiply(BigDecimal.valueOf(qty));
			total = total.add(itemTotal);

			product.setStock(product.getStock() - qty);
			productRepository.save(product);

			OrderItem item = new OrderItem();

			item.setOrder(order);
			item.setProduct(product);
			item.setQuantity(qty);
			item.setPrice(product.getPrice());
			items.add(item);
		}

		order.setItems(items);
		order.setTotalAmount(total);
		return orderRepository.save(order);
	}

	public List<Order> getUserOrders(User user) {
		return orderRepository.findByUserOrderByCreatedAtDesc(user);
	}

	public List<Order> getAllOrders() {
		return orderRepository.findAllByOrderByCreatedAtDesc();
	}

	public void updateStatus(Long orderId, Order.Status status) {
		Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
		order.setStatus(status);
		orderRepository.save(order);
	}

	public BigDecimal getTotalRevenue() {
		return orderRepository.getTotalRevenue();
	}

	public long getTotalOrders() {
		return orderRepository.count();
	}

	@Transactional
	public void updateOrder(Order order) {
		orderRepository.save(order);
	}
}