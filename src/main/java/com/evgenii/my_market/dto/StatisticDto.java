package com.evgenii.my_market.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class StatisticDto {
    private static final int STATISTIC_OBJECT_NAME = 0;
    private static final int STATISTIC_OBJECT_COUNT = 1;

    String name;
    int number;

    public StatisticDto(Object[] o) {
        this.name = o[STATISTIC_OBJECT_NAME].toString();
        this.number = (int) o[STATISTIC_OBJECT_COUNT];
    }
}
