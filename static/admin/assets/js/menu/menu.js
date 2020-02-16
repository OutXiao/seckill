var token = JSON.parse(sessionStorage.getItem('token'));

initMenuList();

$.ajax({
    type: 'get',
    url: "http://" + host + "/sys/admin/permissions/allParent",
    headers: {"token": token},
    success: function (parents) {
        var content = "";
        $.each(parents.object, function (i, child) {
            content += "<option value='" + child.id + "'>" + child.name + "</option>"
        });
        $("#parent").append(content);
        $("#add-parent").append(content);
    }

});

function initMenuList() {
    $.ajax({
        type: 'get',
        url: "http://" + host + "/sys/admin/permissions/all",
        headers: {"token": token},
        success: function (permissions) {
            var content = "";
            $.each(permissions.data, function (i, permission) {
                content += "<tr data-tt-id='" + permission.id + "' data-tt-parent-id='" + permission.parentId + "'>"
                    + "<td>" + permission.name + "</td>"
                    + "<td>" + permission.id + "</td>"
                    + "<td>" + permission.href + "</td>"
                    + "<td>" + permission.permission + "</td>"
                    + "<td>" + permission.sort + "</td>"
                    + "<td><button class=' btn btn-warning' onclick='editPermission(" + permission.id + ")'>编辑</button>&nbsp;&nbsp;&nbsp;&nbsp;" +
                    "<button class=' btn btn-danger' onclick='delPermission(" + permission.id + ")'>删除</button>" +
                    "</td></tr>";

            });
            $("#treeTable-body").append(content);


            //一定要发在添加完之后，不然发送ajax为异步，渲染时还没有将数据填充进去
            $("#example-basic").treetable({
                expandable : true,
                initialState:"expanded",
                //expandable : true
                clickableNodeNames:false,//点击节点名称也打开子节点.
                indent : 30//每个分支缩进的像素数。
            });
        }
    });

    // Highlight a row when selected
    /*$("#example-basic tbody").on("mousedown", "tr", function() {
        $(".selected").not(this).removeClass("selected");
        $(this).toggleClass("selected");
    });*/
}




// 编辑回显
var pId;
function editPermission(permissionId) {

    $('.modal-backdrop').remove();  // 去掉灰色遮罩
    pId = permissionId;
    $("#editModal").modal('show');
    $.ajax({
        type: 'get',
        url: "http://" + host + "/sys/admin/permissions/getPermission/"+permissionId,
        headers: {"token": token},
        success: function (result) {
            if (result.code == '200'){
                $('input[name="name"]').val(result.object.name);
                $('input[name="sort"]').val(result.object.sort);
                $('input[name="icon"]').val(result.object.icon);
                $('input[name="url"]').val(result.object.href);
                if (result.object.parentId == null)
                    $("#parent").val("0");
                else
                    $("#parent").val(result.object.parentId+"");
            }
        },
        error: function (result) {
            toastr.error(result.message);
        }
    });


}

// 提交编辑
$("#edit").click(function () {
    $('.modal-backdrop').remove();  // 去掉灰色遮罩
    $.ajax({
        type: 'post',
        url: "http://" + host + "/sys/admin/permissions/edit/"+pId,
        headers: {"token": token},
        data:{
            "name": $('input[name="name"]').val(),
            "icon": $('input[name="icon"]').val(),
            "url": $('input[name="url"]').val(),
            "sort": $('input[name="sort"]').val(),
            "parentId": $("#parent").val()
        },
        success: function (result) {
            $("#editModal").modal('hide');
            //$("#page-wrapper").load("/assets/pages/menu/menuList.html");
            location.reload();
            toastr.info(result.message);
            $("#editModal").modal('hide');
        },
        error: function (result) {
            toastr.error(result.message);
        }
    });
});


// 添加
$("#add").click(function () {
    $('.modal-backdrop').remove();  // 去掉灰色遮罩
    $.ajax({
        type: 'post',
        url: "http://" + host + "/sys/admin/permissions/addPermission",
        headers: {"token": token},
        data:{
            "name": $('input[name="add-name"]').val(),
            "icon": $('input[name="add-icon"]').val(),
            "url": $('input[name="add-url"]').val(),
            "sort": $('input[name="add-sort"]').val(),
            "parentId": $("#add-parent").val()
        },
        success: function (result) {
            toastr.info(result.message);
            $("#editModal").modal('hide');
            $("#page-wrapper").load("/assets/pages/menu/menuList.html");
        },
        error: function (result) {
            toastr.error(result.message);
        }
    });
});


function delPermission(permissionId) {
    $.ajax({
        type: 'get',
        url: "http://" + host + "/sys/admin/permissions/del/"+permissionId,
        headers: {"token": token},
        success: function (result) {
            toastr.info(result.message);
            location.reload();
        },
        error: function (result) {
            toastr.error(result.message);
        }
    });
}

