package com.evgenii.my_market.services;

import com.evgenii.my_market.dao.AbstractJpaDao;
import com.evgenii.my_market.dao.ProductDAO;
import com.evgenii.my_market.dto.ProductDto;
import com.evgenii.my_market.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    ProductDAO dao;

    @Autowired
    public void setDao(ProductDAO productDAO) {
        dao = productDAO;
        productDAO.setClazz(Product.class);
    }

    public Product getProductById(int id) {
        return dao.findOne(id);
    }

    public List<ProductDto> pagGetAllProducts(int p, List<Object> paramsList) {
        int total = 8;
        if (p != 1) {
            p = (p - 1) * total + 1;
        }

        return dao.pagfindAll(p - 1, total,paramsList).stream().map(ProductDto::new).collect(Collectors.toList());
    }
}
