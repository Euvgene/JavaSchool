package com.evgenii.my_market.validator;

import com.evgenii.my_market.service.api.CategoryService;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Data
@NoArgsConstructor
public class UniqueCategoryValidator implements ConstraintValidator<UniqueCategory, String> {

    @Autowired
    private CategoryService categoryService;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && !categoryService.isCategoryAlreadyInUse(value);
    }


}