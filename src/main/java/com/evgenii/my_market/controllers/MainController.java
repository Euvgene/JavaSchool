package com.evgenii.my_market.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Controller
@EnableWebMvc
public class MainController {

    @GetMapping("/user_products") //TODO google how to naming urls
    public String getProductsForUser() {
        return "user/user_products";
    }

    @GetMapping("/products")
    public String getProducts() {
        return "products";
    }

    @GetMapping("/admin_products")
    public String getProductsForAdmin() {
        return "admin/admin_products";
    }

    @GetMapping("/addproducts")
    public String getNewProductsPage() {
        return "admin/new_product";
    }



    @GetMapping("/user_main")
    public String getUserPage() {
        return "user/user_main";
    }

    @GetMapping("/admin_main")
    public String getAdminPage() {
        return "admin/admin_main";
    }

    @GetMapping("/registration")
    public String getRegistrationPage() {
        return "registration";
    }

    @GetMapping("/user_cart")
    public String getUserCartPage() {
        return "user/user_cart";
    }

    @GetMapping("/cart")
    public String geCartPage() {
        return "cart";
    }

    @GetMapping("/order_confirmation")
    public String getOrderConfirmationPage() {
        return "user/order_confirmation";
    }
}
