package com.evgenii.my_market.dao;

import com.evgenii.my_market.entity.Cart;
import com.evgenii.my_market.entity.Order;
import com.evgenii.my_market.entity.Parameters;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Optional;
import java.util.UUID;

@Repository
public class OrderDAO {
    @PersistenceContext
    EntityManager entityManager;

    public Order saveOrder(Order order) {
        entityManager.persist(order);
        entityManager.flush();
        return order;
    }

    public Optional<Order> findById(UUID id) {
        TypedQuery<Order> query = entityManager.createQuery(
                "SELECT o FROM Order o where o.id = :id", Order.class);
        return Optional.of(query
                .setParameter("id", id)
                .getSingleResult());
    }
}
