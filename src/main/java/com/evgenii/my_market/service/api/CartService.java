package com.evgenii.my_market.service.api;

import com.evgenii.my_market.entity.Cart;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

/**
 * Interface, containing list of required business-logic methods regarding
 * {@linkplain com.evgenii.my_market.entity.Cart Car} entities.
 *
 * @author Boznyakov Evgenii
 */
public interface CartService {

    /**
     * Save new cart to database.
     *
     * @param cart {@linkplain com.evgenii.my_market.entity.Cart Car} to be saved
     * @return {@linkplain com.evgenii.my_market.entity.Cart Car}
     */
    Cart save(Cart cart);

    /**
     * Find cart from database by id.
     *
     * @param id cart id
     * @return {@linkplain com.evgenii.my_market.entity.Cart Cart}
     */
    Cart findById(UUID id);

    /**
     * Add to cart product by id.
     *
     * @param cartId    cart id
     * @param productId product id
     */
    void addToCart(UUID cartId, int productId);

    /**
     * Delete cart items from cart
     *
     * @param cartId cart id
     */
    void clearCart(UUID cartId);

    /**
     * Delete cart items from cart if cart item quantity get invalid
     *
     * @param cartId cart id
     * @return {@linkplain org.springframework.http.ResponseEntity}
     */
    ResponseEntity<?> clearOldCartItems(UUID cartId);

    /**
     * Find cart by user id
     *
     * @param id user id
     * @return List<Cart>
     */
    List<Cart> findByUserId(int id);

    /**
     * Get cart for user
     *
     * @param username user name
     * @param cartUuid cart id
     * @return UUID
     */
    UUID getCartForUser(String username, UUID cartUuid);

    /**
     * Decrement or delete product from cart
     *
     * @param cartId cart id
     * @param productId product id
     * @param number action number (1 - delete, 0 - decrement)
     */
    void updateQuantityInCart(UUID cartId, int productId, int number);

}
