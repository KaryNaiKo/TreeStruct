package com.github.karynaiko.repository;

import com.github.karynaiko.model.SimpleTree;
import com.github.karynaiko.model.Tree;
import com.github.karynaiko.model.TreeElement;
import org.hibernate.exception.ConstraintViolationException;
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

    @Transactional
    @Override
    public void delete(int id) {
        SimpleTree entity = findWithChildenById(id);
        SimpleTree parent = (SimpleTree) entity.getParent();
        if (parent == null) {
            throw new ConstraintViolationException("Can not delete root", null, null);
        }
        parent.removeChildTree(entity);
        em.remove(entity);
        update(parent);
    }

    @Transactional
    @Override
    public void update(SimpleTree entity) {
        em.merge(entity);
    }

    @Transactional
    @Override
    public void create(SimpleTree entity) {
        em.persist(entity);
    }

    public Integer getRootId() {
        return (Integer) em.createNamedQuery("findRootNode")
                .setParameter("aClass", SimpleTree.class.getAnnotation(DiscriminatorValue.class).value())
                .getSingleResult();
    }

    @Override
    public SimpleTree findWithChildenById(int id) {
         return (SimpleTree) em.createNamedQuery("findAllNodesWithTheirChildren")
                .setParameter("aClass", SimpleTree.class.getAnnotation(DiscriminatorValue.class).value())
                .setParameter("id", id)
                .getSingleResult();
    }
}
