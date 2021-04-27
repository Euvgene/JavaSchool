package com.evgenii.my_market.dto;

import com.evgenii.my_market.entity.Order;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Data
public class OrderResultDto {
    private List<OrderItemDto> items;
    private BigDecimal totalPrice;

    public OrderResultDto(Order order) {
        this.totalPrice = order.getPrice();
        this.items = order.getItems().stream().map(OrderItemDto::new).collect(Collectors.toList());
    }
}
