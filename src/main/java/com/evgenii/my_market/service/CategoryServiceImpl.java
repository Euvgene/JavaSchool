package com.evgenii.my_market.service;


import com.evgenii.my_market.dao.api.CategoryDAO;
import com.evgenii.my_market.dto.CategoryDto;
import com.evgenii.my_market.entity.Category;
import com.evgenii.my_market.service.api.CategoryService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryDAO categoryDAO;
    private final Logger LOGGER = LoggerFactory.getLogger(CategoryService.class);

    @Transactional
    public List<Category> getAllCategory() {
        return categoryDAO.findAll();
    }

    @Transactional
    public void save(CategoryDto newCategory) {
        Category category = new Category(newCategory);
        categoryDAO.saveNewCategory(category);
        LOGGER.info("Creat new category with name " + category.getCategoryName());
    }

    @Transactional
    public boolean isCategoryAlreadyInUse(String value) {
        boolean categoryInDb = true;
        if (categoryDAO.getActiveCategory(value) == null) {
            categoryInDb = false;
        }
        return categoryInDb;
    }

    @Transactional
    public void update(CategoryDto newCategory, String categoryOldName) {
        Category category = categoryDAO.getActiveCategory(categoryOldName);
        category.setCategoryName(newCategory.getCategoryName());
        LOGGER.info("Update category " + categoryOldName + " to " + newCategory.getCategoryName());
    }
}
