package com.evgenii.my_market.service.api;

import com.evgenii.my_market.dto.CategoryDto;
import com.evgenii.my_market.entity.Category;

import java.util.List;

/**
 * Interface, containing list of required business-logic methods regarding
 * {@linkplain com.evgenii.my_market.entity.Category Category} entity.
 *
 * @author Boznyakov Evgenii
 */
public interface CategoryService {

    /**
     * Get all category from database
     *
     * @return list of category
     */
    List<Category> getAllCategory();

    /**
     * Save new category to database
     *
     * @param newCategory {@linkplain com.evgenii.my_market.dto.CategoryDto}
     */
    void save(CategoryDto newCategory);

    /**
     * Check if category already exist in database
     *
     * @param value category name
     */
    boolean isCategoryAlreadyInUse(String value);

    /**
     * Update category name
     *
     * @param newCategory {@linkplain com.evgenii.my_market.dto.CategoryDto CategoryDto}
     * @param categoryOldName old name of category
     */
    void update(CategoryDto newCategory, String categoryOldName);
}
