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
    private static final int SECOND_PAGE_NUMBER = 2;
    private static final int FIRST_PAGE_INDEX = 0;
    private static final int SECOND_PAGE_INDEX = 8;
    private static final int PRODUCT_ID = 1;
    private static final byte PRODUCT_QUANTITY = 3;
    private static final int INDEX_OF_FIRST_ITEM = 0;
    private static final int PRODUCT_QUANTITY_AFTER_DELETE = 0;
    private static final BigInteger EXPECTED_COUNT = BigInteger.valueOf(10);

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

        when(productDAO.getProductsPage(FIRST_PAGE_INDEX, TOTAL_PRODUCTS_IN_PAGE, filterDto)).thenReturn(productList);

        List<ProductDto> testList = tested.getProductsPage(filterDto);

        assertEquals(productList.size(), testList.size());
        assertEquals(product.getProductId(), testList.get(INDEX_OF_FIRST_ITEM).getProductId());

    }

    @Test
    void getProductsPageSecondPage() {
        List<Product> productList = new ArrayList<>();

        FilterDto filterDto = new FilterDto();
        filterDto.setPage(SECOND_PAGE_NUMBER);

        Product product = new Product();
        product.setProductId(PRODUCT_ID);

        productList.add(product);

        when(productDAO.getProductsPage(SECOND_PAGE_INDEX, TOTAL_PRODUCTS_IN_PAGE, filterDto)).thenReturn(productList);

        List<ProductDto> testList = tested.getProductsPage(filterDto);

        assertEquals(productList.size(), testList.size());
        assertEquals(product.getProductId(), testList.get(INDEX_OF_FIRST_ITEM).getProductId());

    }

    @Test
    void save() {
        ProductDto productDto = new ProductDto();
        productDto.setProductId(PRODUCT_ID);

        Product product = new Product(productDto);

        doNothing().when(productDAO).saveNewProduct(product);

        tested.save(productDto);

        assertEquals(PRODUCT_ID, product.getProductId());
    }


    @Test
    void getProductById() {
        List<Product> productList = new ArrayList<>();
        Product product = new Product();
        product.setProductId(PRODUCT_ID);
        productList.add(product);

        when(productDAO.findProductById(PRODUCT_ID)).thenReturn(productList);

        Product testProduct = tested.getProductById(PRODUCT_ID);

        assertEquals(product.getProductId(), testProduct.getProductId());

    }

    @Test
    void update() {

        ProductDto productDto = new ProductDto();
        productDto.setProductId(PRODUCT_ID);

        Product product = new Product(productDto);

        doNothing().when(productDAO).update(product);
        tested.update(productDto);

        assertEquals(PRODUCT_ID, product.getProductId());

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

        assertEquals(PRODUCT_QUANTITY_AFTER_DELETE, product.getProductQuantity());

    }

    @Test
    void getProductsCount() {
        BigInteger bigInteger = EXPECTED_COUNT;
        FilterDto filterDto = new FilterDto();

        when(productDAO.getProductCount(filterDto)).thenReturn(bigInteger);

        BigInteger testBigInteger = tested.getProductsCount(filterDto);

        assertEquals(bigInteger, testBigInteger);

    }
}