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

    @Override
    public void delete(SimpleTree entity) {
        SimpleTree parent = (SimpleTree) entity.getParent();
        if (parent == null) {
            throw new ConstraintViolationException("Can not delete root", null, null);
        }
        parent.removeChildTree(entity);
        em.remove(entity);
        update(parent);
    }

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

    @Override
    public SimpleTree findByIdForDepth(Integer id, Integer depth) {
        SimpleTree treeById = getById(id);

        return initializeToDepth(depth, treeById);
    }

    SimpleTree initializeToDepth(int depth, SimpleTree tree) {
        if (tree == null) return null;
        initializeToDepth(0, depth, tree);
        return tree;
    }

    private void initializeToDepth(int currentDepth, int depth, SimpleTree tree) {
        if (currentDepth++ == depth) return;

        for (Tree child : (List<Tree<TreeElement>>) tree.getChildren()) {
            initializeToDepth(currentDepth, depth, (SimpleTree) child);
        }
    }
}
