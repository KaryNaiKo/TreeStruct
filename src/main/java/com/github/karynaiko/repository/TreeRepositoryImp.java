package com.github.karynaiko.repository;

import com.github.karynaiko.model.SimpleTree;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.DiscriminatorValue;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class TreeRepositoryImp implements TreeRepository{
    @PersistenceContext
    private EntityManager em;

    @Override
    public SimpleTree getById(int id) {
        return em.find(SimpleTree.class, id);
    }

    @Override
    public void delete(SimpleTree entity) {

    }

    @Override
    public void update(SimpleTree entity) {

    }

    @Transactional
    @Override
    public void create(SimpleTree entity) {
        em.persist(entity);
    }

    public SimpleTree findEntireTree() {
//        Integer rootId = (Integer) em.createNamedQuery("findRootNode")
//                .setParameter("aClass", SimpleTree.class.getAnnotation(DiscriminatorValue.class).value())
//                .getSingleResult();

        List list = em.createNamedQuery("findAllNodesWithTheirChildren")
                .setParameter("aClass", SimpleTree.class.getAnnotation(DiscriminatorValue.class).value())
                .getResultList();

        //return em.getReference(SimpleTree.class, rootId);
        return (SimpleTree) list.get(0);
    }
}
