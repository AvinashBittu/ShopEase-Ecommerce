package com.shopease.config;

import com.shopease.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

//    @Autowired
//    private CustomUserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                // ✅ Public pages - sab dekh sakte hain bina login ke
//                .requestMatchers("/", "/index", "/products/shop", "/products/**", 
//                                 "/product/**", "/cart/**", "/cart/count",
//                                 "/login", "/register", "/about", "/contact",
//                                 "/css/**", "/js/**", "/images/**", "/static/**").permitAll()
            		.requestMatchers("/**").permitAll()
                
                // ✅ Checkout ke liye login chahiye
                .requestMatchers("/orders/checkout", "/orders/place", "/orders/my-orders", 
                                 "/orders/confirmation/**").authenticated()
                
                // ✅ Admin panel sirf ADMIN role wale ko
                .requestMatchers("/admin/**").hasRole("ADMIN")
                
                // ✅ Baaki sab kuch authenticated chahiye
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/", true)
                .failureUrl("/login?error=true")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .permitAll()
            )
            .csrf(csrf -> csrf.disable());

        return http.build();
    }
}