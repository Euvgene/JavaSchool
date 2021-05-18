package com.evgenii.my_market.dao.api;

import com.evgenii.my_market.entity.Cart;

import java.util.List;
import java.util.UUID;

public interface CartDao {
    void delete(Cart cart);

    Cart save(Cart cart);

    Cart findById(UUID id);

    List<Cart> findByUserId(int id);



}
