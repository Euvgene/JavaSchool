package com.evgenii.my_market.dto;

import com.evgenii.my_market.entity.CartItem;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO for entity {@linkplain com.evgenii.my_market.entity.CartItem CartItem}.
 * @author Boznyakov Evgenii
 *
 */
@NoArgsConstructor
@Data
public class CartItemDto {
    private int productId;
    private String productTitle;
    private int quantity;
    private BigDecimal pricePerProduct;
    private BigDecimal price;
    private String fotoId;

    /**
     * Constructor for creating new instance of this class.
     * @param cartItem an instance of {@linkplain com.evgenii.my_market.entity.CartItem CartItem}
     */
    public CartItemDto(CartItem cartItem) {
        this.fotoId = cartItem.getProduct().getFotoId();
        this.productId = cartItem.getProduct().getProductId();
        this.productTitle = cartItem.getProduct().getProductTitle();
        this.quantity = cartItem.getQuantity();
        this.pricePerProduct = cartItem.getPricePerProduct();
        this.price = cartItem.getPrice();
    }
}
