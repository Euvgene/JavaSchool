package com.evgenii.my_market.service;

import com.evgenii.my_market.dao.api.ProductDAO;
import com.evgenii.my_market.dto.FilterDto;
import com.evgenii.my_market.dto.ProductDto;
import com.evgenii.my_market.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    private static final int TOTAL_PRODUCTS_IN_PAGE = 8;
    private static final int FIRST_PAGE_NUMBER = 1;
    private static final int PRODUCT_ID = 1;
    private static final byte PRODUCT_QUANTITY = 3;

    @Mock
    private ProductDAO productDAO;

    private ProductServiceImpl tested;

    @BeforeEach
    void setup() {
        tested = new ProductServiceImpl(productDAO);
    }

    @Test
    void getProductsPageFirstPage() {
        List<Product> productList = new ArrayList<>();

        FilterDto filterDto = new FilterDto();
        filterDto.setPage(FIRST_PAGE_NUMBER);

        Product product = new Product();
        product.setProductId(PRODUCT_ID);

        productList.add(product);

        when(productDAO.getProductsPage(FIRST_PAGE_NUMBER - 1, TOTAL_PRODUCTS_IN_PAGE, filterDto)).thenReturn(productList);

        List<ProductDto> testList = tested.getProductsPage(filterDto);

        assertEquals(testList.size(), productList.size());
        assertEquals(testList.get(0).getProductId(), product.getProductId());

    }

    @Test
    void getProductsPageSecondPage() {
        List<Product> productList = new ArrayList<>();

        FilterDto filterDto = new FilterDto();
        filterDto.setPage(2);

        Product product = new Product();
        product.setProductId(PRODUCT_ID);

        productList.add(product);

        when(productDAO.getProductsPage(8, TOTAL_PRODUCTS_IN_PAGE, filterDto)).thenReturn(productList);

        List<ProductDto> testList = tested.getProductsPage(filterDto);

        assertEquals(testList.size(), productList.size());
        assertEquals(testList.get(0).getProductId(), product.getProductId());

    }

    @Test
    void save() {
        ProductDto productDto = new ProductDto();
        productDto.setProductId(PRODUCT_ID);

        Product product = new Product(productDto);

        doNothing().when(productDAO).saveNewProduct(product);

        tested.save(productDto);

        assertEquals(product.getProductId(), PRODUCT_ID);
    }


    @Test
    void getProductById() {
        List<Product> productList = new ArrayList<>();
        Product product = new Product();
        product.setProductId(PRODUCT_ID);
        productList.add(product);

        when(productDAO.findProductById(PRODUCT_ID)).thenReturn(productList);

        Product testProduct = tested.getProductById(PRODUCT_ID);

        assertEquals(testProduct.getProductId(), product.getProductId());

    }

    @Test
    void update() {

        ProductDto productDto = new ProductDto();
        productDto.setProductId(PRODUCT_ID);

        Product product = new Product(productDto);

        doNothing().when(productDAO).update(product);
        tested.update(productDto);

    }

    @Test
    void deleteProductById() {
        List<Product> productList = new ArrayList<>();
        Product product = new Product();
        product.setProductId(PRODUCT_ID);
        product.setProductQuantity(PRODUCT_QUANTITY);
        productList.add(product);
        when(productDAO.findProductById(PRODUCT_ID)).thenReturn(productList);
        tested.deleteProductById(PRODUCT_ID);

        assertEquals(product.getProductQuantity(), 0);

    }

    @Test
    void getProductsCount() {
        BigInteger bigInteger = BigInteger.valueOf(10);
        FilterDto filterDto = new FilterDto();

        when(productDAO.getProductCount(filterDto)).thenReturn(bigInteger);

        BigInteger testBigInteger = tested.getProductsCount(filterDto);

        assertEquals(testBigInteger, bigInteger);

    }
}