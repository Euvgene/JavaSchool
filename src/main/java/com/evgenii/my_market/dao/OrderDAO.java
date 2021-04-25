package com.evgenii.my_market.dao;

import com.evgenii.my_market.entity.Order;
import com.evgenii.my_market.entity.Parameters;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class OrderDAO {
    @PersistenceContext
    EntityManager entityManager;

    public Order saveOrder(Order order) {
        entityManager.persist(order);
        entityManager.flush();
        return order;
    }
}
