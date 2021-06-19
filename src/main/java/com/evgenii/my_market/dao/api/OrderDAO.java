package com.evgenii.my_market.dao.api;

import com.evgenii.my_market.entity.Order;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Interface for basic CRUD operations in Order entity.
 *
 * @author Boznyakov Evgenii
 */
public interface OrderDAO {
    /**
     * Save entity to database.
     *
     * @param order entity object to save
     */
    Order saveOrder(Order order);

    /**
     * Find by id entity from database.
     *
     * @param id which order to find
     */
    Optional<Order> findById(UUID id);

    /**
     * Find all entity from database.
     *
     * @param username name of owner
     * @param fromDate since date to find
     * @param toDate to date to find
     * @param page number of page
     * @param total max result to find
     */
    List<Order> findAllByOwnerUsername(String username, LocalDate fromDate, LocalDate toDate, int page, int total);

    /**
     * Find all entity from database.
     *
     * @param state state of order
     * @param fromDate since date to find
     * @param toDate to date to find
     * @param page number of page
     * @param total max result to find
     */
    List<Order> findAlL(LocalDate fromDate, LocalDate toDate, int page, String state, int total);

    /**
     * Get statistic from database.
     *
     * @param statisticName which statistic get
     * @param fromDate since date to find
     * @param toDate to date to find
     */
    List<Object[]> getStatistic(String statisticName, LocalDate fromDate, LocalDate toDate);

    /**
     * Get product statistic from database.
     *
     * @param fromDate since date to find
     * @param toDate to date to find
     */
    List<Object[]> getProductStatistic(LocalDate fromDate, LocalDate toDate);

    /**
     * Get count of order by owner name.
     *
     * @param name owner name of orders
     * @param fromDate since date to find
     * @param toDate to date to find
     */
    BigInteger getOrdersCountByOwnerName(String name, LocalDate fromDate, LocalDate toDate);

    /**
     * Get count of all orders.
     *
     * @param state state of orders
     * @param fromDate since date to find
     * @param toDate to date to find
     */
    BigInteger getAllOrderCount(LocalDate fromDate, LocalDate toDate, String state);
}
