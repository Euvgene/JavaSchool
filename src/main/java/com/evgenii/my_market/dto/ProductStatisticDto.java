package com.evgenii.my_market.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ProductStatisticDto {
    private static final int STATISTIC_PRODUCT_NAME = 0;
    private static final int STATISTIC_PRODUCT_COUNT = 1;
    private static final int STATISTIC_PRODUCT_PRICE_PER_PRODUCT = 2;

    String name;
    int number;
    int pricePerProduct;


    public ProductStatisticDto(Object[] o) {
        this.name = o[STATISTIC_PRODUCT_NAME].toString();
        this.number = (int) o[STATISTIC_PRODUCT_COUNT];
        this.pricePerProduct = (int) o[STATISTIC_PRODUCT_PRICE_PER_PRODUCT];
    }

}
