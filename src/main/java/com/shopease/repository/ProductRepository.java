package com.shopease.repository;

import com.shopease.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

	List<Product> findByCategory(String category);

	List<Product> findByFeaturedTrue();

	List<Product> findByCategoryOrderByPriceAsc(String category);

	@Query("SELECT p FROM Product p WHERE " + "(:category IS NULL OR p.category = :category) AND "
			+ "(:search IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :search, '%')) OR LOWER(p.brand) LIKE LOWER(CONCAT('%', :search, '%'))) AND "
			+ "(:maxPrice IS NULL OR p.price <= :maxPrice)")
	List<Product> filterProducts(@Param("category") String category, @Param("search") String search,
			@Param("maxPrice") BigDecimal maxPrice);

	List<Product> findByStockLessThan(int stock);

	long countByCategory(String category);
}