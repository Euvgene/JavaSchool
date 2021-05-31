package com.evgenii.my_market.service;

import com.evgenii.my_market.dao.api.CartDao;
import com.evgenii.my_market.dao.api.CartItemDAO;
import com.evgenii.my_market.entity.Cart;
import com.evgenii.my_market.entity.CartItem;
import com.evgenii.my_market.entity.Product;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    private static final UUID FIRST_CART_UID = UUID.fromString("64fc8473-97b5-46cb-9197-a631e3fb7e57");
    private static final UUID SECOND_CART_UID = UUID.fromString("64fc8473-97b5-46cb-9197-a631e3fb7e57");
    private static final int PRODUCT_ID = 1;
    private static final BigDecimal PRODUCT_PRICE = BigDecimal.valueOf(100);
    private static final int FIRST_PRODUCT_ITEM_INDEX = 0;
    private static final int PRODUCT_QUANTITY_ZERO = 0;
    private static final int PRODUCT_QUANTITY_ONE = 1;
    private static final int PRODUCT_QUANTITY_TWO = 2;
    private static final ResponseEntity<?> RESPONSE_ENTITY_ACCEPTED = ResponseEntity.ok(HttpStatus.ACCEPTED);
    private static final ResponseEntity<?> RESPONSE_ENTITY_CONFLICT = ResponseEntity.ok(HttpStatus.CONFLICT);


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
        when(productService.findProductById(PRODUCT_ID)).thenReturn(Collections.singletonList(product));

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

        tested.clearOldCartItems(FIRST_CART_UID);

        assertEquals(RESPONSE_ENTITY_CONFLICT, RESPONSE_ENTITY_CONFLICT);
    }


    @Test
    void clearOldCartItemsIfProductQuantityIsNotEnough() {
        Product product = createProduct(PRODUCT_QUANTITY_ONE);

        CartItem cartItem = createCartItem(product);
        cartItem.setQuantity((byte) 2);

        Cart expectedCart = createTestCart(cartItem);

        when(cartDao.findById(FIRST_CART_UID)).thenReturn(expectedCart);
        tested.clearOldCartItems(FIRST_CART_UID);
        assertEquals(RESPONSE_ENTITY_CONFLICT, RESPONSE_ENTITY_CONFLICT);
    }

    @Test
    void clearOldCartItemsIfProductQuantityIsEnough() {
        Product product = createProduct(PRODUCT_QUANTITY_TWO);

        CartItem cartItem = createCartItem(product);

        Cart expectedCart = createTestCart(cartItem);

        when(cartDao.findById(FIRST_CART_UID)).thenReturn(expectedCart);
        tested.clearOldCartItems(FIRST_CART_UID);
        assertEquals(RESPONSE_ENTITY_ACCEPTED, RESPONSE_ENTITY_ACCEPTED);
    }


    @Test
    void findByUserId() {
        List<Cart> cartList = new ArrayList<>();
        cartList.add(createEmptyCart());

        when(cartDao.findByUserId(1)).thenReturn(cartList);

        tested.findByUserId(1);

        assertEquals(cartList, cartList);

    }

    @Test
    void getCartForUser() {
    }

    @Test
    void updateQuantityOrDeleteProductInCart() {
    }

    private CartItem createCartItem(Product product) {
        CartItem cartItem = new CartItem();
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
}