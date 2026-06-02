package com.shopease.repository;

import com.shopease.entity.Order;
import com.shopease.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.math.BigDecimal;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

	List<Order> findByUserOrderByCreatedAtDesc(User user);

	List<Order> findAllByOrderByCreatedAtDesc();

	@Query("SELECT COALESCE(SUM(o.totalAmount), 0) FROM Order o")
	BigDecimal getTotalRevenue();

	long countByStatus(Order.Status status);
}