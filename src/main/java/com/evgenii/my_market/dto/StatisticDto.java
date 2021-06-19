package com.evgenii.my_market.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for statistic response.
 *
 * @author Boznyakov Evgenii
 */
@NoArgsConstructor
@Data
public class StatisticDto {
    private static final int STATISTIC_OBJECT_NAME = 0;
    private static final int STATISTIC_OBJECT_COUNT = 1;

    String name;
    int number;

    /**
     * Constructor for creating new instance of this class.
     *
     * @param objects array of objects contains statistic data
     */
    public StatisticDto(Object[] objects) {
        this.name = objects[STATISTIC_OBJECT_NAME].toString();
        this.number = (int) objects[STATISTIC_OBJECT_COUNT];
    }
}
