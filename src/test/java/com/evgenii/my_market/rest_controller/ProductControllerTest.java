package com.evgenii.my_market.rest_controller;

import com.evgenii.my_market.config.SpringConfig;
import com.evgenii.my_market.dto.FilterDto;
import com.evgenii.my_market.dto.ProductDto;
import com.evgenii.my_market.service.api.ProductService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {SpringConfig.class})
class ProductControllerTest {
    private final static int PRODUCT_ID = 65;
    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @Mock
    ProductService productService;

    private ProductController tested;

    @BeforeEach
    public void setup() {
        tested = new ProductController(productService);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @SneakyThrows
    @Test
    void getProductById() {
        mockMvc.perform(get("/api/v1/products/" + PRODUCT_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("productTitle", is("Mimi cat")));
    }

    @SneakyThrows
    @Test
    void getProductsPage() {
        FilterDto filterDto = new FilterDto();
        filterDto.setName("Mimi cat");
        filterDto.setPage(1);
        filterDto.setQuantity((byte) 0);
        filterDto.setCategory("");
        filterDto.setGender("");
        filterDto.setMaxPrice(BigDecimal.valueOf(Integer.MAX_VALUE));
        filterDto.setMinPrice(BigDecimal.ZERO);

        ObjectMapper mapper = new ObjectMapper();

        String json = mapper.writeValueAsString(filterDto);


        MvcResult result = mockMvc.perform(post("/api/v1/products/get")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk())
                .andReturn();

        List<ProductDto> actual = mapper.readValue(result.getResponse().getContentAsString(),
                new TypeReference<List<ProductDto>>() {
                });

        assertEquals(actual.get(0).getProductTitle(), filterDto.getName());
        assertEquals(actual.size(), 1);
    }

    @Test
    void getProductsCount() {
        BigInteger expectedCount = BigInteger.valueOf(10);
        FilterDto filterDto = new FilterDto();

        when(productService.getProductsCount(filterDto)).thenReturn(expectedCount);

        BigInteger test = tested.getProductsCount(filterDto);

        assertEquals(test, expectedCount);
    }

    @Test
    void saveProduct() {
        ProductDto productDto = new ProductDto();
        productDto.setProductId(PRODUCT_ID);

        doNothing().when(productService).save(productDto);

        ResponseEntity<?> testResponse = tested.saveProduct(productDto);

        assertEquals(testResponse.getStatusCode(), HttpStatus.OK);
    }

    @SneakyThrows
    @Test
    void saveProductInValid() {
        ProductDto productDto = new ProductDto();
        ObjectMapper mapper = new ObjectMapper();

        String json = mapper.writeValueAsString(productDto);

        mockMvc.perform(post("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateProduct() {
        ProductDto productDto = new ProductDto();
        productDto.setProductId(PRODUCT_ID);

        doNothing().when(productService).update(productDto);

        ResponseEntity<?> testResponse = tested.updateProduct(productDto);

        assertEquals(testResponse.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void deleteProductById() throws Exception {
        doNothing().when(productService).deleteProductById(PRODUCT_ID);
        tested.deleteProductById(PRODUCT_ID);
    }
}