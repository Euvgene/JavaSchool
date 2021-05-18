package com.evgenii.my_market.dao;

import com.evgenii.my_market.dao.api.CartItemDAO;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class CartItemDAOImpl implements CartItemDAO {
    @PersistenceContext
    EntityManager entityManager;


    public void deleteCartItem(int id) {
        entityManager.createQuery("delete from CartItem ci where ci.id=:cartItemId ")
                .setParameter("cartItemId", id)
                .executeUpdate();
    }
}
