package com.evgenii.my_market.dao;

import com.evgenii.my_market.dao.api.CartDao;
import com.evgenii.my_market.entity.Cart;
import com.evgenii.my_market.exception_handling.ResourceNotFoundException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.UUID;

@Repository
public class CartDaoImpl implements CartDao {
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

    public Cart findById(UUID id) {
        TypedQuery<Cart> query = entityManager.createQuery(
                "SELECT c FROM Cart c where c.cartId = :id", Cart.class);
        try{
            return  query
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException e){
            throw new ResourceNotFoundException("Unable to find cart with id: " + id);
        }
    }

    public List<Cart> findByUserId(int id) {
        TypedQuery<Cart> query = entityManager.createQuery(
                "SELECT c FROM Cart c where c.user.userId= :id", Cart.class);
        return query
                .setParameter("id", id)
                .getResultList();
    }
}
