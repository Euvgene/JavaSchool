package com.evgenii.my_market.rest_controllers;

import com.evgenii.my_market.dto.OrderDto;
import com.evgenii.my_market.entity.Order;
import com.evgenii.my_market.services.CartService;
import com.evgenii.my_market.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;



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
                                        @RequestParam(name = "address",defaultValue = "from store") String address,
                                        @RequestParam(name = "paymentMethod",defaultValue = "cash" ) String paymentMethod,
                                        @RequestParam(name = "paymentState",defaultValue = "false") boolean paymentState) {

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
