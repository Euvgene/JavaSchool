package com.evgenii.my_market.rest_controller;

import com.evgenii.my_market.dto.FilterDto;
import com.evgenii.my_market.dto.ProductDto;
import com.evgenii.my_market.entity.Product;
import com.evgenii.my_market.service.api.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigInteger;
import java.util.List;

/**
 * Rest controller for all possible actions with product.
 *
 * @author Boznyakov Evgenii
 */
@RestController
@RequestMapping("api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    /**
     * Method responsible for getting product by id
     *
     * @param id product id
     * @return {@linkplain com.evgenii.my_market.entity.Product}
     */
    @GetMapping("/{id}")
    public Product getProductById(@PathVariable int id) {
        return productService.getProductById(id);
    }


    /**
     * Method responsible for getting product list using filter
     *
     * @param filterDto {@linkplain com.evgenii.my_market.dto.FilterDto} DTO for filter request
     * @return list of {@linkplain com.evgenii.my_market.dto.ProductDto ProductDto}
     */
    @PostMapping("/get")
    public List<ProductDto> getProductsPage(@RequestBody FilterDto filterDto) {
        return productService.getProductsPage(filterDto);
    }

    /**
     * Method responsible for getting product count using filter
     *
     * @param filterDto {@linkplain com.evgenii.my_market.dto.FilterDto} DTO for filter request
     * @return BigInteger
     */
    @PostMapping("/get-page-count")
    public BigInteger getProductsCount(@RequestBody FilterDto filterDto) {
        return productService.getProductsCount(filterDto);
    }

    /**
     * Method responsible for save new product
     *
     * @param newProduct {@linkplain com.evgenii.my_market.dto.ProductDto} DTO for product
     * @return {@linkplain org.springframework.http.ResponseEntity}
     */
    @PostMapping
    public ResponseEntity<?> saveProduct(@Valid @RequestBody ProductDto newProduct) {
        productService.save(newProduct);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    /**
     * Method responsible for update product
     *
     * @param product {@linkplain com.evgenii.my_market.dto.ProductDto}
     * @return {@linkplain org.springframework.http.ResponseEntity}
     */
    @PutMapping
    public ResponseEntity<?> updateProduct(@Valid @RequestBody ProductDto product) {
        productService.update(product);
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }

    /**
     * Method responsible for set product quantity to 0
     *
     * @param id product id
     */
    @GetMapping("/delete")
    public void deleteProductById(@RequestParam(name = "product_id") int id) {
        productService.deleteProductById(id);
    }
}
