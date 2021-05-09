package com.evgenii.my_market.dto;

import com.evgenii.my_market.validator.UniqueName;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserNameDto {
    @UniqueName
    String value;
}
