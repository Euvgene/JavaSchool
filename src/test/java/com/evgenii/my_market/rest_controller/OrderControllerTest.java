package com.evgenii.my_market.rest_controller;

import com.evgenii.my_market.config.SpringConfig;
import com.evgenii.my_market.dto.*;
import com.evgenii.my_market.entity.Order;
import com.evgenii.my_market.entity.OrderItem;
import com.evgenii.my_market.entity.Product;
import com.evgenii.my_market.service.OrderServiceImpl;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {SpringConfig.class})
class OrderControllerTest {

    private static final UUID CART_UID = UUID.fromString("64fc8473-97b5-46cb-9197-a631e3fb7e57");
    private static final UUID ORDER_UID = UUID.fromString("64fc8473-97b5-46cb-9197-a631e3fb7e57");
    private static final LocalDate FROM_DATE = LocalDate.now().withDayOfMonth(1);
    private static final LocalDate TO_DATE = LocalDate.now();
    private static final String USER_NAME = "Bob";

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @Mock
    OrderServiceImpl orderService;

    private OrderController tested;

    @BeforeEach
    public void setup() {
        tested = new OrderController(orderService);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @SneakyThrows
    @Test
    void createOrderFromCart() {
        OrderConfirmDto orderConfirmDto = new OrderConfirmDto();
        orderConfirmDto.setUsername(USER_NAME);
        orderConfirmDto.setCartId(String.valueOf(CART_UID));
        orderConfirmDto.setAddress("some");
        orderConfirmDto.setPaymentMethod("cash");

        Order expectedOrder = new Order();
        expectedOrder.setPaymentMethod("cash");
        expectedOrder.setCreatedAt(LocalDate.now());

        when(orderService.createFromUserCart(orderConfirmDto)).thenReturn(expectedOrder);

        OrderDto testOrder = tested.createOrderFromCart(() -> USER_NAME, orderConfirmDto);

        assertEquals(testOrder.getPaymentMethod(), expectedOrder.getPaymentMethod());
    }

    @Test
    void getOrderByIdSuccess() {
        Product product = new Product();
        product.setProductTitle("cat");
        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.setOrderId(ORDER_UID);
        orderItemDto.setPrice(BigDecimal.valueOf(100));
        orderItemDto.setProductTitle(product.getProductTitle());
        orderItemDto.setQuantity(1);

        List<OrderItemDto> orderItemList = new ArrayList<>();
        orderItemList.add(orderItemDto);

        OrderResultDto orderResultDto = new OrderResultDto();
        orderResultDto.setItems(orderItemList);
        orderResultDto.setTotalPrice(BigDecimal.valueOf(100));

        Order expectedOrder = new Order();
        expectedOrder.setId(ORDER_UID);
        List<OrderItem> expectedOrderItems = new ArrayList<>();
        OrderItem orderItem = new OrderItem();
        orderItem.setQuantity(1);
        orderItem.setProduct(product);
        orderItem.setPrice(BigDecimal.valueOf(100));
        orderItem.setOrder(expectedOrder);
        expectedOrderItems.add(orderItem);
        expectedOrder.setItems(expectedOrderItems);
        when(orderService.findById(ORDER_UID)).thenReturn(Optional.of(expectedOrder));

        OrderResultDto testOrderResult = tested.getOrderById(ORDER_UID);

        assertEquals(testOrderResult.getItems().size(), expectedOrder.getItems().size());
        assertEquals(testOrderResult.getTotalPrice(), expectedOrder.getPrice());
    }

    @SneakyThrows
    @Test
    void getOrderByIdThrowException() {
        mockMvc.perform(get("/api/v1/orders/" + ORDER_UID.toString()))
                .andExpect(status().isNotFound());
    }

    @Test
    void getCurrentUserOrders() {
        List<OrderDto> expectedList = new ArrayList<>();
        OrderDto orderDto = new OrderDto();
        orderDto.setOrderId(ORDER_UID);
        expectedList.add(orderDto);

        when(orderService.findAllOrdersByOwnerName(USER_NAME,
                FROM_DATE, TO_DATE, 1)).thenReturn(expectedList);
        List<OrderDto> testList = tested.getCurrentUserOrders(1,
                LocalDate.now().withDayOfMonth(1),
                LocalDate.now(), () -> USER_NAME);

        assertEquals(testList.get(0).getOrderId(), expectedList.get(0).getOrderId());
    }

    @Test
    void getCurrentUserOrdersCount() {
        BigInteger expectedCount = BigInteger.valueOf(10);

        when(orderService.getOrdersCountByOwnerName(USER_NAME, FROM_DATE, TO_DATE)).thenReturn(expectedCount);

        BigInteger test = tested.getCurrentUserOrdersCount(FROM_DATE, TO_DATE, () -> USER_NAME);
        assertEquals(test, expectedCount);
    }

    @Test
    void getAllUserOrdersCount() {
        BigInteger expectedCount = BigInteger.valueOf(10);

        when(orderService.getOrdersCount(FROM_DATE, TO_DATE, "")).thenReturn(expectedCount);

        BigInteger test = tested.getAllUserOrdersCount(FROM_DATE, TO_DATE, "");

        assertEquals(test, expectedCount);
    }

    @Test
    void getAllOrders() {
        List<OrderDto> expectedList = new ArrayList<>();
        OrderDto orderDto = new OrderDto();
        orderDto.setOrderId(ORDER_UID);
        orderDto.setTotalPrice(BigDecimal.valueOf(100));
        expectedList.add(orderDto);

        when(orderService.findAllOrders(FROM_DATE, TO_DATE, 1, "")).thenReturn(expectedList);

        List<OrderDto> testList = tested.getAllOrders(1, FROM_DATE, TO_DATE, "");

        assertEquals(expectedList.get(0).getOrderId(), testList.get(0).getOrderId());

    }

    @Test
    void getStatistic() {
        List<StatisticDto> expectedStatistic = new ArrayList<>();
        StatisticDto statisticDto = new StatisticDto();
        statisticDto.setNumber(1);
        statisticDto.setName("Cat");
        expectedStatistic.add(statisticDto);

        when(orderService.getStatistic("", FROM_DATE, TO_DATE)).thenReturn(expectedStatistic);

        List<StatisticDto> testList = tested.getStatistic("", FROM_DATE, TO_DATE);

        assertEquals(testList.get(0).getNumber(), expectedStatistic.get(0).getNumber());
    }

    @Test
    void getProductStatistic() {
        List<ProductStatisticDto> expectedStatistic = new ArrayList<>();
        ProductStatisticDto statisticDto = new ProductStatisticDto();
        statisticDto.setNumber(1);
        statisticDto.setName("Cat");
        expectedStatistic.add(statisticDto);

        when(orderService.getProductStatistic(FROM_DATE, TO_DATE)).thenReturn(expectedStatistic);

        List<ProductStatisticDto> testList = tested.getProductStatistic(FROM_DATE, TO_DATE);

        assertEquals(testList.get(0).getNumber(), expectedStatistic.get(0).getNumber());
    }
}