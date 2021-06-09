package com.evgenii.my_market.service;

import com.evgenii.my_market.config.MessageSender;
import com.evgenii.my_market.dao.api.OrderDAO;
import com.evgenii.my_market.dto.OrderConfirmDto;
import com.evgenii.my_market.dto.OrderDto;
import com.evgenii.my_market.dto.ProductStatisticDto;
import com.evgenii.my_market.dto.StatisticDto;
import com.evgenii.my_market.entity.*;
import com.evgenii.my_market.service.api.CartService;
import com.evgenii.my_market.service.api.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    private static final String USER_NAME = "Bob";
    private static final boolean PAYMENT_STATE = true;
    private static final UUID ORDER_UID = UUID.fromString("64fc8473-97b5-46cb-9197-a631e3fb7e57");
    private static final String ADDRESS = "Nevskii";
    private static final String STATISTIC_NAME = "orders";
    private static final String PRODUCT_NAME = "cat";
    private static final String DELIVERY_ADDRESS = "from store";
    private static final String PAYMENT_METHOD_CASH = "cash";
    private static final String PAYMENT_METHOD_CREDIT_CARD = "credit card";
    private static final int PRODUCT_ID = 1;
    private static final BigDecimal PRODUCT_PRICE = BigDecimal.valueOf(100);
    private static final int PRODUCT_QUANTITY_ONE = 1;
    private static final int PRODUCT_QUANTITY_TWO = 2;
    private static final int PRODUCT_QUANTITY_THREE = 3;
    private static final int STATISTIC_PRODUCT_PRICE = 122;
    private static final LocalDate FROM_DATE = LocalDate.parse("2021-01-01");
    private static final LocalDate TO_DATE = LocalDate.parse("2021-01-05");
    private static final String STATE = "ACTIVE";
    private static final int FIRST_PAGE_INDEX = 0;
    private static final int SECOND_PAGE_INDEX = 8;
    private static final int FIRST_PAGE = 1;
    private static final int SECOND_PAGE = 2;
    private static final int INDEX_OF_FIRST_ITEM = 0;
    private static final BigInteger EXPECTED_COUNT = BigInteger.valueOf(10);

    @Mock
    private OrderDAO orderDAO;
    @Mock
    private CartService cartService;
    @Mock
    private MessageSender messageSender;
    @Mock
    private UserService userService;

    private OrderServiceImpl tested;

    @BeforeEach
    void setup() {
        tested = new OrderServiceImpl(orderDAO, cartService, messageSender, userService);
    }

    @Test
    void createFromUserCartPaymentStateCash() {
        createTestOrder(PAYMENT_METHOD_CASH);
    }

    @Test
    void createFromUserCartPaymentStateCreditCard() {
        createTestOrder(PAYMENT_METHOD_CREDIT_CARD);
    }

    @Test
    void findAllOrdersByOwnerNameFirstPage() {

        List<Order> orderList = createOrderList();

        when(orderDAO.findAllByOwnerUsername(USER_NAME, FROM_DATE, TO_DATE, FIRST_PAGE_INDEX, 8)).thenReturn(orderList);
        List<OrderDto> orderDtoList = tested.findAllOrdersByOwnerName(USER_NAME, FROM_DATE, TO_DATE, FIRST_PAGE);

        assertEquals(orderList.size(), orderDtoList.size());

    }

    @Test
    void findAllOrdersByOwnerNameSecondPage() {
        List<Order> orderList = createOrderList();

        when(orderDAO.findAllByOwnerUsername(USER_NAME, FROM_DATE, TO_DATE, SECOND_PAGE_INDEX, 8)).thenReturn(orderList);
        List<OrderDto> orderDtoList = tested.findAllOrdersByOwnerName(USER_NAME, FROM_DATE, TO_DATE, SECOND_PAGE);

        assertEquals(orderList.size(), orderDtoList.size());
    }


    @Test
    void findById() {
        Order order = new Order();
        order.setId(ORDER_UID);

        when(orderDAO.findById(ORDER_UID)).thenReturn(Optional.of(order));

        Optional<Order> testOrder = tested.findById(ORDER_UID);

        assertEquals(order.getId(), testOrder.get().getId());
    }

    @Test
    void findAllOrders() {
        List<Order> orderList = createOrderList();

        when(orderDAO.findAlL(FROM_DATE, TO_DATE, FIRST_PAGE_INDEX, STATE, 8)).thenReturn(orderList);

        List<OrderDto> orderDtoList = tested.findAllOrders(FROM_DATE, TO_DATE, FIRST_PAGE, STATE);

        assertEquals(orderList.size(), orderDtoList.size());
    }

    @Test
    void updateOrderStateShipped() {
        Order order = createOrder();
        List<OrderItem> orderItems = new ArrayList<>();
        OrderItem orderItem = new OrderItem();
        Product product = createProduct();
        orderItem.setProduct(product);
        orderItem.setQuantity(PRODUCT_QUANTITY_ONE);
        orderItems.add(orderItem);
        order.setItems(orderItems);

        when(orderDAO.findById(ORDER_UID)).thenReturn(Optional.of(order));

        tested.updateOrder(ORDER_UID, DELIVERY_ADDRESS, StateEnum.SHIPPED.toString());

        assertEquals(StateEnum.SHIPPED, order.getOrderState());
        assertEquals(DELIVERY_ADDRESS, order.getDeliveryMethode());
    }


    @Test
    void updateOrderStateDelivered() {
        Order order = createOrder();
        List<OrderItem> orderItems = new ArrayList<>();
        OrderItem orderItem = new OrderItem();
        Product product = createProduct();
        orderItem.setProduct(product);
        orderItem.setQuantity(PRODUCT_QUANTITY_ONE);
        orderItems.add(orderItem);
        order.setItems(orderItems);

        when(orderDAO.findById(ORDER_UID)).thenReturn(Optional.of(order));

        tested.updateOrder(ORDER_UID, DELIVERY_ADDRESS, StateEnum.DELIVERED.toString());

        assertEquals(StateEnum.DELIVERED, order.getOrderState());
        assertTrue(order.isPaymentState());
    }

    @Test
    void updateOrderStateReturn() {
        Order order = createOrder();
        List<OrderItem> orderItems = new ArrayList<>();
        OrderItem orderItem = new OrderItem();
        Product product = createProduct();
        orderItem.setProduct(product);
        orderItem.setQuantity(PRODUCT_QUANTITY_ONE);
        orderItems.add(orderItem);
        order.setItems(orderItems);

        when(orderDAO.findById(ORDER_UID)).thenReturn(Optional.of(order));

        tested.updateOrder(ORDER_UID, DELIVERY_ADDRESS, StateEnum.RETURN.toString());

        assertEquals(StateEnum.RETURN, order.getOrderState());
        assertEquals(PRODUCT_QUANTITY_THREE, product.getProductQuantity());
    }

    @Test
    void getStatistic() {
        List<Object[]> statisticDtoList = new ArrayList<>();
        Object[] objects = new Object[]{PRODUCT_NAME, PRODUCT_QUANTITY_ONE};
        statisticDtoList.add(objects);

        when(orderDAO.getStatistic(STATISTIC_NAME, FROM_DATE, TO_DATE)).thenReturn(statisticDtoList);

        List<StatisticDto> statisticDtos = tested.getStatistic(STATISTIC_NAME, FROM_DATE, TO_DATE);

        assertEquals(PRODUCT_NAME, statisticDtos.get(INDEX_OF_FIRST_ITEM).getName());
        assertEquals(PRODUCT_QUANTITY_ONE, statisticDtos.get(INDEX_OF_FIRST_ITEM).getNumber());
    }

    @Test
    void getProductStatistic() {
        List<Object[]> statisticDtoList = new ArrayList<>();
        Object[] objects = new Object[]{PRODUCT_NAME, PRODUCT_QUANTITY_ONE, STATISTIC_PRODUCT_PRICE};
        statisticDtoList.add(objects);

        when(orderDAO.getProductStatistic(FROM_DATE, TO_DATE)).thenReturn(statisticDtoList);

        List<ProductStatisticDto> statisticDtos = tested.getProductStatistic(FROM_DATE, TO_DATE);

        assertEquals(PRODUCT_NAME, statisticDtos.get(INDEX_OF_FIRST_ITEM).getName());
        assertEquals(PRODUCT_QUANTITY_ONE, statisticDtos.get(INDEX_OF_FIRST_ITEM).getNumber());
    }

    @Test
    void getOrdersCountByOwnerName() {
        BigInteger bigInteger = EXPECTED_COUNT;

        when(orderDAO.getOrdersCountByOwnerName(USER_NAME, FROM_DATE, TO_DATE)).thenReturn(bigInteger);

        BigInteger testBigInteger = tested.getOrdersCountByOwnerName(USER_NAME, FROM_DATE, TO_DATE);

        assertEquals(bigInteger, testBigInteger);

    }

    @Test
    void getOrdersCount() {

        BigInteger bigInteger = EXPECTED_COUNT;

        when(orderDAO.getAllOrderCount(FROM_DATE, TO_DATE, STATE)).thenReturn(bigInteger);

        BigInteger testBigInteger = tested.getOrdersCount(FROM_DATE, TO_DATE, STATE);

        assertEquals(bigInteger, testBigInteger);
    }

    private CartItem createCartItem(Product product) {
        CartItem cartItem = new CartItem();
        cartItem.setId(product.getProductId());
        cartItem.setProduct(product);
        cartItem.setQuantity((byte) PRODUCT_QUANTITY_ONE);
        cartItem.setPrice(PRODUCT_PRICE);
        cartItem.setPricePerProduct(PRODUCT_PRICE);
        return cartItem;
    }

    private Product createProduct() {
        Product product = new Product();
        product.setProductId(PRODUCT_ID);
        product.setProductQuantity((byte) PRODUCT_QUANTITY_TWO);
        product.setProductPrice(PRODUCT_PRICE);
        return product;
    }

    private Cart createTestCart(CartItem cartItem) {
        Cart expectedCart = new Cart();
        expectedCart.setCartId(ORDER_UID);
        expectedCart.setCartItems(new ArrayList<>());
        expectedCart.add(cartItem);
        return expectedCart;
    }

    private void createTestOrder(String paymentMethod) {
        OrderConfirmDto orderConfirmDto = new OrderConfirmDto(
                ORDER_UID.toString(), ADDRESS, paymentMethod
        );
        orderConfirmDto.setUsername(USER_NAME);

        User testUser = new User();
        testUser.setFirstName(USER_NAME);
        testUser.setUserAddress(new Address());

        Product product = createProduct();

        CartItem cartItem = createCartItem(product);

        Cart testCart = createTestCart(cartItem);

        Order order = new Order(testCart, testUser, testUser.getUserAddress(), orderConfirmDto.getAddress(),
                orderConfirmDto.getPaymentMethod(), PAYMENT_STATE, StateEnum.AWAITING_SHIPMENT);

        when(userService.findByUsername(USER_NAME)).thenReturn(Optional.of(testUser));
        when(cartService.findById(ORDER_UID)).thenReturn(testCart);
        when(orderDAO.saveOrder(any())).thenReturn(order);
        doNothing().when(messageSender).send(any());
        Order testOrder = tested.createFromUserCart(orderConfirmDto);

        assertEquals(paymentMethod, testOrder.getPaymentMethod());
        assertEquals(ADDRESS, testOrder.getDeliveryMethode());
        assertEquals(PRODUCT_QUANTITY_ONE, product.getProductQuantity());
    }

    private List<Order> createOrderList() {
        List<Order> orderList = new ArrayList<>();
        Order order = new Order();
        order.setCreatedAt(FROM_DATE);
        for (int i = 0; i < 10; i++) {
            orderList.add(order);
        }
        return orderList;
    }

    private Order createOrder() {
        Order order = new Order();
        order.setId(ORDER_UID);
        order.setOrderState(StateEnum.AWAITING_SHIPMENT);
        order.setDeliveryMethode(ADDRESS);
        return order;
    }
}