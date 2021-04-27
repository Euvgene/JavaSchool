package com.evgenii.my_market.rest_controllers;

import com.evgenii.my_market.dto.OrderDto;
import com.evgenii.my_market.dto.OrderResultDto;
import com.evgenii.my_market.entity.Order;
import com.evgenii.my_market.exception_handling.ResourceNotFoundException;
import com.evgenii.my_market.services.CartService;
import com.evgenii.my_market.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final CartService cartService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDto createOrderFromCart(@RequestParam(name = "username") String userName,
                                        @RequestParam(name = "uuid") UUID cartUuid,
                                        @RequestParam(name = "address", defaultValue = "from store") String address,
                                        @RequestParam(name = "paymentMethod", defaultValue = "cash") String paymentMethod,
                                        @RequestParam(name = "paymentState", defaultValue = "false") boolean paymentState) {

        Order order = orderService.createFromUserCart(userName, cartUuid, address, paymentMethod, paymentState);
        cartService.clearCart(cartUuid);
        return new OrderDto(order);
    }

    @GetMapping("/{uuid}")
    public OrderResultDto getOrderById(@PathVariable UUID uuid) {
        Order order = orderService.findById(uuid).orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        return new OrderResultDto(order);
    }

    @GetMapping
    public List<OrderDto> getCurrentUserOrders(@RequestParam(name = "p", defaultValue = "1") int page,
                                               @RequestParam(name = "first_date", defaultValue = "1990-01-01")
                                               @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
                                               @RequestParam(name = "second_date", defaultValue = "3000-01-01")
                                               @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate,
                                               Principal principal) {

        return orderService.findAllOrdersByOwnerName(principal.getName(), fromDate, toDate, page);
    }

}
