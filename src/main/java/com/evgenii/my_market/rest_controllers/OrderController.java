package com.evgenii.my_market.rest_controllers;

import com.evgenii.my_market.dto.OrderDto;
import com.evgenii.my_market.entity.Order;
import com.evgenii.my_market.services.CartService;
import com.evgenii.my_market.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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
    public OrderDto createOrderFromCart(@RequestParam String userName,
                                        @RequestParam UUID cartUuid,
                                        @RequestParam(defaultValue = "from store") String address,
                                        @RequestParam(defaultValue = "cash" ) String paymentMethod,
                                        @RequestParam(defaultValue = "false") boolean paymentState) {
        Order order = orderService.createFromUserCart(userName, cartUuid, address,paymentMethod,paymentState);
        cartService.clearCart(cartUuid);
        return new OrderDto(order);
    }
 /*   @GetMapping("/{id}")
    public OrderDto getOrderById(@PathVariable Long id) {
        Order order = orderService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        return new OrderDto(order);
    }

    @GetMapping
    public List<OrderDto> getCurrentUserOrders(Principal principal) {
        return orderService.findAllOrdersByOwnerName(principal.getName()).stream().map(OrderDto::new).collect(Collectors.toList());
    }*/

}
