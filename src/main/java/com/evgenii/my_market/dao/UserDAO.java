package com.evgenii.my_market.dao;

import com.evgenii.my_market.entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDAO {

    @PersistenceContext
    EntityManager entityManager;

    public Optional<User> findByUsername(String username) {

        TypedQuery<User> query = entityManager.createQuery(
                "SELECT u FROM User u WHERE" +
                        " u.firstName = :username ", User.class);

        return Optional.of(query
                .setParameter("username", username)
                .getSingleResult());
    }

    public List<User> findByUsernameAndEmail(String username, String email) {

        TypedQuery<User> query = entityManager.createQuery(
                "SELECT u FROM User u WHERE" +
                        " u.firstName = :username  or u.email = :email", User.class);

        return query
                .setParameter("username", username)
                .setParameter("email", email)
                .getResultList();
    }

    public List<User> getAllUsers() {

        TypedQuery<User> query = entityManager.createQuery(
                "SELECT u FROM User u ", User.class);
        return query
                .getResultList();
    }

    public void saveUser(User newUsers) {
        entityManager.persist(newUsers);
        entityManager.flush();

    }

    public void update(User user) {
        entityManager.merge(user);
        entityManager.flush();
    }

    public User getActiveEmail(String email) {
        User user;
        try {
            user = (User) entityManager.createQuery("SELECT u FROM User u where u.email = :email")
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException e) {
            user = null;
        }
        return user;
    }

    public User getActiveName(String name) {
        User user;
        try {
            user = (User) entityManager.createQuery("SELECT u FROM User u where u.firstName = :name")
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException e) {
            user = null;
        }
        return user;
    }
}
