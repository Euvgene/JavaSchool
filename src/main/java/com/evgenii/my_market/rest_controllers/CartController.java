package com.evgenii.my_market.rest_controllers;

import com.evgenii.my_market.dto.CartDto;
import com.evgenii.my_market.entity.Cart;
import com.evgenii.my_market.exception_handling.ResourceNotFoundException;
import com.evgenii.my_market.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;


@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @PostMapping
    public UUID createNewCart(Principal principal) {
        if (principal == null) {
            return cartService.getCartForUser(null, null);
        }
        return cartService.getCartForUser(principal.getName(), null);
    }

    @GetMapping("/{uuid}")
    public CartDto getCurrentCart(@PathVariable UUID uuid) {
        Cart cart = cartService.findById(uuid).orElseThrow(() -> new ResourceNotFoundException("Unable to find cart with id: " + uuid));
        return new CartDto(cart);
    }

    @PostMapping("/add")
    public void addProductToCart(@RequestParam(name = "uuid")  UUID uuid, @RequestParam(name = "prod_id") int productId) {
        cartService.addToCart(uuid, productId);
    }

    /*@PostMapping("/clear")
    public void clearCart(@RequestParam UUID uuid) {
        cartService.clearCart(uuid);
    }*/

    /*@PostMapping("/delete")
    public void updateQuantityOrDeleteProductInCart(@RequestParam UUID uuid, @RequestParam(name = "product_id") Long productId, @RequestParam(name = "updateNumber",defaultValue = "0") int number) {
        cartService.updateQuantityOrDeleteProductInCart(uuid, productId,number);
    }*/
}
