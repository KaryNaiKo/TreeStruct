package com.github.karynaiko.repository;

import com.github.karynaiko.model.SimpleTree;
import com.github.karynaiko.model.Tree;

public interface TreeRepository {
    SimpleTree getById(int id);
    Integer getRootId();
    void delete (int id);
    void update (SimpleTree entity);
    void create(SimpleTree entity);
    SimpleTree findWithChildenById(int id);
}
