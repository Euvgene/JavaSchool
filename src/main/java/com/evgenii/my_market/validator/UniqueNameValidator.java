package com.evgenii.my_market.validator;

import com.evgenii.my_market.service.api.UserService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Custom unique name validator.
 *
 * @author Boznyakov Evgenii
 */
@NoArgsConstructor
public class UniqueNameValidator implements ConstraintValidator<UniqueName, String> {

    @Autowired
    private UserService userService;

    /**
     * Implements the validation logic. The state of value must not be altered.
     * This method can be accessed concurrently, thread-safety must be ensured by the implementation.
     *
     * @param value   – object to validate
     * @param context – context in which the constraint is evaluated
     * @return false if value does not pass the constraint
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && !userService.isNameAlreadyInUse(value);
    }
}