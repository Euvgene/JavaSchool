package com.evgenii.my_market.dao;

import com.evgenii.my_market.entity.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
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

    public List<Order> findAllByOwnerUsername(String username, LocalDate fromDate, LocalDate toDate, int page, int total ) {
        TypedQuery<Order> query = entityManager.createQuery(
                "SELECT o FROM Order o WHERE" +
                        " o.createdAt >= :from_date and o.createdAt <= :to_date and o.owner.firstName = :username" +
                        " order by o.createdAt ", Order.class)
                .setFirstResult(page)
                .setMaxResults(total);
        return query
                .setParameter("from_date", fromDate)
                .setParameter("to_date", toDate)
                .setParameter("username", username)
                .getResultList();
    }
}
