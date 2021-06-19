package com.evgenii.my_market.dto;

import com.evgenii.my_market.validator.UniqueName;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for user name request.
 *
 * @author Boznyakov Evgenii
 */
@NoArgsConstructor
@Data
public class UserNameDto {
    @UniqueName
    String value;
}
