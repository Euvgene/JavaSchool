package com.evgenii.my_market.rest_controller;

import com.evgenii.my_market.entity.Category;
import com.evgenii.my_market.exception_handling.MarketError;
import com.evgenii.my_market.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/category")
@RequiredArgsConstructor
public class CategoryRestController {
    private final CategoryService categoryService;

    @GetMapping()
    public List<Category> pagination() {
        return categoryService.getAllCategory();
    }

    @PostMapping
    public void saveCategory(@RequestBody Category newCategory) {
        categoryService.save(newCategory);
    }
}
