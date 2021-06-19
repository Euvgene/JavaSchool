package com.evgenii.my_market.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * Entity class for carts.
 *
 * @author Boznyakov Evgenii
 */
@Entity
@Table(name = "carts")
@Getter
@Setter
@NoArgsConstructor
public class Cart {

    @Id
    @GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
    @GeneratedValue(generator = "UUIDGenerator")
    @Column(name = "cart_id")
    private UUID cartId;

    @OneToMany(mappedBy = "cart", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> cartItems;

    @Column(name = "price")
    private BigDecimal price;

    @OneToOne()
    @JoinColumn(name = "owner_id")
    private User user;

    /**
     * Add cart item to List<CartItem> cartItems.
     *
     * @param cartItem  {@linkplain com.evgenii.my_market.entity.CartItem CartItem}
     */
    public void add(CartItem cartItem) {
        for (CartItem ci : cartItems) {
            if (ci.getProduct().getProductId() == (cartItem.getProduct().getProductId())) {
                if (cartItem.getQuantity() + ci.getQuantity() < cartItem.getProduct().getProductQuantity()) {
                    ci.incrementQuantity(cartItem.getQuantity());
                    recalculate();
                }
                return;
            }
        }
        this.cartItems.add(cartItem);
        cartItem.setCart(this);
        recalculate();
    }

    /**
     * Recalculate price of cart item.
     */
    public void recalculate() {
        price = BigDecimal.valueOf(0);
        for (CartItem ci : cartItems) {
            price = BigDecimal.valueOf(price.doubleValue() + ci.getPrice().doubleValue());
        }
    }

    /**
     * Delete cart item from List<CartItem> cartItems.
     */
    public void clear() {
        for (CartItem ci : cartItems) {
            ci.setCart(null);
        }
        cartItems.clear();
        recalculate();
    }

    /**
     * Get cart item from List<CartItem> cartItems by product id.
     *
     * @param productId  id of product
     */
    public CartItem getItemByProductId(int productId) {
        for (CartItem ci : cartItems) {
            if (ci.getProduct().getProductId() == productId) {
                return ci;
            }
        }
        return null;
    }

    /**
     * Merge cart item from one cart to another.
     *
     * @param another   {@linkplain com.evgenii.my_market.entity.Cart Cart}
     */
    public void merge(Cart another) {
        for (CartItem ci : another.cartItems) {
            add(ci);
        }
    }

    /**
     * Delete product from List<CartItem> cartItems
     *
     * @param product  {@linkplain com.evgenii.my_market.entity.Product Product}
     */
    public void deleteProduct(Product product) {
        for (CartItem ci : cartItems) {
            if (ci.getProduct().getProductId() == product.getProductId()) {
                ci.setCart(null);
                ci.setPrice(BigDecimal.valueOf(0));
            }
        }
    }
}
