package com.evgenii.my_market.rest_controller;

import com.evgenii.my_market.config.SpringConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {SpringConfig.class})
class CartControllerTest {

    private static final String PRODUCT_ID = "65";
    private static final String PRODUCT_ID_NOT_EXIST = "2";
    private static final String PRODUCT_NAME = "Mimi cat";
    private static final String CART_UUID = "dbb3cab4-e0e0-402d-b65f-0a89ef3a4092";
    private static final String CART_UUID_NOT_EXIST = "a0fe8317-0ad9-46cb-acfd-fd1533bd1b6c";

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    void addProductToCartSuccess() throws Exception {

        mockMvc.perform(post("/api/v1/cart/add")
                .param("uuid", CART_UUID)
                .param("prod_id", PRODUCT_ID))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/v1/cart/" + CART_UUID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.items").isArray())
                .andExpect(jsonPath("$.items", hasSize(1)))
                .andExpect(jsonPath("$.items[0].productTitle", is(PRODUCT_NAME)));
    }

    @Test
    void addProductToCartProductNotFound() throws Exception {

        mockMvc.perform(post("/api/v1/cart/add")
                .param("uuid", CART_UUID)
                .param("prod_id", PRODUCT_ID_NOT_EXIST))
                .andExpect(status().isNotFound());
    }

    @Test
    void getCurrentCartDenied() throws Exception {
        mockMvc.perform(get("/api/v1/cart/{uuid}", CART_UUID_NOT_EXIST)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


    @Test
    void clearCartSuccess() throws Exception {
        mockMvc.perform(post("/api/v1/cart/clear")
                .param("uuid", CART_UUID))
                .andExpect(status().isOk());
    }

    @Test
    void clearCartDenied() throws Exception {

        mockMvc.perform(post("/api/v1/cart/clear")
                .param("uuid", CART_UUID_NOT_EXIST))
                .andExpect(status().isNotFound());
    }

    @Test
    void clearOldItemsFromCart() throws Exception {

        mockMvc.perform(get("/api/v1/cart/clear")
                .param("uuid", CART_UUID))
                .andExpect(status().isOk());
    }

    @Test
    void updateQuantityOrDeleteProductInCartSuccess() throws Exception {

        mockMvc.perform(post("/api/v1/cart/clear")
                .param("uuid", CART_UUID)
                .param("product_id", "1")
                .param("updateNumber", "1"))
                .andExpect(status().isOk());
    }
}