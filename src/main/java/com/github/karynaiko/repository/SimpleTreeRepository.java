package com.github.karynaiko.repository;

import com.github.karynaiko.model.SimpleTree;
import org.hibernate.Session;

public class SimpleTreeRepository extends AbstractTreeRepository<SimpleTree> {

    private final Session session;

    public SimpleTreeRepository(Session session) {
        this.session = session;
    }

    @Override
    protected Session getCurrentSession() {
        return session;
    }

    @Override
    protected Class<SimpleTree> getPersistentClass() {
        return SimpleTree.class;
    }
}
