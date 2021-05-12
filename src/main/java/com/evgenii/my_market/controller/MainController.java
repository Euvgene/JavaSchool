package com.evgenii.my_market.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Controller
@EnableWebMvc
public class MainController {

    @GetMapping("/user-info")
    @Secured(value = {"ROLE_USER"})
    public String changeUserInfo() {
        return "user/user_info";
    }

    @GetMapping("/user-products")
    public String getProductsForUser() {
        return "user/user_products";
    }

    @GetMapping("/products")
    public String getProducts() {
        return "products";
    }


    @GetMapping("/admin-products")
    public String getProductsForAdmin() {
        return "admin/admin_products";
    }

    @GetMapping("/addproducts")
    public String getNewProductsPage() {
        return "admin/new_product";
    }


    @GetMapping("/user-main")
    public String getUserPage() {
        return "user/user_main";
    }

    @GetMapping("/admin-main")
    public String getAdminPage() {
        return "admin/admin_main";
    }

    @GetMapping("/registration")
    public String getRegistrationPage() {
        return "registration";
    }

    @GetMapping("/user-cart")
    @Secured(value = {"ROLE_USER"})
    public String getUserCartPage() {
        return "user/user_cart";
    }

    @GetMapping("/cart")
    public String geCartPage() {
        return "cart";
    }

    @GetMapping("/order-confirmation")
    public String getOrderConfirmationPage() {
        return "user/order_confirmation";
    }

    @GetMapping("/orders-result")
    public String getOrderResultPage() {
        return "user/order_result";
    }

    @GetMapping("/user-orders")
    public String getUserOrdersPage() {
        return "user/orders";
    }

    @GetMapping("/change-password")
    public String changeUserPassword() {
        return "user/change_password";
    }

    @GetMapping("/change-orderes")
    public String changeOrdersPage() {
        return "admin/admin_orders";
    }

    @GetMapping("/statistic")
    public String showStatistic() {
        return "admin/admin_statistic";
    }

}
