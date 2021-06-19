package com.evgenii.my_market.entity;

/**
 * Enumeration for representing order state.
 * @author Boznyakov Evgenii
 *
 */
public enum StateEnum {
    DELIVERED,
    RETURN,
    AWAITING_SHIPMENT,
    SHIPPED;

    StateEnum() {
    }
}
