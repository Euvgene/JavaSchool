package com.evgenii.my_market.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.type.IntegerType;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

@NoArgsConstructor
@Data
public class StatisticDto {
    String name;
    int number;

    public StatisticDto(Object[] o) {
        this.name = o[0].toString(); // todo magic numbers
        this.number = (int) o[1];
    }
}
