package com.evgenii.my_market.rest_controller;

import com.evgenii.my_market.dto.OrderConfirmDto;
import com.evgenii.my_market.dto.OrderDto;
import com.evgenii.my_market.dto.OrderResultDto;
import com.evgenii.my_market.dto.StatisticDto;
import com.evgenii.my_market.entity.Order;
import com.evgenii.my_market.exception_handling.ResourceNotFoundException;
import com.evgenii.my_market.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDto createOrderFromCart( Principal principal, @Valid @RequestBody OrderConfirmDto order) {
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
                                               @RequestParam(name = "first_date", defaultValue = "1990-01-01")
                                               @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
                                               @RequestParam(name = "second_date", defaultValue = "3000-01-01")
                                               @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate,
                                               Principal principal) {

        return orderService.findAllOrdersByOwnerName(principal.getName(), fromDate, toDate, page);
    }

    @GetMapping("/all")
    public List<OrderDto> getAllOrders(@RequestParam(name = "page", defaultValue = "1") int page,
                                       @RequestParam(name = "first_date", defaultValue = "1990-01-01")
                                       @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
                                       @RequestParam(name = "second_date", defaultValue = "3000-01-01")
                                       @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate,
                                       @RequestParam(name = "state", defaultValue = "") String state) {

        return orderService.findAllOrders(fromDate, toDate, page, state);
    }

    @GetMapping("/update")
    public void updateOrder(@RequestParam(name = "order_id") UUID orderId,
                            @RequestParam(name = "delivery_address")@Pattern(regexp = "^[-.,;:a-zA-Z0-9_ ]*$",
                                    message = "Invalid address format") @NotEmpty(message = "Please provide a country")
                                    String orderAddress,
                            @RequestParam(name = "state") String orderState) {

        orderService.updateOrder(orderId, orderAddress, orderState);
    }

    @GetMapping("/statistic")
    public List getStatistic(@RequestParam(name = "page", defaultValue = "1") int page,
                             @RequestParam(name = "statistic_name") String statisticName,
                             @RequestParam(name = "first_date", defaultValue = "1990-01-01")
                             @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
                             @RequestParam(name = "second_date", defaultValue = "3000-01-01")
                             @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate) {

        return orderService.getStatistic(statisticName, fromDate, toDate, page);
    }
}
