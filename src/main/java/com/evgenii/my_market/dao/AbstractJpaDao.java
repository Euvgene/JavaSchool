/*
package com.evgenii.my_market.dao;

import com.evgenii.my_market.dto.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.List;

public abstract class AbstractJpaDao< T extends Serializable>   {
    private Class< T > clazz;

    @PersistenceContext
    EntityManager entityManager;

    public void setClazz( Class< T > clazzToSet ) {
        this.clazz = clazzToSet;
    }

    public T findOne( int id ){
        return entityManager.find( clazz, id );
    }
    public List< T > findAll(){
        return entityManager.createQuery( "from " + clazz.getName() )
                .getResultList();
    }

    public List< T > pagfindAll(int p, int t){
        return  entityManager.createQuery( "from " + clazz.getName() ).setFirstResult(p).setMaxResults(t)
                .getResultList();
    }

    public void save( T entity ){
        entityManager.persist( entity );
    }

    public void update( T entity ){
        entityManager.merge( entity );
    }

    public void delete( T entity ){
        entityManager.remove( entity );
    }
    public void deleteById( int entityId ){
        T entity = findOne( entityId );
        delete( entity );
    }
}*/
