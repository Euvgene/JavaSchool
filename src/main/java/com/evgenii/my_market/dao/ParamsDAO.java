package com.evgenii.my_market.dao;

import com.evgenii.my_market.entity.Parameters;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class ParamsDAO {
    @PersistenceContext
    EntityManager entityManager;

    public List<Parameters> findAllParams() {
        TypedQuery<Parameters> query = entityManager.createQuery(
                "SELECT p FROM Parameters p ", Parameters.class);

        return query.getResultList();
    }

    public Parameters saveNewCategory(Parameters newParameters) {
            entityManager.getTransaction().begin();
            entityManager.persist(newParameters);
            entityManager.getTransaction().commit();
            return newParameters;
    }
}
