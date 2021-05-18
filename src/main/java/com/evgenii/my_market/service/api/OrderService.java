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

public interface OrderService {

    Order createFromUserCart(OrderConfirmDto orderConfirmDto);

    List<OrderDto> findAllOrdersByOwnerName(String username, LocalDate fromDate, LocalDate toDate, int page);

    Optional<Order> findById(UUID id);

    public List<OrderDto> findAllOrders(LocalDate fromDate, LocalDate toDate, int page, String state);

    void updateOrder(UUID orderId, String orderAddress, String orderState);

    List<StatisticDto> getStatistic(String statisticName, LocalDate fromDate, LocalDate toDate);

    List<ProductStatisticDto> getProductStatistic(LocalDate fromDate, LocalDate toDate);

    BigInteger getOrdersCountByOwnerName(String name, LocalDate fromDate, LocalDate toDate);

    BigInteger getOrdersCount(LocalDate fromDate, LocalDate toDate, String state);
}
