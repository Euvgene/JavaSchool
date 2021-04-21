package com.evgenii.my_market.dao;

import com.evgenii.my_market.entity.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CategoryDAO {

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
}
