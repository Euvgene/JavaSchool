package com.evgenii.my_market.dto;

import com.evgenii.my_market.validator.UniqueEmail;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;

@NoArgsConstructor
@Data
public class UserEmailDto {
    @UniqueEmail
    @Email
    String value;
}
