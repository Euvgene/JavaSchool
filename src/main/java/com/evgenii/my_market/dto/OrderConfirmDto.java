package com.evgenii.my_market.dto;

import com.evgenii.my_market.validator.ValidPaymentMethod;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;

@NoArgsConstructor
@Data
public class OrderConfirmDto {
    private String username;
    @Pattern(regexp = "^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$", message = "Wrong cart id")
    private String cartId;
    @Pattern(regexp = "^[-.,;:a-zA-Z0-9_ ]*$", message = "Invalid address")
    private String address;
    @ValidPaymentMethod
    private String paymentMethod;

    public OrderConfirmDto(String cartId, String address, String paymentMethod) {
        this.cartId = cartId;
        this.address = address;
        this.paymentMethod = paymentMethod;
    }
}
