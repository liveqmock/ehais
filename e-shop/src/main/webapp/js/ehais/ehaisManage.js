var page = 1;
var rows = 10;
$(function(){
	diningOrder();
});

function diningOrder(){
	$.ajax({
		url : "/ehaisOrder",
		data : {page : page , rows : rows },
		success : function(result){
			
			var list = result.rows;
			$.each(list,function(k,v){
				$("#order_in_store").append("<li>"+
					"<div>订单号：<span>"+v.orderSn+"</span></div>"+
					"<div>台号：<span>"+v.zipcode+"</span></div>"+
					"<div>下单时间：<span>"+v.addTime+"</span></div>"+					
					"<div class=\"goods_desc\">"+v.goodsDesc+"</div>"+
					"<div>支付方式：<span>"+ (v.orderAmount / 100).toFixed(2)+"【"+v.payName+"】</span></div>"+
				"</li>");
			});
			
			
		}
	});
}

Date.prototype.format = function(format) {
    var date = {
           "M+": this.getMonth() + 1,
           "d+": this.getDate(),
           "h+": this.getHours(),
           "m+": this.getMinutes(),
           "s+": this.getSeconds(),
           "q+": Math.floor((this.getMonth() + 3) / 3),
           "S+": this.getMilliseconds()
    };
    if (/(y+)/i.test(format)) {
           format = format.replace(RegExp.$1, (this.getFullYear() + '').substr(4 - RegExp.$1.length));
    }
    for (var k in date) {
           if (new RegExp("(" + k + ")").test(format)) {
                  format = format.replace(RegExp.$1, RegExp.$1.length == 1
                         ? date[k] : ("00" + date[k]).substr(("" + date[k]).length));
           }
    }
    return format;
}
