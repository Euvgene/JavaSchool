package com.evgenii.my_market.service;


import com.evgenii.my_market.dao.ProductDAO;

import com.evgenii.my_market.dto.FilterDto;
import com.evgenii.my_market.dto.ProductDto;
import com.evgenii.my_market.entity.Product;
import com.evgenii.my_market.rest_controller.AuthController;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final int TOTAL_PRODUCTS_IN_PAGE = 8;
    private final int CHECK_PAGE_NUMBER = 1;
    private final ProductDAO dao;
    private final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);


    @Transactional
    public List<ProductDto> getProductsPage(FilterDto filterDto) { // todo rename p  and magic NUMBERS
        int total = TOTAL_PRODUCTS_IN_PAGE;
        int page = filterDto.getPage();
        if (filterDto.getPage() != CHECK_PAGE_NUMBER) {
            page = (page - CHECK_PAGE_NUMBER) * total + CHECK_PAGE_NUMBER;
        }

        return dao.getProductsPage(page - 1, total, filterDto).stream()
                .map(ProductDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void save(ProductDto newProduct) {
        Product product = new Product(newProduct);
        dao.saveNewProduct(product);
        LOGGER.info("Create product with name " + product.getProductTitle());
    }

    @Transactional
    public List<Product> findProductById(int productId) {
        return dao.findProductById(productId);
    }

    @Transactional
    public Product getProductById(int id) {
        return dao.findProductById(id).get(0);
    }

    @Transactional
    public void update(ProductDto product) {
        Product updateProduct = new Product(product);
        dao.update(updateProduct);
        LOGGER.info("Update product with name " + updateProduct.getProductTitle());
    }

    @Transactional
    public void deleteProductById(int id) {
        Product product = dao.findProductById(id).get(0);
        product.setProductQuantity((byte) 0);
        LOGGER.info("Set product quantity " + product.getProductTitle());
    }

    public BigInteger getProductsCount(FilterDto filterDto) {
        return dao.getProductCount(filterDto);
    }
}
