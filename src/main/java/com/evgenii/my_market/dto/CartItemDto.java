package com.evgenii.my_market.dto;

import com.evgenii.my_market.entity.CartItem;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@NoArgsConstructor
@Data
public class CartItemDto {
    private int productId;
    private String productTitle;
    private int quantity;
    private BigDecimal pricePerProduct;
    private BigDecimal price;
    private String fotoId;


    public CartItemDto(CartItem cartItem) {
        this.fotoId = cartItem.getProduct().getFotoId();
        this.productId = cartItem.getProduct().getProductId();
        this.productTitle = cartItem.getProduct().getProductTitle();
        this.quantity = cartItem.getQuantity();
        this.pricePerProduct = cartItem.getPricePerProduct();
        this.price = cartItem.getPrice();
    }
}
