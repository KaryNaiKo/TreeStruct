package com.github.karynaiko.web;

import com.github.karynaiko.model.SimpleTree;
import com.github.karynaiko.service.TreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ajax/tree")
public class AjaxController {
    @Autowired
    private TreeService service;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public SimpleTree getRoot() {
        return service.getRoot();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public SimpleTree getById(@PathVariable("id") int id) {
        return service.findWithChildenById(id);
    }
}
