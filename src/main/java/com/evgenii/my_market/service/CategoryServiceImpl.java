package com.evgenii.my_market.service;


import com.evgenii.my_market.dao.api.CategoryDAO;
import com.evgenii.my_market.dto.CategoryDto;
import com.evgenii.my_market.entity.Category;
import com.evgenii.my_market.service.api.CartService;
import com.evgenii.my_market.service.api.CategoryService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementation of {@link CategoryService} interface.
 *
 * @author Boznyakov Evgenii
 */
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    /**
     * Creates an instance of this class using constructor-based dependency injection.
     */
    private final CategoryDAO categoryDAO;
    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryServiceImpl.class);

    /**
     * Get all category from database
     *
     * @return list of category
     */
    @Transactional
    public List<Category> getAllCategory() {
        return categoryDAO.findAll();
    }

    /**
     * Save new category to database
     *
     * @param newCategory {@linkplain com.evgenii.my_market.dto.CategoryDto}
     */
    @Transactional
    public void save(CategoryDto newCategory) {
        Category category = new Category(newCategory);
        categoryDAO.saveNewCategory(category);
        LOGGER.info("Creat new category with name {}", category.getCategoryName());
    }

    /**
     * Check if category already exist in database
     *
     * @param value category name
     */
    @Transactional
    public boolean isCategoryAlreadyInUse(String value) {
        boolean categoryInDb = true;
        if (categoryDAO.getActiveCategory(value) == null) {
            categoryInDb = false;
        }
        return categoryInDb;
    }


    /**
     * Update category name
     *
     * @param newCategory {@linkplain com.evgenii.my_market.dto.CategoryDto CategoryDto}
     * @param categoryOldName old name of category
     */
    @Transactional
    public void update(CategoryDto newCategory, String categoryOldName) {
        Category category = categoryDAO.getActiveCategory(categoryOldName);
        category.setCategoryName(newCategory.getCategoryName());
        LOGGER.info("Update category {} to {} ", categoryOldName, newCategory.getCategoryName());
    }
}
