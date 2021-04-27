package com.evgenii.my_market.dto;

import com.evgenii.my_market.entity.OrderItem;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;


@NoArgsConstructor
@Data
public class OrderItemDto {
    private UUID orderId;
    private String productTitle;
    private int quantity;
    private BigDecimal price;

    public OrderItemDto(OrderItem orderItem) {
        this.orderId = orderItem.getOrder().getId();
        this.productTitle = orderItem.getProduct().getProductTitle();
        this.quantity = orderItem.getQuantity();
        this.price = orderItem.getPrice();
    }
}
