package com.github.karynaiko.service;

import com.github.karynaiko.model.SimpleTree;

public interface TreeService {
    SimpleTree getById(int id);
    void delete (SimpleTree entity);
    void update (SimpleTree entity);
    void create(SimpleTree entity);
    SimpleTree findEntireTree();
}
