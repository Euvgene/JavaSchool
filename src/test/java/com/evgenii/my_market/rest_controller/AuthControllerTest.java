package com.evgenii.my_market.rest_controller;

import com.evgenii.my_market.config.SpringConfig;
import com.evgenii.my_market.dto.JwtRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {SpringConfig.class})
class AuthControllerTest {

    private final String USER_NAME = "Evgenii";
    private final String USER_PASSWORD = "12345678";
    private final String WRONG_USER_PASSWORD = "200";

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    void createAuthTokenSuccess() throws Exception {
        JwtRequest testJwtRequest = new JwtRequest();
        testJwtRequest.setCartId(UUID.fromString(getCartForTest()));
        testJwtRequest.setUsername(USER_NAME);
        testJwtRequest.setPassword(USER_PASSWORD);

        ObjectMapper mapper = new ObjectMapper();

        String json = mapper.writeValueAsString(testJwtRequest);
        mockMvc.perform(post("/api/v1/auth")
                .contentType(
                        MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("userRole", is("[ROLE_USER]")))
                .andExpect(jsonPath("token").exists())
                .andExpect(jsonPath("token").isString());
    }

    @Test
    void createAuthTokenIsUnauthorized() throws Exception {
        JwtRequest testJwtRequest = new JwtRequest();
        testJwtRequest.setCartId(UUID.fromString(getCartForTest()));
        testJwtRequest.setUsername(USER_NAME);
        testJwtRequest.setPassword(WRONG_USER_PASSWORD);

        ObjectMapper mapper = new ObjectMapper();

        String json = mapper.writeValueAsString(testJwtRequest);
        mockMvc.perform(post("/api/v1/auth")
                .contentType(
                        MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isUnauthorized());
    }


    private String getCartForTest() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/cart")
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();

        String uuid = result.getResponse().getContentAsString().replaceAll("\"", "");
        return uuid;
    }
}