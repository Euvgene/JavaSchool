package com.evgenii.my_market.services;

import com.evgenii.my_market.dao.OrderDAO;
import com.evgenii.my_market.entity.Cart;
import com.evgenii.my_market.entity.Order;
import com.evgenii.my_market.entity.OrderState;
import com.evgenii.my_market.entity.User;
import com.evgenii.my_market.exception_handling.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.util.UUID;

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
            order.setOrderState(new OrderState(2,"awaiting shipment"));
        } else{
            order.setOrderState(new OrderState(1,"awaiting payment"));
        }
        order = orderDAO.saveOrder(order);
        return order;
    }

/*    public List<Order> findAllOrdersByOwnerName(String username) {
        return orderRepository.findAllByOwnerUsername(username);
    }

    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }*/
}
