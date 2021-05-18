package com.evgenii.my_market.dao.api;

import com.evgenii.my_market.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserDAO {

    Optional<User> findByUsername(String username);

    List<User> findByUsernameAndEmail(String username, String email);

    void saveUser(User newUsers);

    void update(User user);

    User getActiveEmail(String email);

    User getActiveName(String name);
}
