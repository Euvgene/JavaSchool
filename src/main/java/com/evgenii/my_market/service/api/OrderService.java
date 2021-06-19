package com.evgenii.my_market.service.api;

import com.evgenii.my_market.dto.OrderConfirmDto;
import com.evgenii.my_market.dto.OrderDto;
import com.evgenii.my_market.dto.ProductStatisticDto;
import com.evgenii.my_market.dto.StatisticDto;
import com.evgenii.my_market.entity.Order;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Interface, containing list of required business-logic methods regarding
 * {@linkplain com.evgenii.my_market.entity.Order Order} entity.
 *
 * @author Boznyakov Evgenii
 */
public interface OrderService {

    /**
     * Crate order from user cart
     *
     * @param orderConfirmDto {@linkplain com.evgenii.my_market.dto.OrderConfirmDto}
     * @return {@linkplain com.evgenii.my_market.entity.Order Order}
     */
    Order createFromUserCart(OrderConfirmDto orderConfirmDto);

    /**
     * Find all orders by owner
     *
     * @param username user name
     * @param page     page number
     * @param fromDate since which date get orders
     * @param toDate   to date get orders
     * @return list of {@linkplain com.evgenii.my_market.dto.OrderDto OrderDto}
     */
    List<OrderDto> findAllOrdersByOwnerName(String username, LocalDate fromDate, LocalDate toDate, int page);

    /**
     * Find  order by id
     *
     * @param id order id
     * @return list of {@linkplain com.evgenii.my_market.entity.Order Order}
     */
    Optional<Order> findById(UUID id);

    /**
     * Find  all orders
     *
     * @param page     page number
     * @param fromDate since which date get orders
     * @param toDate   to date get orders
     * @return list of {@linkplain com.evgenii.my_market.dto.OrderDto OrderDto}
     */
    List<OrderDto> findAllOrders(LocalDate fromDate, LocalDate toDate, int page, String state);

    /**
     * Update order
     *
     * @param orderId      order id
     * @param orderAddress new delivery address
     * @param orderState   new order state
     */
    void updateOrder(UUID orderId, String orderAddress, String orderState);

    /**
     * Get statistic by statistic name
     *
     * @param statisticName statistic name
     * @param fromDate      since which date get statistic
     * @param toDate        to date get statistic
     * @return list of {@linkplain com.evgenii.my_market.dto.StatisticDto StatisticDto}
     */
    List<StatisticDto> getStatistic(String statisticName, LocalDate fromDate, LocalDate toDate);

    /**
     * Get product statistic
     *
     * @param fromDate since which date get statistic
     * @param toDate   to date get statistic
     * @return list of {@linkplain com.evgenii.my_market.dto.ProductStatisticDto ProductStatisticDto}
     */
    List<ProductStatisticDto> getProductStatistic(LocalDate fromDate, LocalDate toDate);

    /**
     * Getting count of current user orders
     *
     * @param name user name
     * @param fromDate  since which date get count
     * @param toDate    to date get count
     * @return BigInteger
     */
    BigInteger getOrdersCountByOwnerName(String name, LocalDate fromDate, LocalDate toDate);

    /**
     * Getting count of all orders
     *
     * @param fromDate since which date get count
     * @param toDate   to date get count
     * @return BigInteger
     */
    BigInteger getOrdersCount(LocalDate fromDate, LocalDate toDate, String state);
}
