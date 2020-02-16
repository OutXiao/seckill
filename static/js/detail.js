var token = sessionStorage.getItem('token');
/*获取get请求中的值*/
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]); return null;
}

var itemId = getQueryString("itemId");
var promoteId;
$.ajax({
   type:"get",
   url: "http://"+host+"/server/api/item/getItemById?id="+itemId,
   success: function (data) {
       var content ;
       content = "<img src='"+data.object.imgUrl+"' width=\"100%\"/>\n" +
                "<p class=\"tit\">" +data.object.title+
           "<div class=\"qita\">\n" +
           "<p class=\"p1\">"+data.object.price+"<span>新品促销</span></p>\n" +
           "<p class=\"p2\">全国包邮   |   销量"+data.object.sales+"</p>\n" +
           "</div>";
       $("#imgAndtitleAndPriceAndSales").append(content);
       promoteId = data.object.promoteId;
   },
    error: function () {
        alert("数据加载异常");
    }
});




// 下单
$("#createOrder").click(function () {
    if (token == null || token === "") {
        alert("请您登录");
        location.href = 'login.html';
    } else {
        $.ajax({
            type: "post",
            url: "http://"+host+"/server/api/order/createOrder",
            headers: {"token": token},
            data: {
                "itemId": itemId,
                "amount": $("#amount").val(),
                "promoteId": promoteId
            },
            success: function (data) {
                alert(data.message + "");
            },
            error: function (data) {
                alert(data.message + "");
            }

        })
    }
});

