package com.evgenii.my_market.services;

import com.evgenii.my_market.dao.OrderDAO;
import com.evgenii.my_market.dto.OrderDto;
import com.evgenii.my_market.entity.Cart;
import com.evgenii.my_market.entity.Order;
import com.evgenii.my_market.entity.StateEnum;
import com.evgenii.my_market.entity.User;
import com.evgenii.my_market.exception_handling.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderDAO orderDAO;
    private final CartService cartService;
    private final UserService userService;

    @Transactional
    public Order createFromUserCart(String userName,
                                    UUID cartUuid,
                                    String address,
                                    String paymentMethod,
                                    boolean paymentState) {
        User user = userService.findByUsername(userName).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Cart cart = cartService.findById(cartUuid).orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
        Order order = new Order(cart, user, user.getUserAddress(),address,paymentMethod,paymentState);
        if(paymentState){
            order.setOrderState(StateEnum.AWAITING_SHIPMENT);
        } else{
            order.setOrderState(StateEnum.AWAITING_PAYMENT);
        }
        order = orderDAO.saveOrder(order);
        cart.getCartItems().forEach(cartItem -> cartItem.getProduct().decrementQuantityProduct(cartItem.getQuantity()));
        cartService.clearCart(cartUuid);
        return order;
    }

    public List<OrderDto> findAllOrdersByOwnerName(String username, LocalDate fromDate, LocalDate toDate, int page) {
        int total = 5;
        if (page != 1) {
            page = (page - 1) * total + 1;
        }
        return orderDAO.findAllByOwnerUsername(username,fromDate,toDate, page-1, total).stream().map(OrderDto::new).collect(Collectors.toList());
    }

    public Optional<Order> findById(UUID id) {
        return orderDAO.findById(id);
    }
}
