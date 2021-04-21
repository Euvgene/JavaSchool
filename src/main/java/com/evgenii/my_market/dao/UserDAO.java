package com.evgenii.my_market.dao;

import com.evgenii.my_market.entity.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserDAO {

   /* @PersistenceContext
    EntityManager entityManager;*/
    private final EntityManagerFactory entityManagerFactory;

    public Optional<Users> findByUsername(String username) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        TypedQuery<Users> query = entityManager.createQuery(
                "SELECT u FROM Users u WHERE" +
                        " u.firstName = :username ", Users.class);

        return Optional.of(query
                .setParameter("username", username)
                .getSingleResult());
    }

    public List<Users> findByUsernameAndEmail(String username, String email) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        TypedQuery<Users> query = entityManager.createQuery(
                "SELECT u FROM Users u WHERE" +
                        " u.firstName = :username  or u.email = :email", Users.class);

        return query
                .setParameter("username", username)
                .setParameter("email", email)
                .getResultList();
    }

    public List<Users> getAllUsers() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        TypedQuery<Users> query = entityManager.createQuery(
                "SELECT u FROM Users u ", Users.class);
        return query
                .getResultList();
    }

    public void saveUser(Users newUsers) {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(newUsers);
        entityManager.flush();
        entityManager.getTransaction().commit();

    }
}
