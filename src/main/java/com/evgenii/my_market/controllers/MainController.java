package com.evgenii.my_market.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Controller
@EnableWebMvc
public class MainController {

    @GetMapping("/products")
    public String getProductsPage() {
        return "products";
    }

    @GetMapping("/addproducts")
    public String getNewProductsPage() {
        return "new_product";
    }

}
