package com.github.karynaiko.web;

import com.github.karynaiko.model.SimpleTree;
import com.github.karynaiko.service.TreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;

@RestController
public class AjaxController {
    @Autowired
    private TreeService service;

    @GetMapping(value = "/ajax/tree", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SimpleTree> modifyNode(@RequestParam Map<String,String> allRequestParams) {
        SimpleTree node = null;
        String text;
        int id;
        if(allRequestParams != null && !allRequestParams.isEmpty()) {
            String operation = allRequestParams.get("operation");
            if(operation != null) {
                switch (operation) {
                    case "get_root":
                        node = service.getRoot();
                        break;
                    case "get_node_by_id":
                        id = Integer.parseInt(Objects.requireNonNull(allRequestParams.get("id")));
                        node = service.findWithChildenById(id);
                        break;
                    case "create_node":
                        id = Integer.parseInt(Objects.requireNonNull(allRequestParams.get("id")));
                        text = Objects.requireNonNull(allRequestParams.get("text"));
                        node = service.create(id, text);
                        break;
                    case "rename_node":
                        id = Integer.parseInt(Objects.requireNonNull(allRequestParams.get("id")));
                        text = Objects.requireNonNull(allRequestParams.get("text"));
                        node = service.findWithChildenById(id);
                        node.getElement().setName(text);
                        service.update(node);
                        break;
                    case "delete_node":
                        id = Integer.parseInt(Objects.requireNonNull(allRequestParams.get("id")));
                        service.delete(id);
                        break;
                    case "move_node":
                        id = Integer.parseInt(Objects.requireNonNull(allRequestParams.get("id")));
                        int parentId = Integer.parseInt(Objects.requireNonNull(allRequestParams.get("parent")));
                        node = service.move(id, parentId);
                        break;
                    default:
                        //throw new Exception("Unsupported operation: " + operation);
                        break;
                }
            }
        }
        return new ResponseEntity<>(node, HttpStatus.OK);
    }
}
