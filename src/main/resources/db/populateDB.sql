DELETE FROM tree_element;
DELETE FROM tree;
ALTER SEQUENCE global_seq RESTART WITH 100000;

-- insert into tree (parent_id, tree_type) values
--         (null, 'SIMPLE_TREE'),
--         (100000, 'SIMPLE_TREE');
--
-- insert into tree_element (description, name, element_type, tree_id) values
--         ('child1', 'child1', 'BASE_ELEMENT', 100000);