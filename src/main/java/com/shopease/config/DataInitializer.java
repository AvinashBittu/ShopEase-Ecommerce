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
        // Create admin if not exists
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

        // Seed products if empty
//        if (productRepository.count() == 0) {
//            List<Product> products = List.of(
//                // Electronics
//                Product.builder().name("iPhone 15 Pro").category("Electronics").brand("Apple").price(new BigDecimal("129999")).stock(15).rating(4.8).reviewCount(312).imageUrl("https://images.unsplash.com/photo-1695048133142-1a20484429be?w=400&q=80").description("Apple iPhone 15 Pro with A17 Pro chip, titanium design, and 48MP camera system.").featured(true).build(),
//                Product.builder().name("Samsung Galaxy S24").category("Electronics").brand("Samsung").price(new BigDecimal("89999")).stock(20).rating(4.6).reviewCount(198).imageUrl("https://images.unsplash.com/photo-1610945415295-d9bbf067e59c?w=400&q=80").description("Samsung Galaxy S24 with Snapdragon 8 Gen 3 and 50MP camera.").featured(true).build(),
//                Product.builder().name("Sony WH-1000XM5 Headphones").category("Electronics").brand("Sony").price(new BigDecimal("29999")).stock(30).rating(4.9).reviewCount(542).imageUrl("https://images.unsplash.com/photo-1618366712010-f4ae9c647dcb?w=400&q=80").description("Industry-leading noise cancellation with 30-hour battery life.").featured(true).build(),
//                Product.builder().name("Dell Inspiron 15 Laptop").category("Electronics").brand("Dell").price(new BigDecimal("65999")).stock(8).rating(4.4).reviewCount(87).imageUrl("https://images.unsplash.com/photo-1593642632559-0c6d3fc62b89?w=400&q=80").description("Intel Core i7, 16GB RAM, 512GB SSD, Windows 11.").featured(false).build(),
//                Product.builder().name("Apple Watch Series 9").category("Electronics").brand("Apple").price(new BigDecimal("45999")).stock(12).rating(4.7).reviewCount(221).imageUrl("https://images.unsplash.com/photo-1546868871-7041f2a55e12?w=400&q=80").description("Advanced health monitoring and crash detection.").featured(false).build(),
//                Product.builder().name("Canon EOS R50 Camera").category("Electronics").brand("Canon").price(new BigDecimal("74999")).stock(5).rating(4.5).reviewCount(63).imageUrl("https://images.unsplash.com/photo-1502920917128-1aa500764cbd?w=400&q=80").description("24.2MP APS-C mirrorless camera with 4K video.").featured(false).build(),
//                // Fashion
//                Product.builder().name("Men's Denim Jacket").category("Fashion").brand("Levi's").price(new BigDecimal("1999")).stock(50).rating(4.3).reviewCount(134).imageUrl("https://images.unsplash.com/photo-1551537482-f2075a1d41f2?w=400&q=80").description("Classic denim jacket with modern slim fit.").featured(true).build(),
//                Product.builder().name("Women's Floral Kurti").category("Fashion").brand("FabIndia").price(new BigDecimal("899")).stock(75).rating(4.5).reviewCount(287).imageUrl("https://images.unsplash.com/photo-1610030469983-98e550d6193c?w=400&q=80").description("Beautiful floral print kurti for casual occasions.").featured(true).build(),
//                Product.builder().name("Nike Air Max 270").category("Fashion").brand("Nike").price(new BigDecimal("12999")).stock(22).rating(4.6).reviewCount(456).imageUrl("https://images.unsplash.com/photo-1542291026-7eec264c27ff?w=400&q=80").description("Iconic Air Max cushioning for all-day comfort.").featured(false).build(),
//                Product.builder().name("Women's Leather Handbag").category("Fashion").brand("Baggit").price(new BigDecimal("3499")).stock(18).rating(4.4).reviewCount(92).imageUrl("https://images.unsplash.com/photo-1548036328-c9fa89d128fa?w=400&q=80").description("Premium PU leather handbag with multiple compartments.").featured(false).build(),
//                // Home & Living
//                Product.builder().name("Wooden Study Desk").category("Home & Living").brand("Nilkamal").price(new BigDecimal("8999")).stock(6).rating(4.5).reviewCount(78).imageUrl("https://images.unsplash.com/photo-1518455027359-f3f8164ba6bd?w=400&q=80").description("Solid wood study desk with storage drawers.").featured(true).build(),
//                Product.builder().name("Instant Pot Pressure Cooker").category("Home & Living").brand("Instant Pot").price(new BigDecimal("6999")).stock(14).rating(4.8).reviewCount(512).imageUrl("https://images.unsplash.com/photo-1585515320310-259814833e62?w=400&q=80").description("7-in-1 multi-use programmable pressure cooker.").featured(true).build(),
//                Product.builder().name("LED Fairy Lights 10m").category("Home & Living").brand("Syska").price(new BigDecimal("399")).stock(200).rating(4.4).reviewCount(423).imageUrl("https://images.unsplash.com/photo-1512389098783-66b81f86e199?w=400&q=80").description("Warm white LED string lights, USB powered.").featured(false).build(),
//                // Health & Beauty
//                Product.builder().name("Minimalist Niacinamide Serum").category("Health & Beauty").brand("Minimalist").price(new BigDecimal("599")).stock(80).rating(4.7).reviewCount(891).imageUrl("https://images.unsplash.com/photo-1556228578-8c89e6adf883?w=400&q=80").description("10% Niacinamide + 1% Zinc serum for clear skin.").featured(true).build(),
//                Product.builder().name("Whey Protein Chocolate 1kg").category("Health & Beauty").brand("MuscleBlaze").price(new BigDecimal("2499")).stock(25).rating(4.5).reviewCount(342).imageUrl("https://images.unsplash.com/photo-1593095948071-474c5cc2989d?w=400&q=80").description("25g protein per serving, chocolate flavour.").featured(true).build(),
//                Product.builder().name("Yoga Mat Anti-Slip 6mm").category("Health & Beauty").brand("Boldfit").price(new BigDecimal("999")).stock(45).rating(4.6).reviewCount(234).imageUrl("https://images.unsplash.com/photo-1601925228278-23f85e58a168?w=400&q=80").description("Premium TPE anti-slip yoga mat, 183cm x 61cm.").featured(false).build(),
//                // Books
//                Product.builder().name("Let Us C by Yashavant Kanetkar").category("Books & Education").brand("BPB Publications").price(new BigDecimal("349")).stock(100).rating(4.8).reviewCount(1203).imageUrl("https://images.unsplash.com/photo-1532012197267-da84d127e765?w=400&q=80").description("Most popular C programming book. 17th Edition.").featured(true).build(),
//                Product.builder().name("Atomic Habits by James Clear").category("Books & Education").brand("Random House").price(new BigDecimal("499")).stock(60).rating(4.9).reviewCount(2341).imageUrl("https://images.unsplash.com/photo-1544947950-fa07a98d237f?w=400&q=80").description("An Easy & Proven Way to Build Good Habits.").featured(true).build(),
//                // Sports
//                Product.builder().name("Cricket Bat Kashmir Willow").category("Sports & Fitness").brand("SS").price(new BigDecimal("1499")).stock(20).rating(4.4).reviewCount(178).imageUrl("https://images.unsplash.com/photo-1540747913346-19212a4b5b4e?w=400&q=80").description("Full size Kashmir Willow bat. Weight 1.1-1.2kg.").featured(true).build(),
//                Product.builder().name("Resistance Bands Set (5 pcs)").category("Sports & Fitness").brand("Boldfit").price(new BigDecimal("799")).stock(55).rating(4.6).reviewCount(312).imageUrl("https://images.unsplash.com/photo-1598289431512-b97b0917affc?w=400&q=80").description("Set of 5 resistance bands for home workouts.").featured(true).build()
//            );
//            productRepository.saveAll(products);
//            System.out.println("✅ " + products.size() + " products seeded!");
//        }
        if (productRepository.count() == 0) {
        	//productRepository.deleteAll();
            List<Product> products = List.of(
                // Electronics
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
                    "SS",
                    "https://images.unsplash.com/photo-1540747913346-19212a4b5b4e?w=400&q=80",
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
