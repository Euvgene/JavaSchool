package com.evgenii.my_market.rest_controllers;

import com.evgenii.my_market.entity.Product;
import com.evgenii.my_market.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/products")
@RequiredArgsConstructor
public class ProductRestController {

    private final ProductService productService;

    @GetMapping("/{id}")
    public Product getProductBtId(@PathVariable int id) {
      return productService.getProductById(id);
    }
}
