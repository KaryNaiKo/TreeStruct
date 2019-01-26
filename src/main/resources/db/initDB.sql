DROP TABLE IF EXISTS tree_element;
DROP TABLE IF EXISTS tree;
DROP SEQUENCE IF EXISTS global_seq;

CREATE SEQUENCE global_seq START 100000;

CREATE TABLE tree
(
    tree_type       VARCHAR(31) NOT NULL,
    id              INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    parent_id       INTEGER,
    children_order  INTEGER
);

CREATE TABLE tree_element
(
    element_type    VARCHAR(31) NOT NULL,
    tree_id         INTEGER NOT NULL,
    description     VARCHAR(255),
    name            VARCHAR(255),
    FOREIGN KEY (tree_id) REFERENCES tree (id)
);