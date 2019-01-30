$.ajaxSetup({
    converters: {
        "text json": function (stringData) {
            const json = JSON.parse(stringData);
            $(json).each(function () {
                json.text = json.text.name;
                for(i=0; i<json.children.length; i++) {
                    json.children[i].text = json.children[i].text.name;
                    json.children[i].children = true;
                }
            });
            return json;
        }
    }
});


$(function () {
    $('#jstree')
        .jstree({
            "plugins": ["json_data", "dnd", "contextmenu"],
            'core': {
                "themes": {
                    "theme": "default",
                    "dots": false,
                    "icons": true,
                    "url": "resources/css/themes/default/style.css"
                },
                'data': {
                    'url': function (node) {
                        return node.id === '#' ?
                            'ajax/tree?operation=get_root' : 'ajax/tree?operation=get_node_by_id&id=' + node.id;
                    },
                    'data': function (node) {
                        return {
                            'id': node.id
                        };
                    }
                },
                'check_callback' : function(o, n, p, i, m) {
                    if(m && m.dnd && m.pos !== 'i') { return false; }
                    if(o === "move_node" || o === "copy_node") {
                        if(this.get_node(n).parent === this.get_node(p).id) { return false; }
                    }
                    return true;
                }
            }
        })
        .on('create_node.jstree', function (e, data) {
            $.get('ajax/tree?operation=create_node', { 'id' : data.node.parent, 'text' : data.node.text })
                .done(function (d) {
                    data.instance.set_id(data.node, d.id);
                })
                .fail(function () {
                    data.instance.refresh();
                });
        })
        .on('delete_node.jstree', function (e, data) {
            $.get('ajax/tree?operation=delete_node', { 'id' : data.node.id })
                .done(function () {
                    data.instance.refresh();
                });
        })
        .on('move_node.jstree', function (e, data) {
            $.get('ajax/tree?operation=move_node', { 'id' : data.node.id, 'parent' : data.parent })
                .done(function (d) {
                    data.instance.refresh();
                })
                .fail(function () {
                    data.instance.refresh();
                });
        })
        .on('rename_node.jstree', function (e, data) {
            $.get('ajax/tree?operation=rename_node', {'id': data.node.id, 'text': data.text})
                .done(function (d) {
                    data.instance.set_id(data.node, d.id);
                })
                .fail(function () {
                    data.instance.refresh();
                });
        });
});