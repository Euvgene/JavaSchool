package com.evgenii.my_market.dao;

import com.evgenii.my_market.dto.ProductDto;
import com.evgenii.my_market.entity.Parameters;
import com.evgenii.my_market.entity.Product;
import lombok.Data;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ProductDAO extends AbstractJpaDao<Product> {
    @PersistenceContext
    EntityManager entityManager;

    public List<Product> pagfindAll(int p, int t, List<Object> paramsList) {
        TypedQuery<Product> query = entityManager.createQuery(
                "SELECT p FROM Product p WHERE" +
                        " p.productPrice > :min_price AND p.productPrice < :max_price" +
                        " order by p.productPrice ", Product.class)
                .setFirstResult(p)
                .setMaxResults(t);
        BigDecimal min = (BigDecimal) paramsList.get(2);
        BigDecimal max = (BigDecimal) paramsList.get(3);
        return query
                .setParameter("min_price", min)
                .setParameter("max_price", max)
                .getResultList();


    }
}
