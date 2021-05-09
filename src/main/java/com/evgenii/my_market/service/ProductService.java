package com.evgenii.my_market.service;


import com.evgenii.my_market.dao.ProductDAO;

import com.evgenii.my_market.dto.ProductDto;
import com.evgenii.my_market.entity.Product;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductDAO dao;

    public List<ProductDto> getProductsPage(int page, List<Object> paramsList) { // todo rename p  and magic NUMBERS
        int total = 8;
        if (page != 1) {
            page = (page - 1) * total + 1;
        }

        return dao.getProductsPage(page - 1, total, paramsList).stream()
                .map(ProductDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void save(ProductDto newProduct) {
        Product product = new Product(newProduct);
        dao.saveNewProduct(product);
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
    }

    @Transactional
    public void deleteProductById(int id) {
        Product product = dao.findProductById(id).get(0);
        product.setProductQuantity((byte) 0);
    }
}
