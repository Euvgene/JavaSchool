package com.evgenii.my_market.dao.api;

import com.evgenii.my_market.entity.Cart;

import java.util.List;
import java.util.UUID;

/**
 * Interface for basic CRUD operations in Cart entity.
 *
 * @author Boznyakov Evgenii
 *
 */

public interface CartDao {
    /**
     * Delete entity from database.
     *
     * @param cart entity object to delete
     */
    void delete(Cart cart);

    /**
     * Save entity to database.
     *
     * @param cart entity object to save
     */
    Cart save(Cart cart);

    /**
     * Find by id entity from database.
     *
     * @param id which cart to find
     */
    Cart findById(UUID id);

    /**
     * Find cart by user id  from database.
     *
     * @param id user id
     */
    List<Cart> findByUserId(int id);

}
