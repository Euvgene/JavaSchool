package com.evgenii.my_market.dto;

import lombok.Data;

import java.util.UUID;


/**
 * DTO for jwt request.
 * @author Boznyakov Evgenii
 *
 */
@Data
public class JwtRequest {
    private String username;
    private String password;
    private UUID cartId;
}
