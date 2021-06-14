package com.evgenii.my_market.rest_controller;

import com.evgenii.my_market.dto.*;
import com.evgenii.my_market.entity.Order;
import com.evgenii.my_market.exception_handling.ResourceNotFoundException;
import com.evgenii.my_market.service.api.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.math.BigInteger;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private static final String DEFAULT_VALUE_FROM_DATE = "1990-01-01";
    private static final String DEFAULT_VALUE_TO_DATE = "3000-01-01";

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDto createOrderFromCart(Principal principal, @Valid @RequestBody OrderConfirmDto order) {
        order.setUsername(principal.getName());
        Order newOrder = orderService.createFromUserCart(order);
        return new OrderDto(newOrder);
    }

    @GetMapping("/{uuid}")
    public OrderResultDto getOrderById(@PathVariable UUID uuid) {
        Order order = orderService.findById(uuid).orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        return new OrderResultDto(order);
    }

    @GetMapping
    public List<OrderDto> getCurrentUserOrders(@RequestParam(name = "page", defaultValue = "1") int page,
                                               @RequestParam(name = "first_date", defaultValue = DEFAULT_VALUE_FROM_DATE)
                                               @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
                                               @RequestParam(name = "second_date", defaultValue = DEFAULT_VALUE_TO_DATE)
                                               @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate,
                                               Principal principal) {

        return orderService.findAllOrdersByOwnerName(principal.getName(), fromDate, toDate, page);
    }

    @GetMapping("get-user-order-page-count")
    public BigInteger getCurrentUserOrdersCount(@RequestParam(name = "first_date", defaultValue = DEFAULT_VALUE_FROM_DATE)
                                                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
                                                @RequestParam(name = "second_date", defaultValue = DEFAULT_VALUE_TO_DATE)
                                                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate,
                                                Principal principal) {
        return orderService.getOrdersCountByOwnerName(principal.getName(), fromDate, toDate);
    }

    @GetMapping("get-all-order-page-count")
    public BigInteger getAllUserOrdersCount(@RequestParam(name = "first_date", defaultValue = DEFAULT_VALUE_FROM_DATE)
                                            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
                                            @RequestParam(name = "second_date", defaultValue = DEFAULT_VALUE_TO_DATE)
                                            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate,
                                            @RequestParam(name = "state", defaultValue = "") String state) {

        return orderService.getOrdersCount(fromDate, toDate, state);
    }

    @GetMapping("/all")
    public List<OrderDto> getAllOrders(@RequestParam(name = "page", defaultValue = "1") int page,
                                       @RequestParam(name = "first_date", defaultValue = DEFAULT_VALUE_FROM_DATE)
                                       @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
                                       @RequestParam(name = "second_date", defaultValue = DEFAULT_VALUE_TO_DATE)
                                       @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate,
                                       @RequestParam(name = "state", defaultValue = "") String state) {

        return orderService.findAllOrders(fromDate, toDate, page, state);
    }

    @GetMapping("/update")
    public void updateOrder(@RequestParam(name = "order_id") UUID orderId,
                            @RequestParam(name = "delivery_address") @Pattern(regexp = "^[-.,;:a-zA-Z0-9_ ]*$",
                                    message = "Invalid address format") @NotEmpty(message = "Please provide a country")
                                    String orderAddress,
                            @RequestParam(name = "state") String orderState) {

        orderService.updateOrder(orderId, orderAddress, orderState);
    }

    @GetMapping("/statistic")
    public List<StatisticDto> getStatistic(@RequestParam(name = "statistic_name") String statisticName,
                                           @RequestParam(name = "first_date", defaultValue = DEFAULT_VALUE_FROM_DATE)
                                           @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
                                           @RequestParam(name = "second_date", defaultValue = DEFAULT_VALUE_TO_DATE)
                                           @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate) {

        return orderService.getStatistic(statisticName, fromDate, toDate);
    }

    @GetMapping("/product-statistic")
    public List<ProductStatisticDto> getProductStatistic(
            @RequestParam(name = "first_date", defaultValue = DEFAULT_VALUE_FROM_DATE)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
            @RequestParam(name = "second_date", defaultValue = DEFAULT_VALUE_TO_DATE)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate) {

        return orderService.getProductStatistic(fromDate, toDate);
    }
}
