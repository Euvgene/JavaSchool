package com.evgenii.my_market.services;

import com.evgenii.my_market.dao.CategoryDAO;
import com.evgenii.my_market.entity.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryDAO categoryDAO;

    public List<Category> getAllCategory() {
        return categoryDAO.findAll();
    }

    @Transactional
    public void save(Category newCategory) {
        categoryDAO.saveNewCategory(newCategory);
    }
}
