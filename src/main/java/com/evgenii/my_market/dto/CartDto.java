package com.evgenii.my_market.dto;

import com.evgenii.my_market.entity.Cart;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * DTO for entity {@linkplain com.evgenii.my_market.entity.Cart Cart}.
 * @author Boznyakov Evgenii
 *
 */
@NoArgsConstructor
@Data
public class CartDto {
    private List<CartItemDto> items;
    private BigDecimal totalPrice;
    private String fotoId;

    /**
     * Constructor for creating new instance of this class.
     * @param cart an instance of {@linkplain com.evgenii.my_market.entity.Cart Cart}
     */
    public CartDto(Cart cart) {
        this.totalPrice = cart.getPrice();
        this.items = cart.getCartItems().stream().map(CartItemDto::new).collect(Collectors.toList());
    }
}
