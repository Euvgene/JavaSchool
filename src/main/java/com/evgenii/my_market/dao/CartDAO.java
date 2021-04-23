package com.evgenii.my_market.dao;

import com.evgenii.my_market.entity.Cart;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class CartDAO {
    @PersistenceContext
    private EntityManager entityManager;

    public void delete(Cart cart) {
        entityManager.remove(cart);
         entityManager.flush();
    }

    public Cart save(Cart cart) {
        entityManager.persist(cart);
        entityManager.flush();

        return cart;
    }

    public Optional<Cart> findById(UUID id) {
        TypedQuery<Cart> query = entityManager.createQuery(
                "SELECT c FROM Cart c where c.cartId = :id", Cart.class);
        return Optional.of(query
                .setParameter("id", id)
                .getSingleResult());
    }

    public List<Cart> findByUserId(int id) {
        TypedQuery<Cart> query = entityManager.createQuery(
                "SELECT c FROM Cart c where c.user.userId= :id", Cart.class);
        return query
                .setParameter("id", id)
                .getResultList();
    }
}
