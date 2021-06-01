package com.evgenii.my_market.service.api;

import com.evgenii.my_market.entity.Cart;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface CartService {

     Cart save(Cart cart);

     Cart findById(UUID id);

    public void addToCart(UUID cartId, int productId);

    public void clearCart(UUID cartId);

    public ResponseEntity<?> clearOldCartItems(UUID cartId);

    public List<Cart> findByUserId(int id);

    public UUID getCartForUser(String username, UUID cartUuid);

    public void updateQuantityInCart(UUID cartId, int productId, int number);

}
