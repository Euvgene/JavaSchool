package com.evgenii.my_market.dto;

import com.evgenii.my_market.validator.UniqueCategory;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CategoryDto {

    @UniqueCategory
    private String categoryName;

}
