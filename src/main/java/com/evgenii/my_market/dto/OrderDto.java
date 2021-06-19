package com.evgenii.my_market.dto;

import com.evgenii.my_market.entity.Order;
import com.evgenii.my_market.entity.StateEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * DTO for entity {@linkplain com.evgenii.my_market.entity.Order Order}.
 * @author Boznyakov Evgenii
 *
 */
@NoArgsConstructor
@Data
public class OrderDto {
    private UUID orderId;
    private BigDecimal totalPrice;
    private String creationDateTime;
    private String address;
    private String paymentMethod;
    private StateEnum orderState;

    /**
     * Constructor for creating new instance of this class.
     * @param order an instance of {@linkplain com.evgenii.my_market.entity.Order Order}
     */
    public OrderDto(Order order) {
        this.orderId = order.getId();
        this.totalPrice = order.getPrice();
        this.creationDateTime = order.getCreatedAt().toString();
        this.address = order.getDeliveryMethode();
        this.paymentMethod = order.getPaymentMethod();
        this.orderState = order.getOrderState();

    }
}
