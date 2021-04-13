package com.evgenii.my_market.services;

import com.evgenii.my_market.dao.AbstractJpaDao;
import com.evgenii.my_market.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    AbstractJpaDao<Product> dao;

    @Autowired
    public void setDao(AbstractJpaDao<Product> productDAO){
        dao = productDAO;
        dao.setClazz(Product.class);
    }

    public Product getProductById(int id){
        return dao.findOne(id);
    }
}
