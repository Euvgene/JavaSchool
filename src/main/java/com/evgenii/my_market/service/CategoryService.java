package com.evgenii.my_market.service;

import com.evgenii.my_market.dao.CategoryDAO;
import com.evgenii.my_market.dto.CategoryDto;
import com.evgenii.my_market.entity.Category;
import com.evgenii.my_market.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryDAO categoryDAO;

    @Transactional
    public List<Category> getAllCategory() {
        return categoryDAO.findAll();
    }

    @Transactional
    public void save(CategoryDto newCategory) {
        Category category = new Category(newCategory);
        categoryDAO.saveNewCategory(category);
    }

    @Transactional
    public boolean isCategoryAlreadyInUse(String value) {
        boolean categoryInDb = true;
        if (categoryDAO.getActiveCategory(value) == null) categoryInDb = false;
        return categoryInDb;
    }

    @Transactional
    public void update(CategoryDto newCategory, String categoryOldName) {
        Category category = categoryDAO.getActiveCategory(categoryOldName);
        category.setCategoryName(newCategory.getCategoryName());
    }
}
