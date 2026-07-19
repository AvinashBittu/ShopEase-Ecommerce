package com.shopease.controller;

import com.shopease.entity.Product;
import com.shopease.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/products")
public class ProductController {

	@Autowired
	private ProductService productService;

	@GetMapping("/shop")
	public String shop(@RequestParam(required = false) String category, @RequestParam(required = false) String search,
			@RequestParam(required = false) BigDecimal maxPrice, Model model) {

		List<Product> products = productService.filterProducts(category, search, maxPrice);
		model.addAttribute("products", products);
		model.addAttribute("selectedCategory", category);
		model.addAttribute("searchKeyword", search);

		List<String> categories = List.of("Electronics", "Fashion", "Home & Living", "Health & Beauty",
				"Books & Education", "Sports & Fitness");
		model.addAttribute("categories", categories);

		return "shop";
	}

	@GetMapping("/{id}")
	public String productDetail(@PathVariable Long id, Model model) {
		Optional<Product> productOpt = productService.getById(id);
		if (productOpt.isPresent()) {
			model.addAttribute("product", productOpt.get());

			List<Product> relatedProducts = productService.getByCategory(productOpt.get().getCategory());
			relatedProducts = relatedProducts.stream().filter(p -> !p.getId().equals(id)).limit(4).toList();
			model.addAttribute("relatedProducts", relatedProducts);

			return "product-detail";
		}
		return "redirect:/products/shop";
	}
}