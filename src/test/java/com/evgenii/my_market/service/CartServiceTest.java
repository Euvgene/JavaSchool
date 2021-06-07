package com.evgenii.my_market.service;

import com.evgenii.my_market.dao.api.CartDao;
import com.evgenii.my_market.dao.api.CartItemDAO;
import com.evgenii.my_market.entity.Cart;
import com.evgenii.my_market.entity.CartItem;
import com.evgenii.my_market.entity.Product;
import com.evgenii.my_market.entity.User;
import com.evgenii.my_market.exception_handling.MarketError;
import com.evgenii.my_market.service.api.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    private static final UUID FIRST_CART_UID = UUID.fromString("64fc8473-97b5-46cb-9197-a631e3fb7e57");
    private static final UUID SECOND_CART_UID = UUID.fromString("12fc8473-97b5-46cb-9197-a631e3fb7e12");
    private static final int PRODUCT_ID = 1;
    private static final BigDecimal PRODUCT_PRICE = BigDecimal.valueOf(100);
    private static final String USERNAME = "Bob";
    private static final int FIRST_PRODUCT_ITEM_INDEX = 0;
    private static final int PRODUCT_QUANTITY_ZERO = 0;
    private static final int PRODUCT_QUANTITY_ONE = 1;
    private static final int PRODUCT_QUANTITY_TWO = 2;
    private static final ResponseEntity<?> RESPONSE_ENTITY_ACCEPTED = ResponseEntity.ok(HttpStatus.ACCEPTED);
    private static final ResponseEntity<?> RESPONSE_ENTITY_CONFLICT = new ResponseEntity<>(new MarketError(HttpStatus.CONFLICT.value(),
            "Some message"), HttpStatus.CONFLICT);


    @Mock
    private CartDao cartDao;
    @Mock
    private ProductServiceImpl productService;
    @Mock
    private UserService userService;
    @Mock
    private CartItemDAO cartItemDAO;

    private CartServiceImpl tested;

    @BeforeEach
    void setup() {
        tested = new CartServiceImpl(cartDao, productService, userService, cartItemDAO);

    }

    @Test
    void save() {
    }

    @Test
    void findByIdEmptyCartTest() {
        Cart expectedCart = createEmptyCart();

        when(cartDao.findById(FIRST_CART_UID)).thenReturn(expectedCart);

        Cart testCart = tested.findById(FIRST_CART_UID);
        assertEquals(expectedCart, testCart);
    }


    @Test
    void findByIdNotEmptyCartAndProductQuantityBecomeZeroTest() {
        Product product = createProduct(PRODUCT_QUANTITY_ONE);

        CartItem cartItem = createCartItem(product);

        product.setProductQuantity((byte) PRODUCT_QUANTITY_ZERO);

        Cart expectedCart = createTestCart(cartItem);

        when(cartDao.findById(FIRST_CART_UID)).thenReturn(expectedCart);

        Cart testCart = tested.findById(FIRST_CART_UID);
        assertEquals(expectedCart, testCart);
    }

    @Test
    void addToCartNewProductTest() {
        Product product = createProduct(PRODUCT_QUANTITY_ONE);

        Cart expectedCart = createEmptyCart();

        when(cartDao.findById(FIRST_CART_UID)).thenReturn(expectedCart);
        when(productService.getProductById(PRODUCT_ID)).thenReturn(product);

        tested.addToCart(FIRST_CART_UID, PRODUCT_ID);

        assertEquals(expectedCart.getCartItems().get(FIRST_PRODUCT_ITEM_INDEX).getProduct().getProductId(), PRODUCT_ID);

    }

    @Test
    void addToCartAlreadyExistProductTest() {
        Product product = createProduct(PRODUCT_QUANTITY_TWO);

        CartItem cartItem = createCartItem(product);

        Cart expectedCart = createTestCart(cartItem);

        when(cartDao.findById(FIRST_CART_UID)).thenReturn(expectedCart);

        tested.addToCart(FIRST_CART_UID, PRODUCT_ID);

        assertEquals(expectedCart.getCartItems().get(FIRST_PRODUCT_ITEM_INDEX).getProduct().getProductQuantity(), PRODUCT_QUANTITY_TWO);
    }


    @Test
    void addToCartThrowsException() {
        Cart expectedCart = createEmptyCart();

        when(cartDao.findById(FIRST_CART_UID)).thenReturn(expectedCart);

        Assertions.assertThrows(Exception.class, () -> {
            tested.addToCart(FIRST_CART_UID, PRODUCT_ID);
        });
    }


    @Test
    void clearCart() {
        Product product = createProduct(PRODUCT_QUANTITY_TWO);

        CartItem cartItem = createCartItem(product);

        Cart expectedCart = createTestCart(cartItem);

        when(cartDao.findById(FIRST_CART_UID)).thenReturn(expectedCart);

        tested.clearCart(FIRST_CART_UID);
        assertEquals(expectedCart.getCartItems().size(), 0);

    }

    @Test
    void clearOldCartItemsIfProductAlreadyIsEmpty() {
        Product product = createProduct(PRODUCT_QUANTITY_TWO);

        CartItem cartItem = createCartItem(product);

        Cart expectedCart = createTestCart(cartItem);

        product.setProductQuantity((byte) PRODUCT_QUANTITY_ZERO);

        when(cartDao.findById(FIRST_CART_UID)).thenReturn(expectedCart);

        ResponseEntity<?> responseEntity = tested.clearOldCartItems(FIRST_CART_UID);

        assertEquals(responseEntity.getStatusCode(), RESPONSE_ENTITY_CONFLICT.getStatusCode());
    }


    @Test
    void clearOldCartItemsIfProductQuantityIsNotEnough() {
        Product product = createProduct(PRODUCT_QUANTITY_ONE);

        CartItem cartItem = createCartItem(product);
        cartItem.setQuantity((byte) 2);

        Cart expectedCart = createTestCart(cartItem);

        when(cartDao.findById(FIRST_CART_UID)).thenReturn(expectedCart);
        ResponseEntity<?> responseEntity = tested.clearOldCartItems(FIRST_CART_UID);

        assertEquals(responseEntity.getStatusCode(), RESPONSE_ENTITY_CONFLICT.getStatusCode());
    }

    @Test
    void clearOldCartItemsIfProductQuantityIsEnough() {
        Product product = createProduct(PRODUCT_QUANTITY_TWO);

        CartItem cartItem = createCartItem(product);

        Cart expectedCart = createTestCart(cartItem);

        when(cartDao.findById(FIRST_CART_UID)).thenReturn(expectedCart);
        ResponseEntity<?> responseEntity = tested.clearOldCartItems(FIRST_CART_UID);
        assertEquals(responseEntity.getStatusCode(), RESPONSE_ENTITY_ACCEPTED.getStatusCode());
    }


    @Test
    void findByUserId() {
        List<Cart> cartList = new ArrayList<>();
        cartList.add(createEmptyCart());

        when(cartDao.findByUserId(1)).thenReturn(cartList);

        List<Cart> testCartList = tested.findByUserId(1);

        assertEquals(testCartList, cartList);

    }

    @Test
    void getCartForUserIfUserExist() {
        Product product = createProduct(PRODUCT_QUANTITY_TWO);

        CartItem cartItem = createCartItem(product);

        Cart expectedCart = createTestCart(cartItem);

        User user = createUser(USERNAME);

        when(userService.findByUsername(USERNAME)).thenReturn(Optional.of(user));
        when(cartDao.findById(FIRST_CART_UID)).thenReturn(expectedCart);
        when(cartDao.findByUserId(user.getUserId())).thenReturn(Collections.singletonList(expectedCart));

        UUID uuid = tested.getCartForUser(USERNAME, FIRST_CART_UID);

        assertEquals(uuid, FIRST_CART_UID);

    }

    @Test
    void getCartForUserIfUserNotExist() {

        Cart expectedCart = new Cart();
        expectedCart.setCartId(FIRST_CART_UID);

        when(cartDao.save(any())).thenReturn(expectedCart);

        UUID uuid = tested.getCartForUser(null, null);

        assertEquals(uuid, FIRST_CART_UID);

    }

    @Test
    void getCartForUserIfUserAndCartAlreadyExist() {
        Product product = createProduct(PRODUCT_QUANTITY_TWO);

        CartItem cartItem = createCartItem(product);

        Cart expectedCart = createTestCart(cartItem);

        Cart oldCart = createTestCart(cartItem);
        oldCart.setCartId(SECOND_CART_UID);

        User user = createUser(USERNAME);

        when(userService.findByUsername(USERNAME)).thenReturn(Optional.of(user));
        when(cartDao.findById(FIRST_CART_UID)).thenReturn(expectedCart);
        when(cartDao.findByUserId(user.getUserId())).thenReturn(Collections.singletonList(oldCart))
                .thenReturn(Collections.singletonList(expectedCart));

        UUID uuid = tested.getCartForUser(USERNAME, FIRST_CART_UID);

        assertEquals(uuid, FIRST_CART_UID);

    }


    @Test
    void updateQuantityInCartWhenDeleteProduct() {
        Product product = createProduct(PRODUCT_QUANTITY_TWO);

        CartItem cartItem = createCartItem(product);

        Cart expectedCart = createTestCart(cartItem);

        when(cartDao.findById(FIRST_CART_UID)).thenReturn(expectedCart);
        when(productService.getProductById(PRODUCT_ID)).thenReturn(product);
        doNothing().when(cartItemDAO).deleteCartItem(PRODUCT_ID);

        tested.updateQuantityInCart(FIRST_CART_UID, PRODUCT_ID, 1);

        assertEquals(expectedCart.getPrice(), BigDecimal.valueOf(0.0));
    }

    @Test
    void updateQuantityInCartWhenDecrementProductCount() {
        Product product = createProduct(PRODUCT_QUANTITY_TWO);

        CartItem cartItem = createCartItem(product);
        cartItem.setQuantity((byte) PRODUCT_QUANTITY_TWO);

        Cart expectedCart = createTestCart(cartItem);

        when(cartDao.findById(FIRST_CART_UID)).thenReturn(expectedCart);
        when(productService.getProductById(PRODUCT_ID)).thenReturn(product);

        tested.updateQuantityInCart(FIRST_CART_UID, PRODUCT_ID, 0);

        assertEquals(expectedCart.getCartItems().get(FIRST_PRODUCT_ITEM_INDEX).getQuantity(), PRODUCT_QUANTITY_ONE);
    }

    @Test
    void updateQuantityInCartWhenDeleteProductWhichCountEqualOne() {
        Product product = createProduct(PRODUCT_QUANTITY_TWO);

        CartItem cartItem = createCartItem(product);

        Cart expectedCart = createTestCart(cartItem);

        when(cartDao.findById(FIRST_CART_UID)).thenReturn(expectedCart);
        when(productService.getProductById(PRODUCT_ID)).thenReturn(product);

        tested.updateQuantityInCart(FIRST_CART_UID, PRODUCT_ID, 0);

        assertEquals(expectedCart.getCartItems().get(FIRST_PRODUCT_ITEM_INDEX).getQuantity(), PRODUCT_QUANTITY_ONE);
    }

    private CartItem createCartItem(Product product) {
        CartItem cartItem = new CartItem();
        cartItem.setId(product.getProductId());
        cartItem.setProduct(product);
        cartItem.setQuantity((byte) PRODUCT_QUANTITY_ONE);
        cartItem.setPrice(PRODUCT_PRICE);
        cartItem.setPricePerProduct(PRODUCT_PRICE);
        return cartItem;
    }

    private Product createProduct(int productQuantity) {
        Product product = new Product();
        product.setProductId(PRODUCT_ID);
        product.setProductQuantity((byte) productQuantity);
        product.setProductPrice(PRODUCT_PRICE);
        return product;
    }

    private Cart createTestCart(CartItem cartItem) {
        Cart expectedCart = new Cart();
        expectedCart.setCartId(FIRST_CART_UID);
        expectedCart.setCartItems(new ArrayList<>());
        expectedCart.add(cartItem);
        return expectedCart;
    }

    private Cart createEmptyCart() {
        Cart expectedCart = new Cart();
        expectedCart.setCartId(FIRST_CART_UID);
        expectedCart.setCartItems(new ArrayList<>());
        return expectedCart;
    }

    private User createUser(String name) {
        User user = new User();
        user.setUserId(1);
        user.setFirstName(name);
        return user;
    }
}