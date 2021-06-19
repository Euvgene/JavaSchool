package com.evgenii.my_market.dto;

import com.evgenii.my_market.validator.UniqueCategory;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for entity {@linkplain com.evgenii.my_market.entity.Category}.
 * @author Boznyakov Evgenii
 *
 */
@Data
@NoArgsConstructor
public class CategoryDto {

    @UniqueCategory
    private String categoryName;

}
