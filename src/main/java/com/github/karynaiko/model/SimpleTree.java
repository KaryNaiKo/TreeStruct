package com.github.karynaiko.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("SIMPLE_TREE")
public class SimpleTree extends Tree<TreeElement> {

    SimpleTree() {
    }
}
