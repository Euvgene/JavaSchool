package com.evgenii.my_market.dao;

import com.evgenii.my_market.dao.api.OrderDAO;
import com.evgenii.my_market.entity.Order;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class OrderDAOImpl implements OrderDAO {
    @PersistenceContext
    EntityManager entityManager;

    public Order saveOrder(Order order) {
        entityManager.persist(order);
        return order;
    }

    public Optional<Order> findById(UUID id) {
        TypedQuery<Order> query = entityManager.createQuery(
                "SELECT o FROM Order o where o.id = :id", Order.class);
        try {
            return Optional.of(query
                    .setParameter("id", id)
                    .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }


    }

    public List<Order> findAllByOwnerUsername(String username, LocalDate fromDate, LocalDate toDate, int page, int total) {
        TypedQuery<Order> query = entityManager.createQuery(
                "SELECT o FROM Order o WHERE" +
                        " o.createdAt >= :from_date and o.createdAt <= :to_date and o.owner.firstName = :username" +
                        " order by o.createdAt desc ", Order.class)
                .setFirstResult(page)
                .setMaxResults(total);
        return query
                .setParameter("from_date", fromDate)
                .setParameter("to_date", toDate)
                .setParameter("username", username)
                .getResultList();
    }

    public List<Order> findAlL(LocalDate fromDate, LocalDate toDate, int page, String state, int total) {
        String myQuery;
        if ("Active".equals(state)) {
            myQuery = "SELECT o FROM Order o WHERE" +
                    " o.createdAt >= :from_date and o.createdAt <= :to_date and o.orderState <> 'DELIVERED'" +
                    " and o.orderState <> 'RETURN'" +
                    " order by o.createdAt desc";
        } else if ("Delivered".equals(state)) {
            myQuery = "SELECT o FROM Order o WHERE" +
                    " o.createdAt >= :from_date and o.createdAt <= :to_date and o.orderState = 'DELIVERED'" +
                    " order by o.createdAt desc";
        } else if ("Return".equals(state)) {
            myQuery = "SELECT o FROM Order o WHERE" +
                    " o.createdAt >= :from_date and o.createdAt <= :to_date and o.orderState = 'RETURN'" +
                    " order by o.createdAt desc";
        } else {
            myQuery = "SELECT o FROM Order o WHERE" +
                    " o.createdAt >= :from_date and o.createdAt <= :to_date" +
                    " order by o.createdAt desc ";
        }
        TypedQuery<Order> query = entityManager.createQuery(
                myQuery, Order.class)
                .setFirstResult(page)
                .setMaxResults(total);
        return query
                .setParameter("from_date", fromDate)
                .setParameter("to_date", toDate)
                .getResultList();
    }

    public List<Object[]> getStatistic(String statisticName, LocalDate fromDate, LocalDate toDate) {
        String myQuery = null;
        switch (statisticName) {
            case "User":
                myQuery = "SELECT first_name name,sum(price) as count " +
                        "FROM orders o INNER JOIN users u ON o.owner_id = u.user_id where  o.created_at >= :from_date and o.created_at <= :to_date " +
                        "and o.order_state <> 'RETURN' " +
                        "GROUP BY owner_id asc limit 10;";
                break;
            case "Proceeds":
                myQuery = "SELECT payment_method name, sum(price) as count " +
                        " FROM orders o where payment_state = 1 and o.created_at >= :from_date and o.created_at <= :to_date " +
                        " and o.order_state <> 'RETURN' group by payment_method asc;";
                break;
        }

        Query query = entityManager.createNativeQuery(myQuery)
                .unwrap(org.hibernate.query.NativeQuery.class)
                .addScalar("name", StringType.INSTANCE)
                .addScalar("count", IntegerType.INSTANCE)
                .setParameter("from_date", fromDate)
                .setParameter("to_date", toDate);
        return query.getResultList();
    }

    public List<Object[]> getProductStatistic(LocalDate fromDate, LocalDate toDate) {
        Query query = entityManager.createNativeQuery("SELECT p.productTitle name,sum(o.quantity) as count, sum(o.price) as price " +
                "FROM order_items o INNER JOIN products p ON o.product_id = p.product_id where  o.created_at >= :from_date and o.created_at <= :to_date + INTERVAL 1 DAY " +
                " and o.order_state <> 'RETURN' " +
                "GROUP BY p.productTitle " +
                "order by count desc  limit 10 ")
                .unwrap(org.hibernate.query.NativeQuery.class)
                .addScalar("name", StringType.INSTANCE)
                .addScalar("count", IntegerType.INSTANCE)
                .addScalar("price", IntegerType.INSTANCE)
                .setParameter("from_date", fromDate)
                .setParameter("to_date", toDate);
        return query.getResultList();
    }

    public BigInteger getOrdersCountByOwnerName(String name, LocalDate fromDate, LocalDate toDate) {
        Query query = entityManager.createNativeQuery(
                " SELECT COUNT(*) FROM  orders o INNER JOIN users u ON  o.owner_id = u.user_id  where" +
                        "  o.created_at >= :from_date and o.created_at <= :to_date and first_name = :username");

        return (BigInteger) query
                .setParameter("username", name)
                .setParameter("from_date", fromDate)
                .setParameter("to_date", toDate)
                .getSingleResult();
    }

    public BigInteger getAllOrderCount(LocalDate fromDate, LocalDate toDate, String state) {
        String myQuery;
        if ("Active".equals(state)) {
            myQuery = " SELECT COUNT(*) FROM  orders o where" +
                    "  o.created_at >= :from_date and o.created_at <= :to_date and o.order_state <> 'DELIVERED'" +
                    "  and o.order_state <> 'RETURN'";
        } else if ("Delivered".equals(state)) {
            myQuery = " SELECT COUNT(*) FROM  orders o where" +
                    "  o.created_at >= :from_date and o.created_at <= :to_date and o.order_state = 'DELIVERED'";
        } else if ("Return".equals(state)) {
            myQuery = " SELECT COUNT(*) FROM  orders o where" +
                    "  o.created_at >= :from_date and o.created_at <= :to_date and o.order_state = 'RETURN'";
        } else {
            myQuery = "SELECT COUNT(*) FROM orders o WHERE" +
                    " o.created_at >= :from_date and o.created_at <= :to_date";
        }
        Query query = entityManager.createNativeQuery(
                myQuery);

        return (BigInteger) query
                .setParameter("from_date", fromDate)
                .setParameter("to_date", toDate)
                .getSingleResult();
    }
}
