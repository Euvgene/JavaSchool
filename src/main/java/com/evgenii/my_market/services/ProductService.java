package com.evgenii.my_market.services;


import com.evgenii.my_market.dao.ProductDAO;
import com.evgenii.my_market.dto.ProductDto;
import com.evgenii.my_market.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
   private final ProductDAO dao;

    public List<ProductDto> pagGetAllProducts(int p, List<Object> paramsList) {
        int total = 8;
        if (p != 1) {
            p = (p - 1) * total + 1;
        }

        return dao.pagfindAll(p - 1, total,paramsList).stream().map(ProductDto::new).collect(Collectors.toList());
    }

    public void save(Product newProduct) {
        dao.saveNewProduct(newProduct);
    }
}
