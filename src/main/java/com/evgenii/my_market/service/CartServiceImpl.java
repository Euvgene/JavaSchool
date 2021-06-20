package com.evgenii.my_market.service;

import com.evgenii.my_market.dao.api.CartDao;
import com.evgenii.my_market.dao.api.CartItemDAO;
import com.evgenii.my_market.entity.Cart;
import com.evgenii.my_market.entity.CartItem;
import com.evgenii.my_market.entity.Product;
import com.evgenii.my_market.entity.User;
import com.evgenii.my_market.exception_handling.MarketError;
import com.evgenii.my_market.exception_handling.ResourceNotFoundException;
import com.evgenii.my_market.service.api.CartService;
import com.evgenii.my_market.service.api.ProductService;
import com.evgenii.my_market.service.api.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

/**
 * Implementation of {@link CartService} interface.
 *
 * @author Boznyakov Evgenii
 */
@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    /**
     * Creates an instance of this class using constructor-based dependency injection.
     */
    private final CartDao cartDao;
    private final ProductService productService;
    private final UserService userService;
    private final CartItemDAO cartItemDAO;

    private static final Logger LOGGER = LoggerFactory.getLogger(CartServiceImpl.class);

    /**
     * Save new cart to database.
     *
     * @param cart {@linkplain com.evgenii.my_market.entity.Cart Car} to be saved
     * @return {@linkplain com.evgenii.my_market.entity.Cart Car}
     */
    @Transactional
    public Cart save(Cart cart) {
        return cartDao.save(cart);
    }

    /**
     * Find cart from database by id.
     *
     * @param id cart id
     * @return {@linkplain com.evgenii.my_market.entity.Cart Cart}
     */
    @Transactional
    public Cart findById(UUID id) {
        Cart cart = cartDao.findById(id);
        for (CartItem ci : cart.getCartItems()) {
            if (ci.getProduct().getProductQuantity() == 0) {
                ci.setQuantity((byte) 0);
            }
        }
        return cart;
    }

    /**
     * Add to cart product by id. Check if product exist in cart.
     * If product exist than increment count of product and recalculate price.
     * If not exist than create new cart item.
     *
     * @param cartId    cart id
     * @param productId product id
     */
    @Transactional
    public void addToCart(UUID cartId, int productId) {
        Cart cart = findById(cartId);
        CartItem cartItem = cart.getItemByProductId(productId);
        if (cartItem != null && cartItem.getProduct().getProductQuantity() > cartItem.getQuantity()) {
            cartItem.incrementQuantity();
            cart.recalculate();
            return;
        } else if (cartItem != null && cartItem.getProduct().getProductQuantity() == cartItem.getQuantity()) return;
        try {
            Product p = productService.getProductById(productId);
            cart.add(new CartItem(p));
        } catch (Exception e) {
            throw new ResourceNotFoundException("Unable to add product with id: " + productId + " to cart. Product doesn't exist");
        }
    }

    /**
     * Delete cart items from cart
     *
     * @param cartId cart id
     */
    @Transactional
    public void clearCart(UUID cartId) {
        Cart cart = findById(cartId);
        cart.clear();
    }

    /**
     * Check product quantity of cart item  and decrement it if cart item quantity greater than product quantity.
     * Delete cart item if product quantity in database is zero.
     *
     * @param cartId cart id
     * @return {@linkplain org.springframework.http.ResponseEntity}
     */
    @Transactional
    public ResponseEntity<?> clearOldCartItems(UUID cartId) {
        boolean isValid = true;
        Cart cart = findById(cartId);
        List<CartItem> cartItems = cart.getCartItems();
        ResponseEntity<?> responseEntity = ResponseEntity.ok(HttpStatus.ACCEPTED);
        for (int i = 0; cart.getCartItems().size() > i; i++) {
            CartItem cartItem = cartItems.get(i);
            Product product = cartItem.getProduct();
            byte productQuantity = product.getProductQuantity();
            byte itemQuantity = cartItem.getQuantity();
            if (itemQuantity == 0) {
                cartItemDAO.deleteCartItem(cartItem.getId());
                cart.recalculate();
                LOGGER.warn("{} is over but user try to order it", product.getProductTitle());
                isValid = false;
            } else if (productQuantity < itemQuantity) {
                cartItem.setQuantity(productQuantity);
                cartItem.recalculate();
                cart.recalculate();
                LOGGER.warn("{} quantity is not enough for user order", product.getProductTitle());
                isValid = false;
            }
        }
        if (isValid) {
            return responseEntity;
        } else {
            return new ResponseEntity<>(new MarketError(HttpStatus.CONFLICT.value(),
                    "Your cart is not valid. Quantity of product is not enough or ended. Refreshing cart list"), HttpStatus.CONFLICT);
        }
    }

    /**
     * Find cart by user id
     *
     * @param id user id
     * @return List<Cart>
     */
    @Transactional
    public List<Cart> findByUserId(int id) {
        return cartDao.findByUserId(id);
    }


    /**
     * This method create cart for user if cart not exist, and merge old cart and new cart if old cart exist.
     * If user is not auth create new cart fo quest.
     *
     * @param username user name
     * @param cartUuid cart id
     * @return UUID
     */
    @Transactional
    public UUID getCartForUser(String username, UUID cartUuid) {
        if (username != null && cartUuid != null) {
            User user = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(String.format("User '%s' not found", username)));
            Cart cart = findById(cartUuid);
            List<Cart> oldCartList = findByUserId(user.getUserId());

            if (!oldCartList.isEmpty() && oldCartList.get(0).getCartId() != cart.getCartId()) {
                Cart oldCart = oldCartList.get(0);
                cart.merge(oldCart);
                cartDao.delete(oldCart);
            }
            cart.setUser(user);
        }
        if (username == null) {
            Cart cart = save(new Cart());
            return cart.getCartId();
        }
        User user = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(String.format("User '%s' not found", username)));
        List<Cart> cart = findByUserId(user.getUserId());
        return cart.get(0).getCartId();
    }

    /**
     * Decrement cart item quantity if quantity greater than one.
     * Delete cart item if cart item quantity equal to one
     *
     * @param cartId cart id
     * @param productId product id
     * @param number action number (1 - delete, 0 - decrement)
     */
    @Transactional
    public void updateQuantityInCart(UUID cartId, int productId, int number) {
        Cart cart = findById(cartId);
        CartItem cartItem = cart.getItemByProductId(productId);
        Product p = productService.getProductById(productId);
        if (cartItem.getQuantity() > 1) {
            cartItem.decrementQuantity();
            cart.recalculate();
        } else if (number != 0 || cartItem.getQuantity() <= 1) {
            cart.deleteProduct(p);
            cartItemDAO.deleteCartItem(cartItem.getId());
            cart.recalculate();
        }
    }

}
