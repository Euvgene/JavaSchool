package com.evgenii.my_market.dto;

import com.evgenii.my_market.entity.Order;
import com.evgenii.my_market.entity.OrderState;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@NoArgsConstructor
@Data
public class OrderDto {
    private int id;
    private BigDecimal totalPrice;
    private String creationDateTime;
    private String address;
    private String paymentMethod;
    private String orderState;

    public OrderDto(Order order) {
        this.id = order.getId();
        this.totalPrice = order.getPrice();
        this.creationDateTime = order.getCreatedAt().toString();
        this.address= order.getDeliveryMethode();
        this.paymentMethod = order.getPaymentMethod();
        this.orderState = order.getOrderState().getStateName();

    }
}
