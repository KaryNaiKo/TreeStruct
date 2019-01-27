package com.github.karynaiko.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;


@Entity
@Table(name = "tree_element")
@DiscriminatorColumn(name = "element_type")
@DiscriminatorValue("BASE_ELEMENT")
public class TreeElement {

    private Integer treeId;
    private String name;
    private String description;

    @JsonIgnore
    private Tree<? extends TreeElement> tree;

    TreeElement() {
    }

    public TreeElement(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    @Id
    @GenericGenerator(name = "treeIdGenerator", strategy = "foreign",
            parameters = @Parameter(name = "property", value = "tree"))
    @GeneratedValue(generator = "treeIdGenerator")
    @Column(name = "tree_id")
    public Integer getTreeId() {
        return treeId;
    }

    void setTreeId(Integer treeId) {
        this.treeId = treeId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    public Tree<? extends TreeElement> getTree() {
        return tree;
    }

    <T extends TreeElement> void setTree(Tree<T> tree) {
        this.tree = tree;
    }

    @Override
    public String toString() {
        return getName();
    }

}
