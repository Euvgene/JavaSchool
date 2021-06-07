package com.evgenii.my_market.rest_controller;

import com.evgenii.my_market.dto.OrderConfirmDto;
import com.evgenii.my_market.dto.OrderDto;
import com.evgenii.my_market.entity.Order;
import com.evgenii.my_market.service.OrderServiceImpl;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.Principal;
import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    private static final UUID FIRST_CART_UID = UUID.fromString("64fc8473-97b5-46cb-9197-a631e3fb7e57");

    @Mock
    OrderServiceImpl orderService;

    private OrderController tested;

    @BeforeEach
    public void setup() {
        tested = new OrderController(orderService);
    }

    @SneakyThrows
    @Test
    void createOrderFromCart() {
        OrderConfirmDto orderConfirmDto = new OrderConfirmDto();
        orderConfirmDto.setUsername("Bob");
        orderConfirmDto.setCartId(String.valueOf(FIRST_CART_UID));
        orderConfirmDto.setAddress("some");
        orderConfirmDto.setPaymentMethod("cash");

        Order expectedOrder = new Order();
        expectedOrder.setPaymentMethod("cash");
        expectedOrder.setCreatedAt(LocalDate.now());

        when(orderService.createFromUserCart(orderConfirmDto)).thenReturn(expectedOrder);

        OrderDto testOrder = tested.createOrderFromCart(new Principal() {
            @Override
            public String getName() {
                return "Bob";
            }
        }, orderConfirmDto);

        assertEquals(testOrder.getPaymentMethod(), expectedOrder.getPaymentMethod());
    }

    @Test
    void getOrderById() {
    }

    @Test
    void getCurrentUserOrders() {
    }

    @Test
    void getCurrentUserOrdersCount() {
    }

    @Test
    void getAllUserOrdersCount() {
    }

    @Test
    void getAllOrders() {
    }

    @Test
    void updateOrder() {
    }

    @Test
    void getStatistic() {
    }

    @Test
    void getProductStatistic() {
    }
}