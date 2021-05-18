package com.evgenii.my_market.dao.api;

import com.evgenii.my_market.entity.Order;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface OrderDAO {

     Order saveOrder(Order order);

     Optional<Order> findById(UUID id) ;

     List<Order> findAllByOwnerUsername(String username, LocalDate fromDate, LocalDate toDate, int page, int total) ;

     List<Order> findAlL(LocalDate fromDate, LocalDate toDate, int page, String state, int total) ;

     List<Object[]> getStatistic(String statisticName, LocalDate fromDate, LocalDate toDate) ;

     List<Object[]> getProductStatistic(LocalDate fromDate, LocalDate toDate);

     BigInteger getOrdersCountByOwnerName(String name, LocalDate fromDate, LocalDate toDate) ;

     BigInteger getAllOrderCount(LocalDate fromDate, LocalDate toDate, String state) ;
}
