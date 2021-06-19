package com.evgenii.my_market.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entity class for cart item.
 *
 * @author Boznyakov Evgenii
 */
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

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    /**
     * Constructor for creating new instance of this class.
     *
     * @param product an instance of {@linkplain com.evgenii.my_market.entity.Product Product}
     */
    public CartItem(Product product) {
        this.product = product;
        this.quantity = 1;
        this.pricePerProduct = product.getProductPrice();
        this.price = this.pricePerProduct;
    }

    /**
     * Increment quantity and price of cart item by one.
     */
    public void incrementQuantity() {
        quantity++;
        price = BigDecimal.valueOf(quantity * pricePerProduct.doubleValue());
    }

    /**
     * Increment quantity and price of cart item by amount.
     */
    public void incrementQuantity(int amount) {
        quantity += amount;
        price = BigDecimal.valueOf(quantity * pricePerProduct.doubleValue());
    }

    /**
     * Decrement quantity and price of cart item by amount.
     */
    public void decrementQuantity() {
        quantity--;
        price = BigDecimal.valueOf(quantity * pricePerProduct.doubleValue());
    }

    /**
     * Recalculate price of cart item.
     */
    public void recalculate() {
        price = BigDecimal.valueOf(quantity * pricePerProduct.doubleValue());

    }
}
