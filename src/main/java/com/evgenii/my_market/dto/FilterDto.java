package com.evgenii.my_market.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class FilterDto {
    private int page;
    private String name;
    private String gender;
    private String category;
    private byte quantity;
    private byte notAvailable;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;

}
