package com.evgenii.my_market.dto;

import com.evgenii.my_market.entity.OrderItem;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * DTO for entity {@linkplain com.evgenii.my_market.entity.OrderItem OrderItem}.
 * @author Boznyakov Evgenii
 *
 */
@NoArgsConstructor
@Data
public class OrderItemDto {
    private UUID orderId;
    private String productTitle;
    private int quantity;
    private BigDecimal price;

    /**
     * Constructor for creating new instance of this class.
     * @param orderItem an instance of {@linkplain com.evgenii.my_market.entity.OrderItem OrderItem}
     */
    public OrderItemDto(OrderItem orderItem) {
        this.orderId = orderItem.getOrder().getId();
        this.productTitle = orderItem.getProduct().getProductTitle();
        this.quantity = orderItem.getQuantity();
        this.price = orderItem.getPrice();
    }
}
