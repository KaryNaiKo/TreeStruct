package com.github.karynaiko.service;

import com.github.karynaiko.model.SimpleTree;
import com.github.karynaiko.repository.TreeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TreeServiceImpl implements TreeService {
    private TreeRepository repository;

    @Autowired
    public TreeServiceImpl(TreeRepository repository) {
        this.repository = repository;
    }

    @Override
    public SimpleTree getById(int id) {
        return repository.getById(id);
    }

    @Override
    public SimpleTree getRoot() {
        Integer rootId = repository.getRootId();
        return repository.findWithChildenById(rootId);
    }

    @Override
    public void delete(int id) {
        repository.delete(id);
    }

    @Override
    public void update(SimpleTree entity) {
        repository.update(entity);
    }

    @Override
    public SimpleTree create(int parentId, String text) {
        return repository.create(parentId, text);
    }

    @Override
    public SimpleTree findWithChildenById(int id) {
        return repository.findWithChildenById(id);
    }

}
