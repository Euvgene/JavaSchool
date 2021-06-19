package com.evgenii.my_market.dto;

import com.evgenii.my_market.validator.FieldMatch;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;


/**
 * DTO for update password request.
 * @author Boznyakov Evgenii
 *
 */
@NoArgsConstructor
@Data
@FieldMatch(first = "firstPassword", second = "secondPassword", errorMessage = "Passwords do not match!")
public class UpdatePasswordDto {
    @NotEmpty(message = "Please provide a password")
    @Size(min = 8, max = 20, message = "Old password size must be 8-20 letters or numbers")
    private String oldPassword;

    @NotEmpty(message = "Please provide a password")
    @Size(min = 8, max = 20, message = "New password size must be 8-20 letters or numbers")
    private String firstPassword;

    @NotEmpty(message = "Please provide a password")
    @Size(min = 8, max = 20, message = "New password size must be 8-20 letters or numbers")
    private String secondPassword;
}
