package com.evgenii.my_market.dao.api;

import com.evgenii.my_market.entity.User;

import java.util.List;
import java.util.Optional;
/**
 * Interface for basic CRUD operations in User entity.
 *
 * @author Boznyakov Evgenii
 */
public interface UserDAO {
    /**
     * Find  entity from database by username.
     *
     * @param username name of user to find
     */
    Optional<User> findByUsername(String username);

    /**
     * Find  entity from database by username and email.
     *
     * @param username name of user to find by
     * @param email email of user to find by
     */
    List<User> findByUsernameAndEmail(String username, String email);

    /**
     * Save entity to database.
     *
     * @param newUsers entity object to save
     */
    void saveUser(User newUsers);

    /**
     * Update  entity in database.
     *
     * @param user which user to update
     */
    void update(User user);

    /**
     * Get  existing email in database.
     *
     * @param email email value
     */
    User getActiveEmail(String email);

    /**
     * Get  existing name in database.
     *
     * @param name name value
     */
    User getActiveName(String name);
}
