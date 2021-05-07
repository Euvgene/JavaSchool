package com.evgenii.my_market.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class UpdatePasswordDto {
/*    @NotEmpty(message = "Please provide a password")
    @Size(min = 8, max = 20, message = "Password size must be 8-20 letters or numbers")*/
    private String oldPassword;
/*    @NotEmpty(message = "Please provide a password")
    @Size(min = 8, max = 20, message = "Password size must be 8-20 letters or numbers")*/
    private String newPassword;
}
