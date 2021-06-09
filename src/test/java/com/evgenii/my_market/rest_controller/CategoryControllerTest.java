package com.evgenii.my_market.rest_controller;

import com.evgenii.my_market.config.SpringConfig;
import com.evgenii.my_market.dto.CategoryDto;
import com.evgenii.my_market.entity.Category;
import com.evgenii.my_market.service.api.CategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {SpringConfig.class})
class CategoryControllerTest {

    private static final int CATEGORY_ID = 1;
    private static final String CATEGORY_NAME = "cat";
    private static final String NEW_CATEGORY_NAME = "woolf";

    @Mock
    CategoryService categoryService;

    private CategoryController tested;

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        tested = new CategoryController(categoryService);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }


    @Test
    void getAllCategory() {
        Category category = new Category();
        category.setCategoryId(CATEGORY_ID);
        category.setCategoryName(CATEGORY_NAME);

        List<Category> categoryList = new ArrayList<>();
        categoryList.add(category);

        when(categoryService.getAllCategory()).thenReturn(categoryList);

        List<Category> testList = tested.getAllCategory();

        assertEquals(CATEGORY_NAME, testList.get(0).getCategoryName());
    }


    @SneakyThrows
    @Test
    void saveCategoryValid() {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setCategoryName(NEW_CATEGORY_NAME);

        doNothing().when(categoryService).save(categoryDto);

        ResponseEntity<?> responseEntity = tested.saveCategory(categoryDto);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @SneakyThrows
    @Test
    void saveCategoryInvalid() {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setCategoryName(CATEGORY_NAME);

        ObjectMapper mapper = new ObjectMapper();

        String json = mapper.writeValueAsString(categoryDto);
        mockMvc.perform(post("/api/v1/category")
                .contentType(
                        MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isBadRequest());
    }

    @SneakyThrows
    @Test
    void changeCategorySuccess() {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setCategoryName(NEW_CATEGORY_NAME);

        doNothing().when(categoryService).update(categoryDto, CATEGORY_NAME);

        ResponseEntity<?> responseEntity = tested.changeCategory(categoryDto, CATEGORY_NAME);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

    }

    @SneakyThrows
    @Test
    void changeCategoryBadRequest() {

        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setCategoryName(CATEGORY_NAME);
        ObjectMapper mapper = new ObjectMapper();

        String json = mapper.writeValueAsString(categoryDto);

        mockMvc.perform(
                put("/api/v1/category/" + NEW_CATEGORY_NAME)
                        .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isBadRequest());
    }
}