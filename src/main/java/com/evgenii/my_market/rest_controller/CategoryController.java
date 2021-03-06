package com.evgenii.my_market.rest_controller;

import com.evgenii.my_market.dto.CategoryDto;
import com.evgenii.my_market.entity.Category;
import com.evgenii.my_market.service.api.CategoryService;
import com.evgenii.my_market.validator.UniqueCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Rest controller for all possible actions with category.
 *
 * @author Boznyakov Evgenii
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/category")
public class CategoryController {
    private final CategoryService categoryService;

    /**
     * Method responsible for representation of all category
     *
     * @return List<Category>
     */
    @GetMapping()
    public List<Category> getAllCategory() {
        return categoryService.getAllCategory();
    }

    /**
     * Method responsible for saving new category
     *
     * @param newCategory {@linkplain com.evgenii.my_market.dto.CategoryDto}
     */
    @PostMapping
    public ResponseEntity<?> saveCategory(@Valid @RequestBody CategoryDto newCategory) {
        categoryService.save(newCategory);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    /**
     * Method responsible for updating category name
     *
     * @param newCategory     {@linkplain com.evgenii.my_market.dto.CategoryDto}
     * @param oldCategoryName category name to change
     * @return {@linkplain org.springframework.http.ResponseEntity}
     */
    @PutMapping("/{oldCategoryName}")
    public ResponseEntity<?> changeCategory(@Valid @RequestBody CategoryDto newCategory,
                                            @UniqueCategory @PathVariable String oldCategoryName) {
        categoryService.update(newCategory, oldCategoryName);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

}
