package com.evgenii.my_market.dao.api;

/**
 * Interface for basic CRUD operations in CartItem entity.
 *
 * @author Boznyakov Evgenii
 *
 */
public interface CartItemDAO {
    /**
     * Delete entity from database.
     *
     * @param id entity object to delete
     */
    void deleteCartItem(int id);
}
