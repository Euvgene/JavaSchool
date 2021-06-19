package com.evgenii.my_market.rest_controller;

import com.evgenii.my_market.dto.*;
import com.evgenii.my_market.entity.Order;
import com.evgenii.my_market.exception_handling.ResourceNotFoundException;
import com.evgenii.my_market.service.api.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.math.BigInteger;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * Rest controller for all possible actions with order.
 *
 * @author Boznyakov Evgenii
 */
@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private static final String DEFAULT_VALUE_FROM_DATE = "1990-01-01";
    private static final String DEFAULT_VALUE_TO_DATE = "3000-01-01";

    /**
     * Method responsible for creating order from cart
     *
     * @param principal user name
     * @param order     {@linkplain com.evgenii.my_market.dto.OrderConfirmDto}
     * @return {@linkplain com.evgenii.my_market.dto.OrderDto}
     */
    @PostMapping
    public OrderDto createOrderFromCart(Principal principal, @Valid @RequestBody OrderConfirmDto order) {
        order.setUsername(principal.getName());
        Order newOrder = orderService.createFromUserCart(order);
        return new OrderDto(newOrder);
    }

    /**
     * Method responsible for getting order by order id
     *
     * @param uuid order id
     * @return {@linkplain com.evgenii.my_market.dto.OrderResultDto}
     */
    @GetMapping("/result")
    public OrderResultDto getOrderById(@RequestParam(name = "uuid") UUID uuid) {
        Order order = orderService.findById(uuid).orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        return new OrderResultDto(order);
    }

    /**
     * Method responsible for getting order list for user
     *
     * @param principal user name
     * @param page      page number
     * @param fromDate  since which date get orders
     * @param toDate    to date get orders
     * @return List<OrderDto>
     */
    @GetMapping
    public List<OrderDto> getCurrentUserOrders(@RequestParam(name = "page", defaultValue = "1") int page,
                                               @RequestParam(name = "first_date", defaultValue = DEFAULT_VALUE_FROM_DATE)
                                               @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
                                               @RequestParam(name = "second_date", defaultValue = DEFAULT_VALUE_TO_DATE)
                                               @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate,
                                               Principal principal) {

        return orderService.findAllOrdersByOwnerName(principal.getName(), fromDate, toDate, page);
    }

    /**
     * Method responsible for getting count of current user orders
     *
     * @param principal user name
     * @param fromDate  since which date get count
     * @param toDate    to date get count
     * @return BigInteger
     */
    @GetMapping("get-user-order-page-count")
    public BigInteger getCurrentUserOrdersCount(@RequestParam(name = "first_date", defaultValue = DEFAULT_VALUE_FROM_DATE)
                                                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
                                                @RequestParam(name = "second_date", defaultValue = DEFAULT_VALUE_TO_DATE)
                                                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate,
                                                Principal principal) {
        return orderService.getOrdersCountByOwnerName(principal.getName(), fromDate, toDate);
    }

    /**
     * Method responsible for getting count of all orders
     *
     * @param fromDate since which date get count
     * @param toDate   to date get count
     * @return BigInteger
     */
    @GetMapping("get-all-order-page-count")
    public BigInteger getAllUserOrdersCount(@RequestParam(name = "first_date", defaultValue = DEFAULT_VALUE_FROM_DATE)
                                            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
                                            @RequestParam(name = "second_date", defaultValue = DEFAULT_VALUE_TO_DATE)
                                            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate,
                                            @RequestParam(name = "state", defaultValue = "") String state) {

        return orderService.getOrdersCount(fromDate, toDate, state);
    }

    /**
     * Method responsible for getting order list of all orders
     *
     * @param fromDate since which date get orders
     * @param toDate   to date get orders
     * @param state    order state
     * @return List<OrderDto>
     */
    @GetMapping("/all")
    public List<OrderDto> getAllOrders(@RequestParam(name = "page", defaultValue = "1") int page,
                                       @RequestParam(name = "first_date", defaultValue = DEFAULT_VALUE_FROM_DATE)
                                       @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
                                       @RequestParam(name = "second_date", defaultValue = DEFAULT_VALUE_TO_DATE)
                                       @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate,
                                       @RequestParam(name = "state", defaultValue = "") String state) {

        return orderService.findAllOrders(fromDate, toDate, page, state);
    }

    /**
     * Method responsible for updating order
     *
     * @param orderId      order id
     * @param orderAddress new delivery address
     * @param orderState   new order state
     */
    @GetMapping("/update")
    public void updateOrder(@RequestParam(name = "order_id") UUID orderId,
                            @RequestParam(name = "delivery_address") @Pattern(regexp = "^[-.,;:a-zA-Z0-9_ ]*$",
                                    message = "Invalid address format") @NotEmpty(message = "Please provide a country")
                                    String orderAddress,
                            @RequestParam(name = "state") String orderState) {

        orderService.updateOrder(orderId, orderAddress, orderState);
    }

    /**
     * Method responsible for get statistic
     *
     * @param statisticName statistic name
     * @param fromDate      since which date get statistic
     * @param toDate        to date get statistic
     * @return List<StatisticDto>
     */
    @GetMapping("/statistic")
    public List<StatisticDto> getStatistic(@RequestParam(name = "statistic_name") String statisticName,
                                           @RequestParam(name = "first_date", defaultValue = DEFAULT_VALUE_FROM_DATE)
                                           @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
                                           @RequestParam(name = "second_date", defaultValue = DEFAULT_VALUE_TO_DATE)
                                           @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate) {

        return orderService.getStatistic(statisticName, fromDate, toDate);
    }

    /**
     * Method responsible for get product statistic
     *
     * @param fromDate since which date get statistic
     * @param toDate   to date get statistic
     * @return List<ProductStatisticDto>
     */
    @GetMapping("/product-statistic")
    public List<ProductStatisticDto> getProductStatistic(
            @RequestParam(name = "first_date", defaultValue = DEFAULT_VALUE_FROM_DATE)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
            @RequestParam(name = "second_date", defaultValue = DEFAULT_VALUE_TO_DATE)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate) {

        return orderService.getProductStatistic(fromDate, toDate);
    }
}
