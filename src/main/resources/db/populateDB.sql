DELETE FROM tree_element;
DELETE FROM tree;
ALTER SEQUENCE global_seq RESTART WITH 100000;

insert into tree (tree_type, parent_id, children_order) values
  ('SIMPLE_TREE', null, null),
  ('SIMPLE_TREE',	100000,	0),
  ('SIMPLE_TREE',	100000,	1),
  ('SIMPLE_TREE',	100000,	2),
  ('SIMPLE_TREE',	100000,	3),
  ('SIMPLE_TREE',	100004,	0);



insert into tree_element (element_type, tree_id, description, name) values
  ('BASE_ELEMENT',	100001,	'first child1',	'first child1'),
  ('BASE_ELEMENT',	100002,	'first child2',	'first child2'),
  ('BASE_ELEMENT',	100003,	'first child3',	'first child3'),
  ('BASE_ELEMENT',	100005,	'second children',	'second children'),
  ('BASE_ELEMENT',	100004,	'branch of tree',	'branch'),
  ('BASE_ELEMENT',	100000,	'root of tree',	'root');

