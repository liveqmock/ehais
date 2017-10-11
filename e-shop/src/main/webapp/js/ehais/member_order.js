var orderData = {};
var pageData = {all:1,pw:1,rw:1,rd:1};
var jroll;
var status;
var loaded;
$(function(){
	status = window.location.hash.replace("#","");
	$("dl dd").removeClass("active");
	$("dl dd").each(function(i,ele){if($(ele).attr("for") == status)$(ele).addClass("active");});
	orderinfo_list();
	
	$("dl dd").click(function(){
		status = $(this).attr("for");
		if($(this).attr("for") != location.hash){
			$("dl dd").removeClass("active");
			$("#order li").remove();
			jroll.scrollTo(0, 0, 0);
			jroll.refresh();
			$(this).addClass("active");
			$("#order_sn").val("");
			if(orderData[$(this).attr("for")] == null){
				orderinfo_list($(this).attr("for"));
			}else{
				driverOrderInfo(orderData[$(this).attr("for")]);
			}
		}
		
//		history.pushState({tfor:$(this).attr("for")},$(this).attr("for"),"#"+$(this).attr("for"));
	});
	
	$("#sosn").click(function(){$("#order li").remove(); pageData[status] = 1;orderinfo_list();});
	

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
			pageData[status] = 1;
			orderinfo_list();
		}else if(loaded == "up"){
			orderinfo_list();
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

function orderinfo_list(){
	loaded = "ing";
	var order_status = null;
	var pay_status = null;
	var shipping_status = null;
	if(status == "pw"){order_status = 0 ; pay_status = 0;}
	if(status == "rw"){order_status = 1 ; pay_status = 1;}
	if(status == "rd"){order_status = 4 ; pay_status = 4;}
	$.ajax({
		url : "/ws/orderinfo_list",type:"post",dataType:"json",
		data:{
			order_status:order_status,
			pay_status:pay_status,
			shipping_status:shipping_status,
			page:pageData[status],
			order_sn:$("#order_sn").val(),
			rows:10
			},
		success : function(result){
			loaded = "";
			if(result.rows!=null && result.rows.length > 0){
				pageData[status] = parseInt(pageData[status]) + 1;
			}
			if(orderData[status] == null){
				orderData[status] = result;
			}else{
				for(var k in result.rows ){
					orderData[status].rows.push(result.rows[k]);
				}
				if(result.map != null && result.map.order_goods_list != null && result.map.order_goods_list.length > 0){
					for(var k in result.map.order_goods_list ){
						orderData[status].map.order_goods_list.push(result.map.order_goods_list[k]);
					}
				}
			}
			
			driverOrderInfo(result);
			
			if(orderData[status].rows==null || orderData[status].rows.length == 0)$(".e").addClass("active");
			
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
			$("#order").append("<li>"+
					"<div class='h'>"+
						"订单："+v.orderSn+
						"<b>"+state+"</b>"+
					"</div>"+
					"<ul>"+goodslist+"</ul>"+
					"<div class='t'>"+
						"共"+quantity+"件商品 实付款：￥"+(v.orderAmount / 100 ).toFixed(2)+"元"+
					"</div>"+
//					"<div class='b'>"+
//						"<button class='o'>详情</button>"+
//						btn+
//						"<button class='d'>删除订单</button>"+
//					"</div>"+
				"</li>");
			
			goodslist = null;
			quantity = null;
			state = null;
		});
		jroll.refresh();
	}
}




