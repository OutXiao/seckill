/* 前后端数据交互*/
// /!*标签初始化*!/
/*$.ajax({
    type: "post",
    url: "http://localhost:8060/sys/admin/user/allUser",
    data: {"pageIndex":1,
            "pageSize":10},
    headers: {"Authorization": token},
    success: function (data) {
        var category='';
        $.each(data.data,function (index,item) {
            category+="<option value='"+item.id+"'>"+item.name+"</option>"

        });
        $("#category_id").append(category);
    },
    error: function (data) {
        toastr.error("标签加载异常"+data)
    }
});*/
var token = JSON.parse(sessionStorage.getItem('token'));
var table;

// 系统角色初始化
/*$.ajax({
    type: "get",
    url: "http://localhost:8060/sys/admin/role/allRole",
    headers: {"token": token},
    success: function (result) {
        var roles='';
        $.each(result.data,function (index,item) {
            roles+="<option value='"+item.id+"'>"+item.name+"</option>"

        });
        $("#edit-role").append(roles);
        $("#add-role").append(roles);
    },
    error: function (data) {
        toastr.error("标签加载异常"+data)
    }
});*/


// 所有商品初始化
$(function () {

    //提示信息
    var lang = {
        "sProcessing": "处理中...",
        "sLengthMenu": "每页 _MENU_ 项",
        "sZeroRecords": "没有匹配结果",
        "sInfo": "当前显示第 _START_ 至 _END_ 项，共 _TOTAL_ 项。",
        "sInfoEmpty": "当前显示第 0 至 0 项，共 0 项",
        "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
        "sInfoPostFix": "",
        "sSearch": "搜索:",
        "sUrl": "",
        "sEmptyTable": "表中数据为空",
        "sLoadingRecords": "载入中...",
        "sInfoThousands": ",",
        "oPaginate": {
            "sFirst": "首页",
            "sPrevious": "上页",
            "sNext": "下页",
            "sLast": "末页",
            "sJump": "跳转"
        },
        "oAria": {
            "sSortAscending": ": 以升序排列此列",
            "sSortDescending": ": 以降序排列此列"
        }
    };
    //初始化表格
    table = $("#table").dataTable({
        language: lang,  //提示信息
        autoWidth: false,  //禁用自动调整列宽
        stripeClasses: ["odd", "even"],  //为奇偶行加上样式，兼容不支持CSS伪类的场合
        processing: true,  //隐藏加载提示,自行处理
        serverSide: true,  //启用服务器端分页
        searching: false,  //禁用原生搜索
        orderMulti: false,  //启用多列排序
        order: [],  //取消默认排序查询,否则复选框一列会出现小箭头
        renderer: "bootstrap",  //渲染样式：Bootstrap和jquery-ui
        pagingType: "simple_numbers",  //分页样式：simple,simple_numbers,full,full_numbers

        ajax: function (data, callback, settings) {
            //封装请求参数
            var param = {};
            param.pageSize = data.length;//页面显示记录条数，在页面显示每页显示多少项的时候
            param.pageIndex = (data.start / data.length) + 1;//当前页码
            //console.log(param);
            //ajax请求数据
            $.ajax({
                type: "get",
                url: "http://"+host+"/sys/admin/item/allItemByPage",
                cache: false,  //禁用缓存
                data: param,  //传入组装的参数
                headers: {"token": token},
                success: function (result) {
                    //封装返回数据
                    var returnData = {};
                    returnData.draw = data.draw;//这里直接自行返回了draw计数器,应该由后台返回
                    returnData.recordsTotal = result.recordsTotal;//返回数据全部记录
                    returnData.recordsFiltered = result.recordsFiltered;//后台不实现过滤功能，每次查询均视作全部结果
                    returnData.data = result.data;//返回的数据列表
                    //console.log(returnData);
                    //调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
                    //此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕
                    callback(returnData);
                }
            });
        },
        //列表表头字段
        columns: [
            {"data": "id"},
            {"data": "title"},
            {"data": "price"},
            {"data": "sales"},
            {
                "data": "imgUrl",
                "render": function (data, type, row, meta) {
                    return "<div align='center'><img width='50%' height='50%'  src='" + data + "' /></div>";
                }
            },
            {
                "data": "createTime",
                "render": function (data, type, row, meta) {
                    return timeStamp2Date(data);
                }
            },
            {"data": null}
        ],
        columnDefs: [
            {
                "targets": 1, //第2列，从0开始
                render: function (data, type, full, meta) {
                    if (data) {
                        if (data.length > 20) {
                            return data.substr(0, 14) + " <a href = 'javascript:void(0);' onclick = 'javascript:searchBtn3(\""+data+"\")' >...</a> ";
                        }else{
                            return data;
                        }
                    }else {
                        return "";
                    }
                }
            },
            {
                "targets": -1,//编辑-倒数第一列
                "data": "id",
                "orderable": false,
                "render": function (data, type, row, meta) {
                    return "<button  class='btn btn-warning' onclick='edit(" + data.id + ")' >编辑</button>" +
                        "<button class='btn btn-danger' onclick='del(" + data.id + ")'>删除</button>";
                }

            }
        ]
    }).api();//此处需调用api()方法,否则返回的是JQuery对象而不是DataTables的API对象
});


function searchBtn3(id) {
    alert(id);
}


$("#searchUser").click(function () {

    var username = "";
    username = $('input[name="searchText"]').val();
    if (username == "") {
        toastr.error("请输入用户名！");
    } else {

        $('#table').DataTable().destroy();

        //提示信息
        var lang = {
            "sProcessing": "处理中...",
            "sLengthMenu": "每页 _MENU_ 项",
            "sZeroRecords": "没有匹配结果",
            "sInfo": "当前显示第 _START_ 至 _END_ 项，共 _TOTAL_ 项。",
            "sInfoEmpty": "当前显示第 0 至 0 项，共 0 项",
            "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
            "sInfoPostFix": "",
            "sSearch": "搜索:",
            "sUrl": "",
            "sEmptyTable": "表中数据为空",
            "sLoadingRecords": "载入中...",
            "sInfoThousands": ",",
            "oPaginate": {
                "sFirst": "首页",
                "sPrevious": "上页",
                "sNext": "下页",
                "sLast": "末页",
                "sJump": "跳转"
            },
            "oAria": {
                "sSortAscending": ": 以升序排列此列",
                "sSortDescending": ": 以降序排列此列"
            }
        };

        table = $("#table").dataTable({
            language: lang,  //提示信息
            autoWidth: false,  //禁用自动调整列宽
            stripeClasses: ["odd", "even"],  //为奇偶行加上样式，兼容不支持CSS伪类的场合
            processing: true,  //隐藏加载提示,自行处理
            serverSide: true,  //启用服务器端分页
            searching: false,  //禁用原生搜索
            orderMulti: false,  //启用多列排序
            order: [],  //取消默认排序查询,否则复选框一列会出现小箭头
            renderer: "bootstrap",  //渲染样式：Bootstrap和jquery-ui
            pagingType: "simple_numbers",  //分页样式：simple,simple_numbers,full,full_numbers

            ajax: function (data, callback, settings) {
                //封装请求参数
                var param = {};
                param.pageSize = 10;//页面显示记录条数，在页面显示每页显示多少项的时候
                param.pageIndex = 1;//当前页码
                //console.log(param);
                //ajax请求数据
                $.ajax({
                    type: "GET",
                    url: "http://"+host+"/sys/admin/user/search/" + username,
                    cache: false,  //禁用缓存
                    data: param,  //传入组装的参数
                    headers: {"token": token},
                    success: function (result) {
                        //封装返回数据
                        var returnData = {};
                        returnData.draw = data.draw;//这里直接自行返回了draw计数器,应该由后台返回
                        returnData.recordsTotal = result.recordsTotal;//返回数据全部记录
                        returnData.recordsFiltered = result.recordsFiltered;//后台不实现过滤功能，每次查询均视作全部结果
                        returnData.data = result.data;//返回的数据列表
                        //console.log(returnData);
                        //调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
                        //此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕
                        callback(returnData);
                    },
                    error: function (data) {
                        toastr.error("" + data.message);
                    }
                });
            },
            //列表表头字段
            columns: [
                {"data": "id"},
                {"data": "username"},
                {"data": "phone"},
                {"data": "email"},
                {   // 字段通过id渲染
                    "data": "sex",
                    "render": function (data, type, row, meta) {
                        if (data == "1") {
                            return "男";
                        } else {
                            return "女";
                        }
                    }
                },
                {"data": "status"},
                {"data": null}
            ],
            columnDefs: [
                {
                    "targets": -1,//编辑
                    "data": "id",
                    "orderable": false,
                    "render": function (data, type, row, meta) {
                        return "<button  class='btn btn-warning' onclick='edit(" + data.id + ")' >编辑</button>" +
                            "<button class='btn btn-danger' onclick='del(" + data.id + ")'>删除</button>";
                    }

                }
            ]
        }).api();//此处需调用api()方法,否则返回的是JQuery对象而不是DataTables的API对象
    }

});

/**
 * 删除数据
 * @param name
 */
function del(id) {

    var r = confirm("您确认删除吗?");
    if (r == true) {
        $.ajax({
            url: "http://"+host+"/sys/admin/item/deleteItem/" + id,
            type: "DELETE",
            data: {"id": id},
            headers: {"token": token},
            success: function (data) {
                table.ajax.reload();
                toastr.info(data.message);
            },
            error: function (data) {
                toastr.error(data.message);
            }
        });
    }
}


$("#addItem").click(function () {

    $('.modal-backdrop').remove();  // 去掉灰色遮罩
    /*toastr.options = {
        closeButton: false,                                            // 是否显示关闭按钮，（提示框右上角关闭按钮）
        debug: false,                                                    // 是否使用deBug模式
        progressBar: true,                                            // 是否显示进度条，（设置关闭的超时时间进度条）
        positionClass: "toast-top-center",              // 设置提示款显示的位置
        onclick: null,                                                     // 点击消息框自定义事件 
        showDuration: "300",                                      // 显示动画的时间
        hideDuration: "1000",                                     //  消失的动画时间
        timeOut: "2000",                                             //  自动关闭超时时间 
        extendedTimeOut: "1000",                             //  加长展示时间
        showEasing: "swing",                                     //  显示时的动画缓冲方式
        hideEasing: "linear",                                       //   消失时的动画缓冲方式
        showMethod: "fadeIn",                                   //   显示时的动画方式
        hideMethod: "fadeOut"                                   //   消失时的动画方式
    };*/

    //toastr.info("添加中......");

    $.ajax({
        type: "post",
        url: "http://"+host+"/sys/admin/item/addItem",
        data: {
            "title": $('input[name="add-title"]').val(),
            "price": $('input[name="add-price"]').val(),
            "description": $('input[name="add-desc"]').val(),
            "imgUrl": $('input[name="add-imgUrl"]').val(),
            "status": $("#add-status").val(),
            "stock":$('input[name="add-stock"]').val()
        },
        headers: {"token": token},
        success: function (data) {
            $("#myModal").modal('hide');
            $('.modal-backdrop').remove();  // 去掉灰色遮罩
            toastr.info(data.message + "");
            table.ajax.reload();
        },
        error: function (data) {
            alert(data.message);
            toastr.error(data.message + "");
        }
    });
});

var itemId;
// 编辑查询数据回显
function edit(id) {
    $.ajax({
        type: "get",
        url: "http://"+host+"/sys/admin/item/getAdminItemById/" + id,
        headers: {"token": token},
        success: function (data) {
            if (data.code == "200") {
                itemId = data.object.id;
                $("#editModal").modal('show');
                $('input[name="edit-title"]').val(data.object.title);
                $('input[name="edit-price"]').val(data.object.price);
                $('input[name="edit-desc"]').val(data.object.description);
                $('input[name="edit-imgUrl"]').val(data.object.imgUrl);
                $('input[name="edit-stock"]').val(data.object.stock);
                $('.edit-status').val(data.object.status);

            }
        }
    })

}

// 提交编辑
$("#editItem").click(function () {

    if (itemId == null || itemId == ""){
        toastr.info("数据加载异常！");
        return;
    }
    /*var editForm = new FormData;
    editForm.append();
    editForm.append("title",$('input[name="title"]').val());
    editForm.append("coverUrl",$('input[name="coverUrl"]').val());
    editForm.append("likeSize",$('input[name="likeSize"]').val());
    editForm.append("unLikeSize",$('input[name="unLikeSize"]').val());*/
    $.ajax({
        type: "post",
        url: "http://"+host+"/sys/admin/item/updateItem/" + itemId,
        data: {
            "title": $('input[name="edit-title"]').val(),
            "price": $('input[name="edit-price"]').val(),
            "desc": $('input[name="edit-desc"]').val(),
            "imgUrl": $('input[name="edit-imgUrl"]').val(),
            "stock": $('input[name="edit-stock"]').val(),
            "status": $("#edit-status").val()
        },
        headers: {"token": token},
        success: function (data) {
            $("#editModal").modal('hide');
            $('.modal-backdrop').remove();  // 去掉灰色遮罩
            toastr.info(data.message);
            table.ajax.reload();
        },
        error: function (data) {
            toastr.info("修改失败" + data);
        }
    });

});


function timeStamp2Date(timestamp) {

    var datetime = new Date();
    datetime.setTime(timestamp);
    var year = datetime.getFullYear();
    var month = datetime.getMonth() + 1;
    var date = datetime.getDate();
    var hour = datetime.getHours();
    var minute = datetime.getMinutes();
    var second = datetime.getSeconds();
    var msecond = datetime.getMilliseconds();
    return year + "-" + month + "-" + date + " " + hour + ":" + minute + ":" + second;

}