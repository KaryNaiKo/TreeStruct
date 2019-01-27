$(function () {
    $.ajax({
        async: true,
        type: "GET",
        url: "http://localhost:8080/ajax/tree",
        dataType: "json",
        success: function (json) {
            createJSTree(json);
        },

        error: function (xhr, ajaxOptions, thrownError) {
            alert(xhr.status);
            alert(thrownError);
        }
    });
});

function createJSTree(jsondata) {
    $('#jstree').jstree({
        "plugins": [ "sort", "json_data" ],
        'core': {
            'data':  function (jsondata) {
                return {'id' : jsondata.id};
            }
        }
    });
}

// $(function () {
//     $('#jstree').jstree({
//             "plugins": [ "sort", "json_data" ],
//             'core': {
//                 'data': {
//                     'url': "ajax/tree",
//                     'data': function (node) {
//                         return {
//                             'id': node.id,
//                             'text' : "text" + node.id,
//                             'children' : node.children
//                         };
//                     }
//                 }
//             }
//         });
// });
// $('#jstree').jstree({
//     "core" : {
//         "animation" : 0,
//         "check_callback" : true,
//         "themes" : { "stripes" : true },
//         'data' : {
//             'url' : function (node) {
//                 return node.id === '#' ?
//                     'ajax/tree' : 'ajax/tree/' + node.id;
//             },
//             'data' : function (node) {
//                 return { 'id' : node.id };
//             }
//         }
//     },
//     "types" : {
//         "#" : {
//             "max_children" : 1,
//             "max_depth" : 4,
//             "valid_children" : ["root"]
//         },
//         "root" : {
//             "icon" : "/static/3.3.7/assets/images/tree_icon.png",
//             "valid_children" : ["default"]
//         },
//         "default" : {
//             "valid_children" : ["default","file"]
//         },
//         "file" : {
//             "icon" : "glyphicon glyphicon-file",
//             "valid_children" : []
//         }
//     },
//     "plugins" : [
//         "contextmenu", "dnd", "search",
//         "state", "types", "wholerow"
//     ]
// });