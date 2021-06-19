package com.evgenii.my_market.dao.api;

import com.evgenii.my_market.entity.Category;

import java.util.List;

/**
 * Interface for basic CRUD operations in Category entity.
 *
 * @author Boznyakov Evgenii
 */
public interface CategoryDAO {

    /**
     * Find all entity from database.
     */
    List<Category> findAll();

    /**
     * Save entity to database.
     *
     * @param newCategory entity object to save
     */
    void saveNewCategory(Category newCategory);

    /**
     * Get entity from database.
     *
     * @param value category name to return entity
     */
    Category getActiveCategory(String value);
}
