package com.evgenii.my_market.service.api;

import com.evgenii.my_market.dto.CategoryDto;
import com.evgenii.my_market.entity.Category;

import java.util.List;

public interface CategoryService {

    List<Category> getAllCategory();

    void save(CategoryDto newCategory);

    boolean isCategoryAlreadyInUse(String value);

    void update(CategoryDto newCategory, String categoryOldName);
}
