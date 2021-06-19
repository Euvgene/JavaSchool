package com.evgenii.my_market.rest_controller;

import com.evgenii.my_market.dto.CartDto;
import com.evgenii.my_market.entity.Cart;
import com.evgenii.my_market.service.api.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Rest controller for all possible actions with cart.
 *
 * @author Boznyakov Evgenii
 */
@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    /**
     * Method responsible for creating new Cart for user
     *
     * @return UUID
     */
    @PostMapping
    public UUID createNewCart() {
        return cartService.getCartForUser(null, null);
    }

    /**
     * Method responsible for representation current Cart for user
     *
     * @param uuid cart id
     * @return {@linkplain com.evgenii.my_market.dto.CartDto}
     */
    @GetMapping("/{uuid}")
    public CartDto getCurrentCart(@PathVariable UUID uuid) {
        Cart cart = cartService.findById(uuid);
        return new CartDto(cart);
    }

    /**
     * Method responsible for adding product to Cart
     *
     * @param uuid      cart id
     * @param productId product id
     */
    @PostMapping("/add")
    public void addProductToCart(@RequestParam(name = "uuid") UUID uuid, @RequestParam(name = "prod_id") int productId) {
        cartService.addToCart(uuid, productId);
    }

    /**
     * Method responsible for deleting cart items from cart
     *
     * @param uuid cart id
     */
    @PostMapping("/clear")
    public void clearCart(@RequestParam(name = "uuid") UUID uuid) {
        cartService.clearCart(uuid);
    }

    /**
     * Method responsible for deleting invalid cart items from cart
     *
     * @param uuid cart id
     */
    @GetMapping("/clear")
    public ResponseEntity<?> clearOldItemsFromCart(@RequestParam(name = "uuid") UUID uuid) {
        return cartService.clearOldCartItems(uuid);
    }

    /**
     * Method responsible for decrement product quantity from cart or
     * if count of product in cart item equal to one delete it
     *
     * @param uuid cart id
     * @param productId product id
     * @param number action number (1 - delete, 0 - decrement)
     *
     */
    @PostMapping("/delete")
    public void updateQuantityOrDeleteProductInCart(@RequestParam(name = "uuid") UUID uuid,
                                                    @RequestParam(name = "product_id") int productId,
                                                    @RequestParam(name = "updateNumber", defaultValue = "0") int number) {
        cartService.updateQuantityInCart(uuid, productId, number);
    }
}
