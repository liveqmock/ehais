var orderData = {rows:[],map:{order_goods_list:[]}};
var page = 1;
var jroll;
var status;
var loaded;
$(function(){
	comment_wait_list();
	
	$("#jorder").height(parseFloat($(window).height()) - parseFloat($(".wm").css('padding-top').replaceAll("px","")));
	
	loaded = "";
	jroll = new JRoll("#jorder", {scrollBarY:false});
	
	jroll.on("touchEnd", function() {
		
		if (this.y >= 44) {
//			if(loaded=="")loaded="pull";
		}else if (this.y < this.maxScrollY - 10){
			if(loaded=="")loaded="up";
		}
	});
	
	jroll.on("scrollEnd", function() {
		if(loaded == "pull"){
			page = 1;
			comment_wait_list();
		}else if(loaded == "up"){
			comment_wait_list();
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
		$(this).attr("src","http://ovug9f17p.bkt.clouddn.com/dining121.jpg");
		jroll.refresh();
	});
	num = null;
	
});

function comment_wait_list(){
	loaded = "ing";
	$.ajax({
		url : "/ws/comment_wait_list",type:"post",dataType:"json",
		data:{
			page:page,
			rows:10
			},
		success : function(result){
			loaded = "";
			if(result.rows!=null && result.rows.length > 0){
				page = parseInt(page) + 1;
			}
			if(orderData == null){
				orderData = result;
			}else{
				for(var k in result.rows ){
					orderData.rows.push(result.rows[k]);
				}
				if(result.map != null && result.map.order_goods_list != null && result.map.order_goods_list.length > 0){
					for(var k in result.map.order_goods_list ){
						orderData.map.order_goods_list.push(result.map.order_goods_list[k]);
					}
				}
			}
			
			driverOrderInfo(result);
			
			if(orderData.rows==null || orderData.rows.length == 0)$(".e").addClass("active");
			
		}
	});
}

function driverOrderInfo(result){
	
	if(result.rows != null && result.rows.length > 0){
		$(".e").removeClass("active");
		$.each(result.rows,function(k,v){
			var goodslist = "";
			var quantity = 0 ;
			$.each(result.map.order_goods_list,function(i,d){
				if(v.orderId == d.orderId){
					goodslist += "<li>"+
						"<img src='"+d.goodsThumb+"' />"+d.goodsName+"<div>x "+d.goodsNumber+"</div>"+
					"</li>";
					quantity += parseInt(d.goodsNumber);
				}
			});
			
			var state = "";
			var btn = "";
			if(v.orderStatus == 0){
				state = "待付款";
				btn = "<button class='o'>付款</button>";
			}else if(v.orderStatus == 1){
				state = "待收货";
			}else if(v.orderStatus == 2){
				state = "取消";
			}else if(v.orderStatus == 3){
				state = "无效";
			}else if(v.orderStatus == 4){
				state = "退换货";
			}else if(v.orderStatus == 5){
				state = "完成";
			}
			$("#order").append("<li id='"+v.orderId+"'>"+
					"<div class='h'>"+
						"订单："+v.orderSn+
						"<b>"+state+"</b>"+
					"</div>"+
					"<ul>"+goodslist+"</ul>"+
					"<div class='b'>"+
						"<button class='o'>评论</button>"+
					"</div>"+
				"</li>");
			
			goodslist = null;
			quantity = null;
			state = null;
		});
		jroll.refresh();
		
		$(".o").unbind();
		$(".o").click(function(){
			window.location.href = "w_comment_write!"+$(this).parent().parent().attr("id");
		});
	}
}




