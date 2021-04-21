package com.evgenii.my_market.rest_controllers;

import com.evgenii.my_market.entity.Category;
import com.evgenii.my_market.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    @ResponseStatus(HttpStatus.CREATED)
    public void saveCategory(@RequestBody Category newCategory) {
        categoryService.save(newCategory);
    }
}
