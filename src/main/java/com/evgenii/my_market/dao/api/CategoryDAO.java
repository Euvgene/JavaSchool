package com.evgenii.my_market.dao.api;

import com.evgenii.my_market.entity.Category;
import java.util.List;


public interface CategoryDAO {

     List<Category> findAll();

     void saveNewCategory(Category newCategory);

     Category getActiveCategory(String value);
}
