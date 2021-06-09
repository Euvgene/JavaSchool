package com.evgenii.my_market.rest_controller;

import com.evgenii.my_market.dto.CartDto;
import com.evgenii.my_market.entity.Cart;
import com.evgenii.my_market.service.api.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @PostMapping
    public UUID createNewCart() {
        return cartService.getCartForUser(null, null);
    }

    @GetMapping("/{uuid}")
    public CartDto getCurrentCart(@PathVariable UUID uuid) {
        Cart cart = cartService.findById(uuid);
        return new CartDto(cart);
    }

    @PostMapping("/add")
    public void addProductToCart(@RequestParam(name = "uuid") UUID uuid, @RequestParam(name = "prod_id") int productId) {
        cartService.addToCart(uuid, productId);
    }

    @PostMapping("/clear")
    public void clearCart(@RequestParam(name = "uuid") UUID uuid) {
        cartService.clearCart(uuid);
    }


    @GetMapping("/clear")
    public ResponseEntity<?> clearOldItemsFromCart(@RequestParam(name = "uuid") UUID uuid) {
        return cartService.clearOldCartItems(uuid);
    }

    @PostMapping("/delete")
    public void updateQuantityOrDeleteProductInCart(@RequestParam(name = "uuid") UUID uuid,
                                                    @RequestParam(name = "product_id") int productId,
                                                    @RequestParam(name = "updateNumber", defaultValue = "0") int number) {
        cartService.updateQuantityInCart(uuid, productId, number);
    }
}
