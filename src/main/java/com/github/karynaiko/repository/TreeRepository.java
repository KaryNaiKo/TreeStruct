package com.github.karynaiko.repository;

import com.github.karynaiko.model.SimpleTree;

public interface TreeRepository {
    SimpleTree getById(int id);
    void delete (SimpleTree entity);
    void update (SimpleTree entity);
    void create(SimpleTree entity);
    SimpleTree findEntireTree();
}
