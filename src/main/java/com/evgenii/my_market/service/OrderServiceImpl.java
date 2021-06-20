package com.evgenii.my_market.service;

import com.evgenii.my_market.config.MessageSender;
import com.evgenii.my_market.dao.api.OrderDAO;
import com.evgenii.my_market.dto.OrderConfirmDto;
import com.evgenii.my_market.dto.OrderDto;
import com.evgenii.my_market.dto.ProductStatisticDto;
import com.evgenii.my_market.dto.StatisticDto;
import com.evgenii.my_market.entity.*;
import com.evgenii.my_market.exception_handling.ResourceNotFoundException;
import com.evgenii.my_market.service.api.CartService;
import com.evgenii.my_market.service.api.CategoryService;
import com.evgenii.my_market.service.api.OrderService;
import com.evgenii.my_market.service.api.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Implementation of {@link OrderService} interface.
 *
 * @author Boznyakov Evgenii
 */
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    /**
     * Creates an instance of this class using constructor-based dependency injection.
     */
    private final OrderDAO orderDAO;
    private final CartService cartService;
    private final MessageSender messageSender;
    private final UserService userService;
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);
    private static final int TOTAL_ORDERS_IN_PAGE = 8;

    /**
     * Crate order from user cart and send update message to rabbitMq que.
     *
     * @param orderConfirmDto {@linkplain com.evgenii.my_market.dto.OrderConfirmDto}
     * @return {@linkplain com.evgenii.my_market.entity.Order Order}
     */
    @Transactional
    public Order createFromUserCart(OrderConfirmDto orderConfirmDto) {
        boolean paymentState = true;
        User user = userService.findByUsername(orderConfirmDto.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User '%s' not found", orderConfirmDto.getUsername())));
        Cart cart = cartService.findById(UUID.fromString(orderConfirmDto.getCartId()));
        if (orderConfirmDto.getPaymentMethod().equals("cash")) {
            paymentState = false;
        }
        Order order = orderDAO.saveOrder(new Order(cart, user, user.getUserAddress(), orderConfirmDto.getAddress(),
                orderConfirmDto.getPaymentMethod(), paymentState, StateEnum.AWAITING_SHIPMENT));
        cart.getCartItems().forEach(cartItem -> cartItem.getProduct().decrementQuantityProduct(cartItem.getQuantity()));
        cartService.clearCart(UUID.fromString(orderConfirmDto.getCartId()));
        LOGGER.info("Create order with id - {} ", order.getId());
        messageSender.send("update");
        return order;
    }

    /**
     * Find all orders by owner
     *
     * @param username user name
     * @param page     page number
     * @param fromDate since which date get orders
     * @param toDate   to date get orders
     * @return list of {@linkplain com.evgenii.my_market.dto.OrderDto OrderDto}
     */
    public List<OrderDto> findAllOrdersByOwnerName(String username, LocalDate fromDate, LocalDate toDate, int page) {
        int total = TOTAL_ORDERS_IN_PAGE;
        return orderDAO.findAllByOwnerUsername(username, fromDate, toDate, getPage(page, total), total).stream().map(OrderDto::new).collect(Collectors.toList());
    }

    /**
     * Find  order by id
     *
     * @param id order id
     * @return list of {@linkplain com.evgenii.my_market.entity.Order Order}
     */
    public Optional<Order> findById(UUID id) {
        return orderDAO.findById(id);
    }

    /**
     * Find  all orders
     *
     * @param page     page number
     * @param fromDate since which date get orders
     * @param toDate   to date get orders
     * @return list of {@linkplain com.evgenii.my_market.dto.OrderDto OrderDto}
     */
    public List<OrderDto> findAllOrders(LocalDate fromDate, LocalDate toDate, int page, String state) {
        int total = TOTAL_ORDERS_IN_PAGE;
        return orderDAO.findAlL(fromDate, toDate, getPage(page, total), state, total).stream().map(OrderDto::new).collect(Collectors.toList());
    }

    /**
     * Update order state and delivery address.
     * If new state become "delivered" set payment state true.
     * if new state "return" increases product quantity by the amount of order item quantity.
     *
     * @param orderId      order id
     * @param orderAddress new delivery address
     * @param orderState   new order state
     */
    @Transactional
    public void updateOrder(UUID orderId, String orderAddress, String orderState) {
        Order order = orderDAO.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order with id " + orderId + " not found"));
        if (orderState.equals("DELIVERED")) {
            order.setPaymentState(true);
        } else if (orderState.equals("RETURN")) {
            incrementProducts(order.getItems());
        }
        order.setOrderState(StateEnum.valueOf(orderState));
        order.setDeliveryMethode(orderAddress);
        LOGGER.info("Update order with  id - {}", order.getId());
    }

    /**
     * Increases product quantity by the amount of order item quantity.
     * Send update message to rabbitMq que.
     *
     * @param items list of {@linkplain com.evgenii.my_market.entity.OrderItem OrderItem}
     */
    private void incrementProducts(List<OrderItem> items) {
        for (OrderItem i : items) {
            i.getProduct().incrementQuantityProduct((byte) i.getQuantity());
            i.setOrderState(StateEnum.RETURN);
        }
        messageSender.send("update");
    }

    /**
     * Get statistic by statistic name
     *
     * @param statisticName statistic name
     * @param fromDate      since which date get statistic
     * @param toDate        to date get statistic
     * @return list of {@linkplain com.evgenii.my_market.dto.StatisticDto StatisticDto}
     */
    public List<StatisticDto> getStatistic(String statisticName, LocalDate fromDate, LocalDate toDate) {
        return orderDAO.getStatistic(statisticName, fromDate, toDate).stream().map(StatisticDto::new).collect(Collectors.toList());
    }

    /**
     * Get product statistic
     *
     * @param fromDate since which date get statistic
     * @param toDate   to date get statistic
     * @return list of {@linkplain com.evgenii.my_market.dto.ProductStatisticDto ProductStatisticDto}
     */
    public List<ProductStatisticDto> getProductStatistic(LocalDate fromDate, LocalDate toDate) {
        return orderDAO.getProductStatistic(fromDate, toDate).stream().map(ProductStatisticDto::new).collect(Collectors.toList());
    }

    /**
     * Generate page index
     *
     * @param page number of page
     * @param total max result to find
     * @return page index
     */
    private int getPage(int page, int total) {
        int firstPage = 1;
        if (page != firstPage) {
            page = (page - firstPage) * total + firstPage;
            return page - firstPage;
        }
        return 0;
    }

    /**
     * Getting count of current user orders
     *
     * @param name user name
     * @param fromDate  since which date get count
     * @param toDate    to date get count
     * @return BigInteger
     */
    public BigInteger getOrdersCountByOwnerName(String name, LocalDate fromDate, LocalDate toDate) {
        return orderDAO.getOrdersCountByOwnerName(name, fromDate, toDate);
    }

    /**
     * Getting count of all orders
     *
     * @param fromDate since which date get count
     * @param toDate   to date get count
     * @return BigInteger
     */
    public BigInteger getOrdersCount(LocalDate fromDate, LocalDate toDate, String state) {
        return orderDAO.getAllOrderCount(fromDate, toDate, state);
    }
}
