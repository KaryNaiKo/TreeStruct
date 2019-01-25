package com.github.karynaiko.repository;

import com.github.karynaiko.model.Tree;

import java.util.List;

public interface TreeRepository<T extends Tree> extends Repository<T> {

    List<T> findByName(String name);

    T findRoot();

    T findByIdForDepth(Integer id, Integer depth);
}
