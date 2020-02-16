$.ajax({
    type: "get",
    url: "http://"+host+"/server/api/item/allItem",
    success: function (data) {
        if(data.code == "200"){
            var content = "";
            var item = "";
            $.each(data.object,function (index,item) {
            item = "<a href='/detail.html?itemId="+item.id+"&promoteId="+item.promoteId+"' class=\"jq22-flex b-line\">" +
                                        "<div class=\"jq22-flex-time-img\">" +
                                            "<img src='"+item.imgUrl+"' alt=\"\">" +
                                        "</div>" +
                                        "<div class=\"jq22-flex-box\">" +
                                            "<h2>"+item.title+"</h2>" +
                                            "<h3>￥"+item.price+"</h3>" +
                                            "<h4>专柜价:"+(parseInt(item.price)*1.2)+"</h4>" +
                                            "<div class=\"jq22-flex jq22-flex-clear-pa\">" +
                                                "<div class=\"jq22-flex-box\">" +
                                                    "<div class=\"jq22-flex-texts\">" +
                                                        "<p>"+item.stock+"</p>" +
                                                    "</div>" +
                                                    "<div class=\"jq22-time-seep jq22-flex-box\">" +
                                                        "<div class=\"jq22-time-seep-go\" style=\"width:88%\"></div>" +
                                                    "</div>" +
                                                "</div>" +
                                                "<div class=\"jq22-time-button\">" +
                                                    "<button>立即抢购</button>" +
                                                "</div>" +
                                            "</div>" +
                                        "</div>" +
                                    "</a>";
                content +=item;
            });
                $("#seckill-container").append(content);
        }else {

        }
    },
    error: function (data) {
        alert(data.message);
    }
});