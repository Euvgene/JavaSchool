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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
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
    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    void addProductToCartSuccess() throws Exception {

        String uuid = getCartForTest();

        mockMvc.perform(post("/api/v1/cart/add")
                .param("uuid", uuid)
                .param("prod_id", "65"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/v1/cart/" + uuid)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.items").isArray())
                .andExpect(jsonPath("$.items", hasSize(1)))
                .andExpect(jsonPath("$.items[0].productTitle", is("Mimi cat")))
                .andExpect(jsonPath("$.items[0].price", is(459.00)));
    }

    @Test
    void addProductToCartProductNotFound() throws Exception {
        String uuid = getCartForTest();
        mockMvc.perform(post("/api/v1/cart/add")
                .param("uuid", uuid)
                .param("prod_id", "2"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getCurrentCartDenied() throws Exception {
        mockMvc.perform(get("/api/v1/cart/{uuid}", "a0fe8317-0ad9-46cb-acfd-fd1533bd1b6c")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


    @Test
    void clearCartSuccess() throws Exception {
        String uuid = getCartForTest();
        mockMvc.perform(post("/api/v1/cart/clear")
                .param("uuid", uuid))
                .andExpect(status().isOk());
    }

    @Test
    void clearCartDenied() throws Exception {
        String uuid = "a0fe8317-0ad9-46cb-acfd-fd1533bd1b6c";
        mockMvc.perform(post("/api/v1/cart/clear")
                .param("uuid", uuid))
                .andExpect(status().isNotFound());
    }

    @Test
    void clearOldItemsFromCart() throws Exception {
        String uuid = getCartForTest();
        mockMvc.perform(get("/api/v1/cart/clear")
                .param("uuid", uuid))
                .andExpect(status().isOk());
    }

    @Test
    void updateQuantityOrDeleteProductInCartSuccess() throws Exception {
        String uuid = getCartForTest();
        mockMvc.perform(post("/api/v1/cart/clear")
                .param("uuid", uuid)
                .param("product_id", "1")
                .param("updateNumber", "1"))
                .andExpect(status().isOk());
    }

    private String getCartForTest() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/cart")
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();

        String uuid = result.getResponse().getContentAsString().replaceAll("\"", "");
        return uuid;
    }
}