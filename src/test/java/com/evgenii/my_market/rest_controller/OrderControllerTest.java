package com.evgenii.my_market.rest_controller;

import com.evgenii.my_market.config.SpringConfig;
import com.evgenii.my_market.dto.*;
import com.evgenii.my_market.entity.Order;
import com.evgenii.my_market.entity.OrderItem;
import com.evgenii.my_market.entity.Product;
import com.evgenii.my_market.service.OrderServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    private static final String PAYMENT_METHOD = "cash";
    private static final String INVALID_PAYMENT_METHOD = "credit";
    private static final String DELIVERY_ADDRESS = "some address";
    private static final String PRODUCT_TITLE = "Cat";
    private static final BigInteger EXPECTED_COUNT = BigInteger.valueOf(10);
    private static final BigDecimal TOTAL_PRICE = BigDecimal.valueOf(100);
    private static final int FIRST_PAGE_NUMBER = 1;
    private static final int PRODUCT_QUANTITY = 1;
    private static final int INDEX_OF_FIRST_ITEM = 0;

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

    @Test
    void createOrderFromCart() {
        OrderConfirmDto orderConfirmDto = new OrderConfirmDto();
        orderConfirmDto.setUsername(USER_NAME);
        orderConfirmDto.setCartId(String.valueOf(CART_UID));
        orderConfirmDto.setAddress(DELIVERY_ADDRESS);
        orderConfirmDto.setPaymentMethod(PAYMENT_METHOD);

        Order expectedOrder = new Order();
        expectedOrder.setPaymentMethod(PAYMENT_METHOD);
        expectedOrder.setCreatedAt(LocalDate.now());

        when(orderService.createFromUserCart(orderConfirmDto)).thenReturn(expectedOrder);

        OrderDto testOrder = tested.createOrderFromCart(() -> USER_NAME, orderConfirmDto);

        assertEquals(testOrder.getPaymentMethod(), expectedOrder.getPaymentMethod());
    }

    @SneakyThrows
    @Test
    void createOrderFromCartInValid() {
        OrderConfirmDto orderConfirmDto = new OrderConfirmDto();
        orderConfirmDto.setUsername(USER_NAME);
        orderConfirmDto.setCartId(String.valueOf(CART_UID));
        orderConfirmDto.setAddress(DELIVERY_ADDRESS);
        orderConfirmDto.setPaymentMethod(INVALID_PAYMENT_METHOD);

        ObjectMapper mapper = new ObjectMapper();

        String json = mapper.writeValueAsString(orderConfirmDto);
        mockMvc.perform(
                post("/api/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getOrderByIdSuccess() {
        Product product = new Product();
        product.setProductTitle(PRODUCT_TITLE);
        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.setOrderId(ORDER_UID);
        orderItemDto.setPrice(TOTAL_PRICE);
        orderItemDto.setProductTitle(product.getProductTitle());
        orderItemDto.setQuantity(PRODUCT_QUANTITY);

        List<OrderItemDto> orderItemList = new ArrayList<>();
        orderItemList.add(orderItemDto);

        OrderResultDto orderResultDto = new OrderResultDto();
        orderResultDto.setItems(orderItemList);
        orderResultDto.setTotalPrice(TOTAL_PRICE);

        Order expectedOrder = new Order();
        expectedOrder.setId(ORDER_UID);
        List<OrderItem> expectedOrderItems = new ArrayList<>();
        OrderItem orderItem = new OrderItem();
        orderItem.setQuantity(PRODUCT_QUANTITY);
        orderItem.setProduct(product);
        orderItem.setPrice(TOTAL_PRICE);
        orderItem.setOrder(expectedOrder);
        expectedOrderItems.add(orderItem);
        expectedOrder.setItems(expectedOrderItems);
        when(orderService.findById(ORDER_UID)).thenReturn(Optional.of(expectedOrder));

        OrderResultDto testOrderResult = tested.getOrderById(ORDER_UID);

        assertEquals(expectedOrder.getItems().size(), testOrderResult.getItems().size());
        assertEquals(expectedOrder.getPrice(), testOrderResult.getTotalPrice());
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
                FROM_DATE, TO_DATE, FIRST_PAGE_NUMBER)).thenReturn(expectedList);
        List<OrderDto> testList = tested.getCurrentUserOrders(FIRST_PAGE_NUMBER,
                FROM_DATE, TO_DATE, () -> USER_NAME);

        assertEquals(expectedList.get(INDEX_OF_FIRST_ITEM).getOrderId(), testList.get(INDEX_OF_FIRST_ITEM).getOrderId());
    }

    @Test
    void getCurrentUserOrdersCount() {
        BigInteger expectedCount = EXPECTED_COUNT;

        when(orderService.getOrdersCountByOwnerName(USER_NAME, FROM_DATE, TO_DATE)).thenReturn(expectedCount);

        BigInteger test = tested.getCurrentUserOrdersCount(FROM_DATE, TO_DATE, () -> USER_NAME);
        assertEquals(test, expectedCount);
    }

    @Test
    void getAllUserOrdersCount() {
        BigInteger expectedCount = EXPECTED_COUNT;

        when(orderService.getOrdersCount(FROM_DATE, TO_DATE, "")).thenReturn(expectedCount);

        BigInteger test = tested.getAllUserOrdersCount(FROM_DATE, TO_DATE, "");

        assertEquals(test, expectedCount);
    }

    @Test
    void getAllOrders() {
        List<OrderDto> expectedList = new ArrayList<>();
        OrderDto orderDto = new OrderDto();
        orderDto.setOrderId(ORDER_UID);
        orderDto.setTotalPrice(TOTAL_PRICE);
        expectedList.add(orderDto);

        when(orderService.findAllOrders(FROM_DATE, TO_DATE, FIRST_PAGE_NUMBER, "")).thenReturn(expectedList);

        List<OrderDto> testList = tested.getAllOrders(FIRST_PAGE_NUMBER, FROM_DATE, TO_DATE, "");

        assertEquals(expectedList.get(INDEX_OF_FIRST_ITEM).getOrderId(), testList.get(INDEX_OF_FIRST_ITEM).getOrderId());

    }

    @Test
    void getStatistic() {
        List<StatisticDto> expectedStatistic = new ArrayList<>();
        StatisticDto statisticDto = new StatisticDto();
        statisticDto.setNumber(PRODUCT_QUANTITY);
        statisticDto.setName(PRODUCT_TITLE);
        expectedStatistic.add(statisticDto);

        when(orderService.getStatistic("", FROM_DATE, TO_DATE)).thenReturn(expectedStatistic);

        List<StatisticDto> testList = tested.getStatistic("", FROM_DATE, TO_DATE);

        assertEquals(expectedStatistic.get(INDEX_OF_FIRST_ITEM).getNumber(), testList.get(INDEX_OF_FIRST_ITEM).getNumber());
    }

    @Test
    void getProductStatistic() {
        List<ProductStatisticDto> expectedStatistic = new ArrayList<>();
        ProductStatisticDto statisticDto = new ProductStatisticDto();
        statisticDto.setNumber(PRODUCT_QUANTITY);
        statisticDto.setName(PRODUCT_TITLE);
        expectedStatistic.add(statisticDto);

        when(orderService.getProductStatistic(FROM_DATE, TO_DATE)).thenReturn(expectedStatistic);

        List<ProductStatisticDto> testList = tested.getProductStatistic(FROM_DATE, TO_DATE);

        assertEquals(expectedStatistic.get(INDEX_OF_FIRST_ITEM).getNumber(), testList.get(INDEX_OF_FIRST_ITEM).getNumber());
    }
}