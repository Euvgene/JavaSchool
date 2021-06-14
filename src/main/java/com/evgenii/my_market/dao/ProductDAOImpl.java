package com.evgenii.my_market.dao;

import com.evgenii.my_market.dao.api.ProductDAO;
import com.evgenii.my_market.dto.FilterDto;
import com.evgenii.my_market.entity.Product;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.math.BigInteger;
import java.util.List;

@Repository
public class ProductDAOImpl implements ProductDAO {
    @PersistenceContext
    EntityManager entityManager;

    public List<Product> getProductsPage(int p, int t, FilterDto filterDto) {
        String availableQuery = " and p.productQuantity >= :quantity";
        if (filterDto.getNotAvailable() == 1) {
            availableQuery = " and p.productQuantity = :quantity";
        }
        TypedQuery<Product> query = entityManager.createQuery(
                "SELECT p FROM Product p WHERE" +
                        " p.productPrice >= :min_price and p.productPrice <= :max_price and" +
                        " p.productParams.productGender like :gender and  p.productTitle like :name" +
                        availableQuery +
                        " and p.category.categoryName like :category" +
                        " order by p.productPrice ", Product.class)
                .setFirstResult(p)
                .setMaxResults(t);

        String title = "%" + filterDto.getName() + "%";
        String category = "%" + filterDto.getCategory() + "%";
        String gender = filterDto.getGender() + "%";

        return query
                .setParameter("min_price", filterDto.getMinPrice())
                .setParameter("max_price", filterDto.getMaxPrice())
                .setParameter("gender", gender)
                .setParameter("category", category)
                .setParameter("quantity", filterDto.getQuantity())
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

    public BigInteger getProductCount(FilterDto filterDto) {
        String availableQuery = "and p.quantity >= :quantity";
        if (filterDto.getNotAvailable() == 1) {
            availableQuery = "and p.quantity = :quantity";
        }
        Query query = entityManager.createNativeQuery(
                " SELECT COUNT(*) FROM  products p INNER JOIN parameters param ON p.product_param = param.parameters_id " +
                        "INNER JOIN category cat ON p.category = cat.category_id where " +
                        "p.price >=:min_price and p.price <=:max_price and " +
                        "p.productTitle like :name and param.gender like :gender and " +
                        "cat.name like :category " +
                        availableQuery);

        String title = "%" + filterDto.getName() + "%";
        String gender = filterDto.getGender() + "%";
        String category = "%" + filterDto.getCategory() + "%";

        return (BigInteger) query
                .setParameter("min_price", filterDto.getMinPrice())
                .setParameter("max_price", filterDto.getMaxPrice())
                .setParameter("gender", gender)
                .setParameter("category", category)
                .setParameter("quantity", filterDto.getQuantity())
                .setParameter("name", title)
                .getSingleResult();
    }
}
