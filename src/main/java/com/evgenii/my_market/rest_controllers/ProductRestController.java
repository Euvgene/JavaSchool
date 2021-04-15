package com.evgenii.my_market.rest_controllers;

import com.evgenii.my_market.dao.ProductDAO;
import com.evgenii.my_market.dto.ProductDto;
import com.evgenii.my_market.entity.Product;
import com.evgenii.my_market.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

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

 /*   @GetMapping
    public List<Product> getAllProducts(){
    return productService.getAllProducts();
    }*/

    @GetMapping()
    public List<ProductDto> pagination(@RequestParam(name = "p", defaultValue = "1") int page,
                                       @RequestParam(name = "name", defaultValue = "") String name,
                                       @RequestParam(name = "gender", defaultValue = "") String gender,
                                       @RequestParam(name = "min_price", defaultValue = 0 + "") BigDecimal minPrice,
                                       @RequestParam(name = "max_price", defaultValue = Integer.MAX_VALUE + "") BigDecimal maxPrice) {
        List<Object> paramList = new ArrayList<>();
        paramList.add(name);
        paramList.add(gender);
        paramList.add(minPrice);
        paramList.add(maxPrice);
        return productService.pagGetAllProducts(page, paramList);
    }
}
