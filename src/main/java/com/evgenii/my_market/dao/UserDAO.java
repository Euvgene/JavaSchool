package com.evgenii.my_market.dao;

import com.evgenii.my_market.entity.Users;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Optional;

@Repository
public class UserDAO {

    @PersistenceContext
    EntityManager entityManager;

    public Optional<Users> findByUsername(String username) {
        TypedQuery<Users> query = entityManager.createQuery(
                "SELECT u FROM Users u WHERE" +
                        " u.firstName = :username ", Users.class);

        return Optional.of(query
                .setParameter("username", username)
                .getSingleResult());
    }
}
