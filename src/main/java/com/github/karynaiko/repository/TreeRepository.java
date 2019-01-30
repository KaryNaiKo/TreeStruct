package com.github.karynaiko.repository;

import com.github.karynaiko.model.SimpleTree;

public interface TreeRepository {
    SimpleTree getById(int id);
    Integer getRootId();
    void delete (int id);
    void update (SimpleTree entity);
    SimpleTree create(int parentId, String text);
    SimpleTree findWithChildenById(int id);
    SimpleTree move(int id, int parentId);
}
