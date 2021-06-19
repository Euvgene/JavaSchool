package com.evgenii.my_market.dto;

import com.evgenii.my_market.entity.Order;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * DTO for entity {@linkplain com.evgenii.my_market.entity.Order Order}.
 * @author Boznyakov Evgenii
 *
 */
@NoArgsConstructor
@Data
public class OrderResultDto {
    private List<OrderItemDto> items;
    private BigDecimal totalPrice;

    /**
     * Constructor for creating new instance of this class.
     * @param order an instance of {@linkplain com.evgenii.my_market.entity.Order Order}
     */
    public OrderResultDto(Order order) {
        this.totalPrice = order.getPrice();
        this.items = order.getItems().stream().map(OrderItemDto::new).collect(Collectors.toList());
    }
}
