package com.evgenii.my_market.service;

import com.evgenii.my_market.dao.api.ProductDAO;
import com.evgenii.my_market.dto.FilterDto;
import com.evgenii.my_market.dto.ProductDto;
import com.evgenii.my_market.entity.Product;
import com.evgenii.my_market.service.api.OrderService;
import com.evgenii.my_market.service.api.ProductService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of {@link ProductService} interface.
 *
 * @author Boznyakov Evgenii
 */
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    /**
     * Creates an instance of this class using constructor-based dependency injection.
     */
    private final ProductDAO dao;

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);
    private static final int TOTAL_PRODUCTS_IN_PAGE = 8;
    private static final int FIRST_PAGE_NUMBER = 1;

    /**
     * Getting product list using filter
     *
     * @param filterDto {@linkplain com.evgenii.my_market.dto.FilterDto} DTO for filter request
     * @return list of  {@linkplain com.evgenii.my_market.dto.ProductDto ProductDto}
     */
    @Transactional
    public List<ProductDto> getProductsPage(FilterDto filterDto) {
        int total = TOTAL_PRODUCTS_IN_PAGE;
        int page = filterDto.getPage();
        if (filterDto.getPage() != FIRST_PAGE_NUMBER) {
            page = (page - FIRST_PAGE_NUMBER) * total + FIRST_PAGE_NUMBER;
        }
        return dao.getProductsPage(page - FIRST_PAGE_NUMBER, total, filterDto).stream()
                .map(ProductDto::new)
                .collect(Collectors.toList());
    }

    /**
     * Save new product
     *
     * @param newProduct {@linkplain com.evgenii.my_market.dto.ProductDto} DTO for product
     */
    @Transactional
    public void save(ProductDto newProduct) {
        Product product = new Product(newProduct);
        dao.saveNewProduct(product);
        LOGGER.info("Create product with name {}", product.getProductTitle());
    }

    /**
     * Getting product by id
     *
     * @param id product id
     * @return {@linkplain com.evgenii.my_market.entity.Product}
     */
    @Transactional
    public Product getProductById(int id) {
        return dao.findProductById(id).get(0);
    }

    /**
     * Update product
     *
     * @param product {@linkplain com.evgenii.my_market.dto.ProductDto}
     */
    @Transactional
    public void update(ProductDto product) {
        Product updateProduct = new Product(product);
        dao.update(updateProduct);
        LOGGER.info("Update product with name {}", updateProduct.getProductTitle());
    }

    /**
     * Set product quantity to 0
     *
     * @param id product id
     */
    @Transactional
    public void deleteProductById(int id) {
        Product product = dao.findProductById(id).get(0);
        product.setProductQuantity((byte) 0);
        LOGGER.info("Set product quantity 0 to {}", product.getProductTitle());
    }

    /**
     * Getting product count using filter
     *
     * @param filterDto {@linkplain com.evgenii.my_market.dto.FilterDto} DTO for filter request
     * @return BigInteger
     */
    public BigInteger getProductsCount(FilterDto filterDto) {
        return dao.getProductCount(filterDto);
    }
}
