package com.evgenii.my_market.service;

import com.evgenii.my_market.dao.api.CartDao;
import com.evgenii.my_market.entity.Cart;
import com.evgenii.my_market.service.api.CartService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)

class CartServiceTest {
    @InjectMocks
    private CartService cartService;

    @Mock
    private CartDao cartDao;
    @Test
    void save() {

    }

    @Test
    void findById() {
    }

    @Test
    void addToCart() {
    }

    @Test
    void clearCart() {
    }

    @Test
    void clearOldCartItems() {
    }

    @Test
    void findByUserId() {
    }

    @Test
    void getCartForUser() {
    }

    @Test
    void updateQuantityOrDeleteProductInCart() {
    }
}