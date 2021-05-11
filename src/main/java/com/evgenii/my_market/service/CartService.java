package com.evgenii.my_market.service;

import com.evgenii.my_market.dao.CartDAO;
import com.evgenii.my_market.dao.CartItemDAO;
import com.evgenii.my_market.entity.Cart;
import com.evgenii.my_market.entity.CartItem;
import com.evgenii.my_market.entity.Product;
import com.evgenii.my_market.entity.User;
import com.evgenii.my_market.exception_handling.MarketError;
import com.evgenii.my_market.exception_handling.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartDAO cartDAO;
    private final ProductService productService;
    private final UserService userService;
    private final CartItemDAO cartItemDAO;

    @Transactional
    public Cart save(Cart cart) {
        return cartDAO.save(cart);
    }

    @Transactional
    public Optional<Cart> findById(UUID id) {

        return cartDAO.findById(id);
    }

    @Transactional
    public void addToCart(UUID cartId, int productId) {
        Cart cart = findById(cartId).orElseThrow(() -> new ResourceNotFoundException("Unable to find cart with id: " + cartId));
        CartItem cartItem = cart.getItemByProductId(productId);
        if (cartItem != null && cartItem.getProduct().getProductQuantity() > cartItem.getQuantity()) {
            cartItem.incrementQuantity();
            cart.recalculate();
            return;
        } else if (cartItem != null && cartItem.getProduct().getProductQuantity() == cartItem.getQuantity()) return;
        try {
            Product p = productService.findProductById(productId).get(0);
            cart.add(new CartItem(p));
        } catch (Exception e) {
            throw new ResourceNotFoundException("Unable to add product with id: " + productId + " to cart. Product doesn't exist");
        }
    }

    @Transactional
    public void clearCart(UUID cartId) {
        Cart cart = findById(cartId).orElseThrow(() -> new ResourceNotFoundException("Unable to find cart with id: " + cartId));
        cart.clear();
    }

    @Transactional
    public List<Cart> findByUserId(int id) {
        return cartDAO.findByUserId(id);
    }


    @Transactional
    public UUID getCartForUser(String username, UUID cartUuid) {
        if (username != null && cartUuid != null) {
            User user = userService.findByUsername(username).get();
            Cart cart = findById(cartUuid).get();
            List<Cart> oldCartList = findByUserId(user.getUserId());

            if (!oldCartList.isEmpty() && oldCartList.get(0).getCartId() != cart.getCartId()) {
                Cart oldCart = oldCartList.get(0);
                cart.merge(oldCart);
                cartDAO.delete(oldCart);
            }
            cart.setUser(user);
        }
        if (username == null) {
            Cart cart = save(new Cart());
            return cart.getCartId();
        }
        User user = userService.findByUsername(username).get();
        List<Cart> cart = findByUserId(user.getUserId());
        return cart.get(0).getCartId();

    }

    @Transactional
    public void updateQuantityOrDeleteProductInCart(UUID cartId, int productId, int number) {
        Cart cart = findById(cartId).orElseThrow(() -> new ResourceNotFoundException("Unable to find cart with id: " + cartId));
        CartItem cartItem = cart.getItemByProductId(productId);
        Product p = productService.findProductById(productId).get(0);
        if (number != 0) {
            cart.deleteProduct(p);
            cartItemDAO.deleteCartItem(cartItem.getId());
            cart.recalculate();
        } else if (cartItem.getQuantity() > 1) {
            cartItem.decrementQuantity();
            cart.recalculate();
        } else {
            cart.deleteProduct(p);
            cartItemDAO.deleteCartItem(cartItem.getId());
            cart.recalculate();
        }
    }

}
