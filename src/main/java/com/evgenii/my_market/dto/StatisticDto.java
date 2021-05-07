package com.evgenii.my_market.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class StatisticDto {
    private final int STATISTIC_NAME = 0;
    String name;
    int number;

    public StatisticDto(Object[] o) {
        this.name = o[STATISTIC_NAME].toString();
        this.number = (int) o[1];
    }
}
