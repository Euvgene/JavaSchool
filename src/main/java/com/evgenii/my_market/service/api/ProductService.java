package com.evgenii.my_market.service.api;



import com.evgenii.my_market.dto.FilterDto;
import com.evgenii.my_market.dto.ProductDto;
import com.evgenii.my_market.entity.Product;

import java.math.BigInteger;
import java.util.List;

/**
 * Interface, containing list of required business-logic methods regarding
 * {@linkplain com.evgenii.my_market.entity.Product Product} entity.
 *
 * @author Boznyakov Evgenii
 */
public interface ProductService {

    /**
     * Getting product list using filter
     *
     * @param filterDto {@linkplain com.evgenii.my_market.dto.FilterDto} DTO for filter request
     * @return list of  {@linkplain com.evgenii.my_market.dto.ProductDto ProductDto}
     */
    List<ProductDto> getProductsPage(FilterDto filterDto);

    /**
     * Save new product
     *
     * @param newProduct {@linkplain com.evgenii.my_market.dto.ProductDto} DTO for product
     */
    void save(ProductDto newProduct);

    /**
     * Getting product by id
     *
     * @param id product id
     * @return {@linkplain com.evgenii.my_market.entity.Product}
     */
    Product getProductById(int id);

    /**
     * Update product
     *
     * @param product {@linkplain com.evgenii.my_market.dto.ProductDto}
     */
    void update(ProductDto product);

    /**
     * Set product quantity to 0
     *
     * @param id product id
     */
    void deleteProductById(int id);

    /**
     * Getting product count using filter
     *
     * @param filterDto {@linkplain com.evgenii.my_market.dto.FilterDto} DTO for filter request
     * @return BigInteger
     */
    BigInteger getProductsCount(FilterDto filterDto);
}
