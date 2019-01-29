package com.github.karynaiko.service;

import com.github.karynaiko.model.SimpleTree;

public interface TreeService {
    SimpleTree getById(int id);
    SimpleTree getRoot();
    void delete (int id);
    void update (SimpleTree entity);
    void create(SimpleTree entity);
    SimpleTree findWithChildenById(int id);
}
