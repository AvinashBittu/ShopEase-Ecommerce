package com.shopease.config;


import com.shopease.entity.Product;
import com.shopease.entity.User;
import com.shopease.repository.ProductRepository;
import com.shopease.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

	@Autowired
    private UserRepository userRepository;
	
	@Autowired
    private ProductRepository productRepository;
	
	@Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        
        if (!userRepository.existsByEmail("admin@shopease.com")) {
        	User admin = new User();
        	admin.setName("Admin User");
        	admin.setEmail("admin@shopease.com");
        	admin.setPassword(passwordEncoder.encode("admin123"));
        	admin.setRole(User.Role.ADMIN);
        	admin.setAddress("ShopEase HQ, Mumbai");

        	userRepository.save(admin);
            System.out.println("✅ Admin user created: admin@shopease.com / admin123");
        }

        // Create demo user
        if (!userRepository.existsByEmail("avinashtiwari@gmail.com")) {
        	User user = new User();
        	user.setName("Avinash Tiwari");
        	user.setEmail("avinashtiwari@gmail.com");
        	user.setPassword(passwordEncoder.encode("avinash123"));
        	user.setRole(User.Role.USER);
        	user.setAddress("45 MG Road, Bangalore");

        	userRepository.save(user);
        }

        if (productRepository.count() == 0) {
            List<Product> products = List.of(
                
                new Product(
                    "iPhone 15 Pro",
                    "Apple iPhone 15 Pro with A17 Pro chip, titanium design, and 48MP camera system.",
                    new BigDecimal("129999"),
                    15,
                    "Electronics",
                    "Apple",
                    "https://fdn2.gsmarena.com/vv/bigpic/apple-iphone-15-pro.jpg",
                    4.8,
                    312,
                    true
                ),
                new Product(
                    "Samsung Galaxy S24",
                    "Samsung Galaxy S24 with Snapdragon 8 Gen 3 and 50MP camera.",
                    new BigDecimal("89999"),
                    20,
                    "Electronics",
                    "Samsung",
                    "https://images.unsplash.com/photo-1610945415295-d9bbf067e59c?w=400&q=80",
                    4.6,
                    198,
                    true
                ),
                new Product(
                    "Sony WH-1000XM5 Headphones",
                    "Industry-leading noise cancellation with 30-hour battery life.",
                    new BigDecimal("29999"),
                    30,
                    "Electronics",
                    "Sony",
                    "https://images.unsplash.com/photo-1618366712010-f4ae9c647dcb?w=400&q=80",
                    4.9,
                    542,
                    true
                ),
                new Product(
                    "Dell Inspiron 15 Laptop",
                    "Intel Core i7, 16GB RAM, 512GB SSD, Windows 11.",
                    new BigDecimal("65999"),
                    8,
                    "Electronics",
                    "Dell",
                    "https://images.unsplash.com/photo-1593642632559-0c6d3fc62b89?w=400&q=80",
                    4.4,
                    87,
                    false
                ),
                new Product(
                    "Apple Watch Series 9",
                    "Advanced health monitoring and crash detection.",
                    new BigDecimal("45999"),
                    12,
                    "Electronics",
                    "Apple",
                    "https://images.unsplash.com/photo-1546868871-7041f2a55e12?w=400&q=80",
                    4.7,
                    221,
                    false
                ),
                new Product(
                    "Canon EOS R50 Camera",
                    "24.2MP APS-C mirrorless camera with 4K video.",
                    new BigDecimal("74999"),
                    5,
                    "Electronics",
                    "Canon",
                    "https://images.unsplash.com/photo-1502920917128-1aa500764cbd?w=400&q=80",
                    4.5,
                    63,
                    false
                ),
                // Fashion
                new Product(
                    "Men's Denim Jacket",
                    "Classic denim jacket with modern slim fit.",
                    new BigDecimal("1999"),
                    50,
                    "Fashion",
                    "Levi's",
                    "https://images.unsplash.com/photo-1551537482-f2075a1d41f2?w=400&q=80",
                    4.3,
                    134,
                    true
                ),
                new Product(
                    "Women's Floral Kurti",
                    "Beautiful floral print kurti for casual occasions.",
                    new BigDecimal("899"),
                    75,
                    "Fashion",
                    "FabIndia",
                    "https://images.unsplash.com/photo-1610030469983-98e550d6193c?w=400&q=80",
                    4.5,
                    287,
                    true
                ),
                new Product(
                    "Nike Air Max 270",
                    "Iconic Air Max cushioning for all-day comfort.",
                    new BigDecimal("12999"),
                    22,
                    "Fashion",
                    "Nike",
                    "https://images.unsplash.com/photo-1542291026-7eec264c27ff?w=400&q=80",
                    4.6,
                    456,
                    false
                ),
                new Product(
                    "Women's Leather Handbag",
                    "Premium PU leather handbag with multiple compartments.",
                    new BigDecimal("3499"),
                    18,
                    "Fashion",
                    "Baggit",
                    "https://images.unsplash.com/photo-1548036328-c9fa89d128fa?w=400&q=80",
                    4.4,
                    92,
                    false
                ),
                // Home & Living
                new Product(
                    "Wooden Study Desk",
                    "Solid wood study desk with storage drawers.",
                    new BigDecimal("8999"),
                    6,
                    "Home & Living",
                    "Nilkamal",
                    "https://images.unsplash.com/photo-1518455027359-f3f8164ba6bd?w=400&q=80",
                    4.5,
                    78,
                    true
                ),
                new Product(
                    "Instant Pot Pressure Cooker",
                    "7-in-1 multi-use programmable pressure cooker.",
                    new BigDecimal("6999"),
                    14,
                    "Home & Living",
                    "Instant Pot",
                    "https://images.unsplash.com/photo-1585515320310-259814833e62?w=400&q=80",
                    4.8,
                    512,
                    true
                ),
                new Product(
                    "LED Fairy Lights 10m",
                    "Warm white LED string lights, USB powered.",
                    new BigDecimal("399"),
                    200,
                    "Home & Living",
                    "Syska",
                    "https://images.unsplash.com/photo-1512389098783-66b81f86e199?w=400&q=80",
                    4.4,
                    423,
                    false
                ),
                // Health & Beauty
                new Product(
                    "Minimalist Niacinamide Serum",
                    "10% Niacinamide + 1% Zinc serum for clear skin.",
                    new BigDecimal("599"),
                    80,
                    "Health & Beauty",
                    "Minimalist",
                    "https://images.unsplash.com/photo-1556228578-8c89e6adf883?w=400&q=80",
                    4.7,
                    891,
                    true
                ),
                new Product(
                    "Whey Protein Chocolate 1kg",
                    "25g protein per serving, chocolate flavour.",
                    new BigDecimal("2499"),
                    25,
                    "Health & Beauty",
                    "MuscleBlaze",
                    "https://images.unsplash.com/photo-1593095948071-474c5cc2989d?w=400&q=80",
                    4.5,
                    342,
                    true
                ),
                new Product(
                    "Yoga Mat Anti-Slip 6mm",
                    "Premium TPE anti-slip yoga mat, 183cm x 61cm.",
                    new BigDecimal("999"),
                    45,
                    "Health & Beauty",
                    "Boldfit",
                    "https://images.unsplash.com/photo-1592432678016-e910b452f9a2?w=400",
                    4.6,
                    234,
                    false
                ),
                // Books
                new Product(
                    "Let Us C by Yashavant Kanetkar",
                    "Most popular C programming book. 17th Edition.",
                    new BigDecimal("349"),
                    100,
                    "Books & Education",
                    "BPB Publications",
                    "https://images.unsplash.com/photo-1532012197267-da84d127e765?w=400&q=80",
                    4.8,
                    1203,
                    true
                ),
                new Product(
                    "Atomic Habits by James Clear",
                    "An Easy & Proven Way to Build Good Habits.",
                    new BigDecimal("499"),
                    60,
                    "Books & Education",
                    "Random House",
                    "https://images.unsplash.com/photo-1544947950-fa07a98d237f?w=400&q=80",
                    4.9,
                    2341,
                    true
                ),
                // Sports
                new Product(
                    "Cricket Bat Kashmir Willow",
                    "Full size Kashmir Willow bat. Weight 1.1-1.2kg.",
                    new BigDecimal("1499"),
                    20,
                    "Sports & Fitness",
                    "MRF",
//                    "https://images.unsplash.com/photo-1540747913346-19212a4b5b4e?w=400&q=80",
                    "/images/cricket-bat.jpg",
                    4.4,
                    178,
                    true
                ),
                new Product(
                    "Resistance Bands Set (5 pcs)",
                    "Set of 5 resistance bands for home workouts.",
                    new BigDecimal("799"),
                    55,
                    "Sports & Fitness",
                    "Boldfit",
                    "https://images.unsplash.com/photo-1598289431512-b97b0917affc?w=400&q=80",
                    4.6,
                    312,
                    true
                )
            );
            productRepository.saveAll(products);
            System.out.println("✅ " + products.size() + " products seeded!");
        }
    }
}
