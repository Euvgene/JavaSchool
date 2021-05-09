package com.evgenii.my_market.rest_controller;

import com.evgenii.my_market.dto.ProductDto;
import com.evgenii.my_market.entity.Product;
import com.evgenii.my_market.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/v1/products")
@RequiredArgsConstructor
public class ProductRestController {

    private final ProductService productService;

    @GetMapping("/{id}")
    public Product getProductBtId(@PathVariable int id) {
        return productService.getProductById(id);
    }


    @GetMapping()
    public List<ProductDto> getProductsPage(@RequestParam(name = "page", defaultValue = "1") int page,
                                       @RequestParam(name = "product_title", defaultValue = "") String name,
                                       @RequestParam(name = "gender", defaultValue = "") String gender,
                                       @RequestParam(name = "min_price", defaultValue = 0 + "") BigDecimal minPrice,
                                       @RequestParam(name = "max_price", defaultValue = Integer.MAX_VALUE + "") BigDecimal maxPrice) {
        List<Object> paramList = new ArrayList<>();
        paramList.add(name);
        paramList.add(gender);
        paramList.add(minPrice);
        paramList.add(maxPrice);
        return productService.getProductsPage(page, paramList);
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
