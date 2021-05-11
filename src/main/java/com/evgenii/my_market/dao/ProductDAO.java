package com.evgenii.my_market.dao;

import com.evgenii.my_market.dto.ProductDto;
import com.evgenii.my_market.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ProductDAO {
    @PersistenceContext
    EntityManager entityManager;

    public List<Product> getProductsPage(int p, int t, List<Object> paramsList) {

        TypedQuery<Product> query = entityManager.createQuery(
                "SELECT p FROM Product p WHERE" +
                        " p.productPrice >= :min_price and p.productPrice <= :max_price and" +
                        " p.productParams.productGender like :gender and  p.productTitle like :name " +
                        " order by p.productPrice ", Product.class)
                .setFirstResult(p)
                .setMaxResults(t);

        String title = "%" + paramsList.get(0) + "%";
        String gender =  paramsList.get(1) + "%";
        BigDecimal min = (BigDecimal) paramsList.get(2);
        BigDecimal max = (BigDecimal) paramsList.get(3);

        return query
                .setParameter("min_price", min)
                .setParameter("max_price", max)
                .setParameter("gender", gender)
                .setParameter("name", title)
                .getResultList();
    }

    public void saveNewProduct(Product newProduct) {

        entityManager.persist(newProduct);
        entityManager.flush();

    }

    public List<Product> findProductById(int productId) {

        TypedQuery<Product> query = entityManager.createQuery(
                "SELECT p FROM Product p where p.productId = :id", Product.class);
        return query
                .setParameter("id", productId)
                .getResultList();

    }

    public void update(Product product) {
        entityManager.merge(product);
        entityManager.flush();
    }
}
