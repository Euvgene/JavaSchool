package com.evgenii.my_market.rest_controller;

import com.evgenii.my_market.dto.FilterDto;
import com.evgenii.my_market.dto.ProductDto;
import com.evgenii.my_market.entity.Product;
import com.evgenii.my_market.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/{id}")
    public Product getProductBtId(@PathVariable int id) {
        return productService.getProductById(id);
    }


    @PostMapping("/get")
    public List<ProductDto> getProductsPage(@RequestBody FilterDto filterDto) {

        return productService.getProductsPage(filterDto);
    }

    @PostMapping("/get-page-count")
    public BigInteger getProductsCount(@RequestBody FilterDto filterDto) {

        return productService.getProductsCount(filterDto);
    }

    @PostMapping
    public ResponseEntity<?>  saveProduct(@Valid @RequestBody ProductDto newProduct) {
        productService.save(newProduct);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> updateProduct(@Valid @RequestBody ProductDto product) {
        productService.update(product);
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }

    @GetMapping("/delete")
    public void deleteProductById(@RequestParam(name = "product_id") int id) {
        productService.deleteProductById(id);
    }
}
