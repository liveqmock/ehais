var page = 1;
var rows = 10;
var jroll;
var loaded;
$(function(){
	ehaisOrder({page : page , rows : rows });//初始化
	setInterval(function(){
		var order_time = "";
		if($("#o li").length > 0) order_time = $("#o li:first").attr("t");
		ehaisOrder({page : page , rows : rows , orderSort : "New" , order_time : order_time })
		order_time = null;
		},30000);
	
	$("#c").height(parseFloat($(window).height()) - parseFloat($(".we").css('padding-top').replaceAll("px","")) - parseFloat($(".we").css('padding-bottom').replaceAll("px","")));
	
	loaded = "";
	jroll = new JRoll("#c", {scrollBarY:false});
	
	jroll.on("touchEnd", function() {
		
		if (this.y >= 44) {
//			if(loaded=="")loaded="pull";
		}else if (this.y < this.maxScrollY - 10){
			console.log("this.maxScrollY:"+this.y+"--------"+this.maxScrollY);
			if(loaded=="")loaded="up";
		}
	});
	
	jroll.on("scrollEnd", function() {
		if(loaded == "pull"){
			
		}else if(loaded == "up"){
			var order_time = "";
			if($("#o > li").length > 0) order_time = $("#o > li:last").attr("t");
			ehaisOrder({page : page , rows : rows , orderSort : "Old" , order_time : order_time })
			order_time = null;
		}
	});
	
	
	var num = $("img").length;
	$("img").load(function() {
		num--;
		if (num > 0) {
			return;
		}
		jroll.refresh(); 
	}).error(function(){
		$(this).attr("src","http://eg.ehais.com/images/eICON.png");
		jroll.refresh();
	});
	num = null;
	
	
});

function ehaisOrder(obj){
	loaded = "ing";
	console.log(JSON.stringify(obj));
	$.ajax({
		url : "ehaisOrder",
		data : obj ,
		success : function(result){
			loaded = "";
			var list = result.rows;
			$.each(list,function(k,v){
				if(obj.orderSort == "New"){
					$("#o").prepend(orderItem(v,result.map.listOrderGoods));
				}else{
					$("#o").append(orderItem(v,result.map.listOrderGoods));
				}
			});
			
			jroll.refresh(); 
		}
	});
}

function orderItem(v,g){
	
	var goodslist = "";
	var quantity = 0 ;
	var state = "";
	var shipping = "";
	$.each(g,function(i,d){
		if(v.orderId == d.orderId){
			goodslist += "<li>"+
				"<img src='"+d.goodsThumb+"' />"+d.goodsName+"<div>x "+d.goodsNumber+"</div>"+
			"</li>";
			quantity += parseInt(d.goodsNumber);
		}
	});
	
	if(v.shippingId != null && v.shippingId > 0){
		state = "已发货";
		shipping = "<div class='s'>"+v.shippingName+"<i>"+v.shippingNumber+"</i>"+"</div>";
	}else{
		state = "未发货";
	}
	
	return "<li t='"+v.addTime+"'>"+
	"<div class='h'>"+
	"订单："+v.orderSn+
	"</div>"+
	"<div class='d'>"+v.addTime+"  <b>"+v.payName+"</b></div>"+
	"<ul>"+goodslist+"</ul>"+shipping+
	"<div class='t'>"+
		"<i>"+state+"</i>共"+quantity+"件商品 实付款：￥"+ (v.orderAmount / 100).toFixed(2)+"元"+
	"</div>"+						
	"</li>";
	
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
