package com.evgenii.my_market.dao.api;

import com.evgenii.my_market.dto.FilterDto;
import com.evgenii.my_market.entity.Product;

import java.math.BigInteger;
import java.util.List;

/**
 * Interface for basic CRUD operations in Product entity.
 *
 * @author Boznyakov Evgenii
 */
public interface ProductDAO {

     /**
      * Get  entity's from database.
      *
      * @param page number of page
      * @param total max result to find
      * @param filterDto entity object of filter
      */
     List<Product> getProductsPage(int page, int total, FilterDto filterDto) ;

     /**
      * Save entity to database.
      *
      * @param newProduct entity object to save
      */
     void saveNewProduct(Product newProduct);

     /**
      * Find by id entity from database.
      *
      * @param productId which product to find
      */
     List<Product> findProductById(int productId);

     /**
      * Update  entity in database.
      *
      * @param product which product to update
      */
     void update(Product product);


     /**
      * get  count of entity's from database.
      *
      * @param filterDto entity object of filter
      */
     BigInteger getProductCount(FilterDto filterDto);
}
