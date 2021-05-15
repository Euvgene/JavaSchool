package com.evgenii.my_market.rest_controller;

import com.evgenii.my_market.dto.CategoryDto;
import com.evgenii.my_market.entity.Category;
import com.evgenii.my_market.exception_handling.MarketError;
import com.evgenii.my_market.service.CategoryService;
import com.evgenii.my_market.validator.UniqueCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public ResponseEntity<?> saveCategory(@Valid @RequestBody CategoryDto newCategory) {
        categoryService.save(newCategory);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<?> changeCategory(@Valid @RequestBody CategoryDto newCategory,
                                            @UniqueCategory @PathVariable String categoryId) {
        categoryService.update(newCategory,categoryId);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

}
