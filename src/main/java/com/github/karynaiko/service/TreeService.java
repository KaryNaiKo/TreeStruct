package com.github.karynaiko.service;

import com.github.karynaiko.model.SimpleTree;

public interface TreeService {
    SimpleTree getById(int id);
    SimpleTree getRoot();
    void delete (int id);
    void update (SimpleTree entity);
    SimpleTree create(int parentId, String text);
    SimpleTree findWithChildenById(int id);
    SimpleTree move(int id, int parentId);
}
