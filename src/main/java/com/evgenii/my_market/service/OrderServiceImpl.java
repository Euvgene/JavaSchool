package com.evgenii.my_market.service;

import com.evgenii.my_market.dao.api.OrderDAO;
import com.evgenii.my_market.dto.OrderConfirmDto;
import com.evgenii.my_market.dto.OrderDto;
import com.evgenii.my_market.dto.ProductStatisticDto;
import com.evgenii.my_market.dto.StatisticDto;
import com.evgenii.my_market.entity.*;
import com.evgenii.my_market.service.api.CartService;
import com.evgenii.my_market.service.api.OrderService;
import com.evgenii.my_market.service.api.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderDAO orderDAO;
    private final CartService cartService;
    private final UserService userService;
    private final Logger LOGGER = LoggerFactory.getLogger(OrderService.class);
    private final int TOTAL_ORDERS_IN_PAGE = 8;
    private final int CHECK_PAGE_NUMBER = 1;

    @Transactional
    public Order createFromUserCart(OrderConfirmDto orderConfirmDto) {
        boolean paymentState = true;
        User user = userService.findByUsername(orderConfirmDto.getUsername()).get();
        Cart cart = cartService.findById(UUID.fromString(orderConfirmDto.getCartId()));
        if (orderConfirmDto.getPaymentMethod().equals("cash")) paymentState = false;
        Order order = new Order(cart, user, user.getUserAddress(), orderConfirmDto.getAddress(), orderConfirmDto.getPaymentMethod(), paymentState);
        order.setOrderState(StateEnum.AWAITING_SHIPMENT);
        order = orderDAO.saveOrder(order);
        cart.getCartItems().forEach(cartItem -> cartItem.getProduct().decrementQuantityProduct(cartItem.getQuantity()));
        cartService.clearCart(UUID.fromString(orderConfirmDto.getCartId()));
        LOGGER.info("Create order with id "  + order.getId());
        return order;
    }

    public List<OrderDto> findAllOrdersByOwnerName(String username, LocalDate fromDate, LocalDate toDate, int page) {
        int total = TOTAL_ORDERS_IN_PAGE;
        return orderDAO.findAllByOwnerUsername(username, fromDate, toDate, getPage(page, total), total).stream().map(OrderDto::new).collect(Collectors.toList());
    }

    public Optional<Order> findById(UUID id) {
        return orderDAO.findById(id);
    }

    public List<OrderDto> findAllOrders(LocalDate fromDate, LocalDate toDate, int page, String state) {
        int total = TOTAL_ORDERS_IN_PAGE;
        return orderDAO.findAlL(fromDate, toDate, getPage(page, total), state, total).stream().map(OrderDto::new).collect(Collectors.toList());
    }

    @Transactional
    public void updateOrder(UUID orderId, String orderAddress, String orderState) {
        Order order = orderDAO.findById(orderId).get();
        if (orderState.equals("DELIVERED")) {
            order.setPaymentState(true);
        } else if (orderState.equals("RETURN")) {
            incrementProducts(order.getItems());
        }
        order.setOrderState(StateEnum.valueOf(orderState));
        order.setDeliveryMethode(orderAddress);
        LOGGER.info("Update order with  id " + order.getId());
    }

    private void incrementProducts(List<OrderItem> items) {
        for (OrderItem i : items) {
            i.getProduct().incrementQuantityProduct((byte) i.getQuantity());
        }
    }

    public List<StatisticDto> getStatistic(String statisticName, LocalDate fromDate, LocalDate toDate) {
        return orderDAO.getStatistic(statisticName, fromDate, toDate).stream().map(StatisticDto::new).collect(Collectors.toList());
    }

    public List<ProductStatisticDto> getProductStatistic(LocalDate fromDate, LocalDate toDate) {
        return orderDAO.getProductStatistic( fromDate, toDate).stream().map(ProductStatisticDto::new).collect(Collectors.toList());
    }



    private int getPage(int page, int total) {
        if (page != CHECK_PAGE_NUMBER) {
            page = (page - CHECK_PAGE_NUMBER) * total + CHECK_PAGE_NUMBER;
            return page - CHECK_PAGE_NUMBER;
        }
        return 0;
    }

    public BigInteger getOrdersCountByOwnerName(String name, LocalDate fromDate, LocalDate toDate) {
        return orderDAO.getOrdersCountByOwnerName(name,fromDate,toDate);
    }

    public BigInteger getOrdersCount(LocalDate fromDate, LocalDate toDate, String state) {
        return orderDAO.getAllOrderCount(fromDate,toDate, state);
    }
}
