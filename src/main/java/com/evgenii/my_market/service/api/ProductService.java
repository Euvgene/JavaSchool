package com.evgenii.my_market.service.api;



import com.evgenii.my_market.dto.FilterDto;
import com.evgenii.my_market.dto.ProductDto;
import com.evgenii.my_market.entity.Product;

import java.math.BigInteger;
import java.util.List;

public interface ProductService {

    List<ProductDto> getProductsPage(FilterDto filterDto);

    void save(ProductDto newProduct);

    List<Product> findProductById(int productId);

    Product getProductById(int id);

    void update(ProductDto product);

    void deleteProductById(int id);

    BigInteger getProductsCount(FilterDto filterDto);
}
