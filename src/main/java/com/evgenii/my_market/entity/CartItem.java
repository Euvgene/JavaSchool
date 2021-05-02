package com.evgenii.my_market.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "cart_items")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_item_id")
    private int id;

    @ManyToOne()
    @JoinColumn(name = "cart_id", insertable = true, updatable = true, nullable = true)
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "quantity")
    private byte quantity;

    @Column(name = "price_per_product")
    private BigDecimal pricePerProduct;

    @Column(name = "cart_item_price")
    private BigDecimal price;


    public CartItem(Product product) {
        this.product = product;
        this.quantity = 1;
        this.pricePerProduct = product.getProductPrice();
        this.price = this.pricePerProduct;
    }

    public void incrementQuantity() {
        quantity++;
        price = BigDecimal.valueOf(quantity * pricePerProduct.doubleValue());
    }

    public void incrementQuantity(int amount) {
        quantity += amount;
        price = BigDecimal.valueOf(quantity * pricePerProduct.doubleValue());
    }


    public void decrementQuantity() {
        quantity--;
        price = BigDecimal.valueOf(quantity * pricePerProduct.doubleValue());
    }

    public void decrementQuantityProduct() {
        byte prodQuantity = this.product.getProductQuantity();
        this.product.setProductQuantity(prodQuantity--);
    }
}
