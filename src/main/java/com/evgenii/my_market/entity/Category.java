package com.evgenii.my_market.entity;

import com.evgenii.my_market.dto.CategoryDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "category")
@Data
@NoArgsConstructor
public class Category {

    @Id
    @Column(name = "category_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int categoryId;

    @Column(name = "name")
    @NotEmpty(message = "Please provide a category name")
    private String categoryName;


    public Category(CategoryDto newCategory) {
        this.categoryName = newCategory.getCategoryName();
    }
}
