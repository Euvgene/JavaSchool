package com.evgenii.my_market.dao;

import com.evgenii.my_market.dto.UserDto;
import com.evgenii.my_market.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
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
                        " u.firstName = :username  and u.email = :email", User.class);

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
    }

    public List<User> findByUsernameAndPassword(String oldPassword, String username) {
        TypedQuery<User> query = entityManager.createQuery(
                "SELECT u FROM User u WHERE" +
                        " u.password = :password and u.firstName = :username", User.class);

        return query
                .setParameter("password", oldPassword)
                .setParameter("username", username)
                .getResultList();
    }
}
