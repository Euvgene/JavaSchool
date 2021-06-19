package com.evgenii.my_market.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO for jwt response.
 * @author Boznyakov Evgenii
 *
 */
@Data
@AllArgsConstructor
public class JwtResponse {
    private String token;
    private String userRole;
}
