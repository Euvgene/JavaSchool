package com.evgenii.my_market.dao.api;

import com.evgenii.my_market.dto.FilterDto;
import com.evgenii.my_market.entity.Product;

import java.math.BigInteger;
import java.util.List;


public interface ProductDAO {

     List<Product> getProductsPage(int p, int t, FilterDto filterDto) ;

     void saveNewProduct(Product newProduct);

     List<Product> findProductById(int productId);

     void update(Product product);

     BigInteger getProductCount(FilterDto filterDto);
}
