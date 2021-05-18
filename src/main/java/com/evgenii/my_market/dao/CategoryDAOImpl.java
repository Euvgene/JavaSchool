package com.evgenii.my_market.dao;

import com.evgenii.my_market.dao.api.CategoryDAO;
import com.evgenii.my_market.entity.Category;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class CategoryDAOImpl implements CategoryDAO {

    @PersistenceContext
    EntityManager entityManager;

    public List<Category> findAll() {
        TypedQuery<Category> query = entityManager.createQuery(
                "SELECT c FROM Category c", Category.class);

        return query
                .getResultList();
    }

    public void saveNewCategory(Category newCategory) {
        entityManager.persist(newCategory);
        entityManager.flush();
    }

    public Category getActiveCategory(String value) {
        Category category;
        try {
            category = (Category) entityManager.createQuery("SELECT c FROM Category c where c.categoryName = :name")
                    .setParameter("name", value)
                    .getSingleResult();
        } catch (NoResultException e) {
            category = null;
        }
        return category;
    }
}
