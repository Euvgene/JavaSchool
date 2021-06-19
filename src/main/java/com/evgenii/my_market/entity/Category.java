package com.evgenii.my_market.entity;

import com.evgenii.my_market.dto.CategoryDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

/**
 * Entity class for category.
 *
 * @author Boznyakov Evgenii
 */
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

    /**
     * Constructor for creating new instance of this class.
     *
     * @param newCategory an instance of {@linkplain com.evgenii.my_market.dto.CategoryDto }
     */
    public Category(CategoryDto newCategory) {
        this.categoryName = newCategory.getCategoryName();
    }
}
