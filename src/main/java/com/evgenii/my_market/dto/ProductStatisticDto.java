package com.evgenii.my_market.dto;

import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * DTO for product statistic response.
 * @author Boznyakov Evgenii
 *
 */
@NoArgsConstructor
@Data
public class ProductStatisticDto {
    private static final int STATISTIC_PRODUCT_NAME = 0;
    private static final int STATISTIC_PRODUCT_COUNT = 1;
    private static final int STATISTIC_PRODUCT_PRICE_PER_PRODUCT = 2;

    String name;
    int number;
    int pricePerProduct;

    /**
     * Constructor for creating new instance of this class.
     *
     * @param objects array of objects contains statistic data
     */
    public ProductStatisticDto(Object[] objects) {
        this.name = objects[STATISTIC_PRODUCT_NAME].toString();
        this.number = (int) objects[STATISTIC_PRODUCT_COUNT];
        this.pricePerProduct = (int) objects[STATISTIC_PRODUCT_PRICE_PER_PRODUCT];
    }

}
