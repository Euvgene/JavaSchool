package com.evgenii.my_market.services;


import com.evgenii.my_market.dao.ProductDAO;
import com.evgenii.my_market.dto.ProductDto;
import com.evgenii.my_market.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductDAO dao;

    public List<ProductDto> getProductsPage(int p, List<Object> paramsList) { // todo rename p  and magic NUMBERS
        int total = 8;
        if (p != 1) {
            p = (p - 1) * total + 1;
        }

        return dao.getProductsPage(p - 1, total, paramsList).stream() // todo rename method name
                .map(ProductDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void save(Product newProduct) {
        dao.saveNewProduct(newProduct);
    }

    public List<Product> findProductById(int productId) {
        return dao.findProductById(productId);
    }
}
