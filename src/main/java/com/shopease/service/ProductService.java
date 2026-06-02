package com.shopease.service;


import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopease.entity.Product;
import com.shopease.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getById(Long id) {
        return productRepository.findById(id);
    }

    public List<Product> getFeatured() {
        return productRepository.findByFeaturedTrue();
    }

    public List<Product> filterProducts(String category, String search, BigDecimal maxPrice) {
        return productRepository.filterProducts(
            (category != null && !category.isEmpty()) ? category : null,
            (search != null && !search.isEmpty()) ? search : null,
            maxPrice
        );
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    public long getTotalProducts() {
        return productRepository.count();
    }

    public long getLowStockCount() {
        return productRepository.findByStockLessThan(10).size();
    }

    public List<Product> getByCategory(String category) {
        return productRepository.findByCategory(category);
    }
}