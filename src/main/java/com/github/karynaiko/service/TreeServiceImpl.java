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
    public void delete(SimpleTree entity) {
        repository.delete(entity);
    }

    @Override
    public void update(SimpleTree entity) {
        repository.update(entity);
    }

    @Override
    public void create(SimpleTree entity) {
        repository.create(entity);
    }

    @Override
    public SimpleTree findEntireTree() {
        return repository.findEntireTree();
    }
}