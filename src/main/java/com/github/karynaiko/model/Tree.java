package com.github.karynaiko.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import org.hibernate.Hibernate;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import javax.persistence.*;
import java.util.*;

@NamedQueries({
        @NamedQuery(name = "findRootNode", query = "select id from Tree t where t.class = :aClass and t.parent = null"),
        @NamedQuery(name = "findAllNodesWithTheirChildren", query = "SELECT DISTINCT t from Tree t left join fetch t.children where t.class = :aClass and t.id = :id")
})

@Entity
@Table(name = "tree")
@DynamicUpdate
@DynamicInsert
@DiscriminatorColumn(name = "tree_type")
@DiscriminatorValue("BASE_TREE")
public abstract class Tree<T extends TreeElement> {

    private Integer id;
    @JsonProperty("text")
    private T element;
    @JsonIgnore
    private Tree<T> parent;
    private List<Tree<T>> children = new LinkedList<>();

    // used by hibernate
    Tree() {
    }

    public static <R extends Tree<S>, S extends TreeElement> R createRoot(S treeElement, Class<R> type) {
        R root;
        try {
            root = type.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        root.setElement(treeElement);
        treeElement.setTree(root);
        return root;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    void setId(Integer id) {
        this.id = id;
    }

    @OneToOne(mappedBy = "tree", cascade = CascadeType.ALL)
    public T getElement() {
        return element;
    }

    void setElement(T element) {
        this.element = element;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    public Tree<T> getParent() {
        return parent;
    }

    private Tree<T> setParent(Tree<T> parent) {
        this.parent = parent;
        return this;
    }

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    @OrderColumn(name = "children_order")
    public List<Tree<T>> getChildren() {
        return children;
    }

    public void setChildren(List<Tree<T>> children) {
        this.children = children;
    }

    public Tree<T> addChildTree(Tree<T> childTree) {
        children.add(childTree);
        return childTree.setParent(this);
    }

    public <R extends Tree<T>> R addChildTree(T treeElement) {
        R newChild;
        try {
            //noinspection unchecked
            newChild = (R) this.getClass().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        newChild.setElement(treeElement);
        treeElement.setTree(newChild);

        this.addChildTree(newChild);
        return newChild;
    }

    public Tree<T> removeChildTree(Tree<T> childTree) {
        children.remove(childTree);
        return this;
    }

    @Transient
    public String getPath() {
        List<T> parts = new ArrayList<>(Arrays.asList(this.getElement()));
        for (Tree<T> root = this.getParent(); root != null; root = root.getParent()) {
            parts.add(root.getElement());
        }
        return Joiner.on(".").join(Lists.reverse(parts));
    }

    @Transient
    public Integer getDepth() {
        return parent == null ? 0 : (parent.getDepth() + 1);
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }

    public String prettyPrint() {
        return prettyPrint(this, "", true).trim();
    }

    private String prettyPrint(Tree<T> tree, String prefix, boolean isTail) {
        StringBuilder stringBuilder = new StringBuilder(prefix).append((isTail ? "└── " : "├── ")).append(tree.element).append("\n");
        if (!Hibernate.isInitialized(tree.children)) return stringBuilder.toString();

        for (Iterator<Tree<T>> iterator = tree.children.iterator(); iterator.hasNext(); ) {
            stringBuilder.append(prettyPrint(iterator.next(), prefix + (isTail ? "    " : "│   "), !iterator.hasNext()));
        }
        return stringBuilder.toString();
    }

    public Tree<T> findTree(String elementName) {
        return findTree(this, elementName);
    }

    Tree<T> findTree(Tree<T> currentTree, String elementName) {
        if (currentTree.element.getName().equals(elementName)) {
            return currentTree;
        }
        for (Tree<T> child : currentTree.children) {
            Tree<T> tree = findTree(child, elementName);
            if (tree != null) {
                return tree;
            }
        }
        return null;
    }

    public void move(final Tree<T> newParent) {
        if (newParent == null) throw new AssertionError("newParent can not be null");

        this.parent.children.remove(this);
        this.parent = newParent;
        newParent.children.add(this);
    }

    public List<Tree<T>> toList() {
        return toList(this, new ArrayList<Tree<T>>());
    }

    List<Tree<T>> toList(Tree<T> tree, List<Tree<T>> allNodes) {
        allNodes.add(tree);
        for (Tree<T> child : tree.<T>getChildren()) {
            toList(child, allNodes);
        }
        return allNodes;
    }

}
