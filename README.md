# 🛍️ ShopEase - Complete E-Commerce Platform

![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.5-green)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue)
![License](https://img.shields.io/badge/License-MIT-yellow)

## 📌 Project Overview

**ShopEase** is a fully functional monolithic e-commerce web application developed as a **BCA Final Year Project**. It provides a seamless online shopping experience with user authentication, product browsing, cart management, order placement, and an admin dashboard.

### 🎯 Key Features

| Feature | Description |
|---------|-------------|
| 🔐 **User Authentication** | Spring Security with BCrypt password encoding |
| 🛒 **Shopping Cart** | Session-based cart with quantity management |
| 📦 **Order Management** | Place orders, view history, track status |
| 👨‍💼 **Admin Panel** | Complete CRUD for products and orders |
| 🔍 **Search & Filter** | Filter by category, price, and keyword |
| 📱 **Responsive UI** | Bootstrap 5 + jQuery, works on all devices |

### 📊 Product Categories

- Electronics (6 products)
- Fashion (4 products)
- Home & Living (3 products)
- Health & Beauty (3 products)
- Books & Education (2 products)
- Sports & Fitness (2 products)

## 🛠️ Technology Stack

| Layer | Technology |
|-------|------------|
| Backend | Java 21, Spring Boot 3.2.5 |
| Security | Spring Security 6, BCrypt |
| Database | MySQL 8.0 |
| Frontend | Thymeleaf, Bootstrap 5, jQuery |
| Build Tool | Maven |

## 📁 Project Structure

ShopEase/
├── src/main/java/com/shopease/
│ ├── config/ # Security & Data Initializer
│ ├── controller/ # MVC Controllers (6 files)
│ ├── entity/ # JPA Entities (4 files)
│ ├── repository/ # Spring Data JPA (3 files)
│ └── service/ # Business Logic (4 files)
├── src/main/resources/
│ ├── templates/ # Thymeleaf HTML (15+ files)
│ ├── static/ # CSS, JS, Images
│ └── application.properties
└── pom.xml


## 🚀 Installation & Setup Guide

### Prerequisites

- Java 21 (JDK)
- MySQL 8.0
- Spring Tool Suite (STS) or Eclipse
- Maven (built-in with STS)

### Step-by-Step Setup

#### 1️⃣ Clone the Repository

```bash
git clone https://github.com/AvinashBittu/ShopEase-Ecommerce.git
cd ShopEase-Ecommerce

2️⃣ Configure MySQL Database
CREATE DATABASE shopease_db;

3️⃣ Update application.properties
spring.datasource.url=jdbc:mysql://localhost:3306/shopease_db
spring.datasource.username=root
spring.datasource.password=YOUR_PASSWORD

4️⃣ Run the Application
Option 1: STS/Eclipse

Import as Existing Maven Project

Right-click → Run As → Spring Boot App
Option 2: Command Line
mvn spring-boot:run

5️⃣ Access the Application
Open browser: http://localhost:9090

🔑 Login Credentials
Role	Email	Password
Admin	admin@shopease.com	admin123
Demo User	avinashtiwari@gmail.com	avinash123

📱 Application Screenshots
Page	             Description
/	                 Home page with featured products
/products/shop	   Product listing with filters
/cart	             Shopping cart management
/orders/my-orders	 Order history
/admin/dashboard	 Admin analytics dashboard
/admin/products	   Product management CRUD
/admin/orders	     Order status management

🎯 Features Demonstrated
👤 User Features
✅ Browse products by 6 categories

✅ Search products by name/brand

✅ Filter by category and price

✅ Add/remove products from cart

✅ Update cart quantities

✅ User registration & login

✅ Place orders with address

✅ View order history

👨‍💼 Admin Features
✅ Dashboard with statistics (revenue, orders, users)

✅ Add new products

✅ Edit existing products

✅ Delete products

✅ View all orders

✅ Update order status (Pending → Shipped → Delivered)

🔒 Security Implementation
Password Encryption: BCrypt hashing (not stored as plain text)

Role-Based Access: USER and ADMIN roles

Protected Routes: Checkout and Admin panel require authentication

Session Management: Spring Security session handling

📊 Database Schema
Table	Description
users	User details (id, name, email, password, role)
products	Product catalog (id, name, price, stock, category)
orders	Order details (id, user_id, total, status, address)
order_items	Order items mapping (order_id, product_id, quantity)
🚧 Future Scope (Level 3 & 4)
Level	Features
Level 3	Payment Gateway (Razorpay), Wishlist, Email Notifications
Level 4	Live Order Tracking, Sales Analytics, Mobile App
👨‍💻 Author
Avinash Tiwari

BCA Final Year

ID: 310-5158 | Batch: B2 | Roll No: C13

📅 Project Timeline
Level	Date	Status
Level 1 (PPT)	20-21 May 2026	✅ Completed
Level 2 (Live Demo)	5-6 June 2026	🔄 Ready
Level 3 (Improvements)	25-28 June 2026	⏳ Pending
Level 4 (Final Report)	6-7 July 2026	⏳ Pending
🙏 Acknowledgments
Spring Boot Documentation

Thymeleaf Reference Guide

Bootstrap 5

Unsplash for images

## 📞 Contact

For any queries: **support@shopease.com**

⭐ **If you found this project helpful, please give it a star!**
