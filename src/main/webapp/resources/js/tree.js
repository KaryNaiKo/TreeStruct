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
        .on('load_node.jstree', function (e, data) {
            $("#jstree").jstree(true).hide_node(data);
        })
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
                        'ajax/tree' : 'ajax/tree/' + node.id;
                },
                'data': function (node) {
                    return {
                        'id': node.id
                    };
                }
            }
        }
    });
});