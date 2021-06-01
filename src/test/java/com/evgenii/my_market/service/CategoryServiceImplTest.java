package com.evgenii.my_market.service;

import com.evgenii.my_market.dao.CategoryDAOImpl;
import com.evgenii.my_market.dto.CategoryDto;
import com.evgenii.my_market.entity.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    private static final String CATEGORY_OLD_NAME = "Cat";

    @Mock
    private CategoryDAOImpl categoryDAO;

    private CategoryServiceImpl tested;

    @BeforeEach
    void setup() {
        tested = new CategoryServiceImpl(categoryDAO);
    }

    @Test
    void save() {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setCategoryName("Cat");

        Category category = new Category(categoryDto);

        doNothing().when(categoryDAO).saveNewCategory(category);

        tested.save(categoryDto);
        assertEquals(category.getCategoryName(), categoryDto.getCategoryName());

    }

    @Test
    void getAllCategory() {
        List<Category> categoryList = new ArrayList<>();
        Category category = createCategory();
        categoryList.add(category);

        when(categoryDAO.findAll()).thenReturn(categoryList);

        List<Category> testList = tested.getAllCategory();
        assertEquals(testList, categoryList);
    }

    @Test
    void isCategoryAlreadyInUseFalse() {
        boolean categoryInDb = false;

        when(categoryDAO.getActiveCategory(anyString())).thenReturn(null);

        boolean isInDb = tested.isCategoryAlreadyInUse(anyString());
        assertEquals(isInDb, categoryInDb);
    }

    @Test
    void isCategoryAlreadyInUseTrue() {
        Category category = createCategory();

        when(categoryDAO.getActiveCategory(anyString())).thenReturn(category);

        boolean isInDb = tested.isCategoryAlreadyInUse(anyString());
        assertTrue(isInDb);
    }

    @Test
    void update() {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setCategoryName("Dog");

        Category category = createCategory();

        when(categoryDAO.getActiveCategory(CATEGORY_OLD_NAME)).thenReturn(category);
        tested.update(categoryDto, CATEGORY_OLD_NAME);
        assertEquals(category.getCategoryName(), categoryDto.getCategoryName());
    }

    private Category createCategory() {
        Category category = new Category();
        category.setCategoryName(CATEGORY_OLD_NAME);
        category.setCategoryId(1);
        return category;
    }

}