var orderData = {};
var pageData = {all:1,paywaiting:1,payed:1,shiped:1,returned:1};
var jroll;
var status;
var loaded;
$(function(){
	status = $("dl dd:first").attr("for");
	orderinfo_list();
	
	$("dl dd").click(function(){
		status = $(this).attr("for");
		if($(this).attr("for") != location.hash){
			$("dl dd").removeClass("active");
			$(this).addClass("active");			
			if(orderData[$(this).attr("for")] == null){
				orderinfo_list($(this).attr("for"));
			}else{
				driverOrderInfo(orderData[$(this).attr("for")]);
			}
		}
		
//		history.pushState({tfor:$(this).attr("for")},$(this).attr("for"),"#"+$(this).attr("for"));
	});
	
	

	$("#jorder").height(parseFloat($(window).height()) - parseFloat($(".wm").css('padding-top').replaceAll("px","")));
	
	loaded = "";
	jroll = new JRoll("#jorder", {scrollBarY:false});
	
	jroll.on("touchEnd", function() {
		
		if (this.y >= 44) {
			console.log("下拉刷新中");
			if(loaded=="")loaded="pull";
		}else if (this.y < this.maxScrollY - 10){
			console.log("上拉刷新中");
			if(loaded=="")loaded="up";
		}
	});
	
	jroll.on("scrollEnd", function() {
		console.log(loaded);
		if(loaded == "pull"){
			pageData[status] = 1;
			orderinfo_list();
		}else if(loaded == "up"){
			orderinfo_list();
		}
	});
	
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
			rows:3
			},
		success : function(result){
			loaded = "";
			
			if(orderData[status] == null){
				orderData[status] = result;
				driverOrderInfo(result);
			}else{
				var oresult = orderData[status];
				
				console.log(result.rows.length +"---"+ orderData[status].rows.length);
				
				for(var k in result.rows ){
					console.log("result.rows[k].orderSn:"+result.rows[k].orderSn);
					orderData[status].rows.push(result.rows[k]);
				}
				
				
				if(result.map != null && result.map.order_goods_list != null && result.map.order_goods_list.length > 0){
					for(var k in result.map.order_goods_list ){
						orderData[status].map.order_goods_list.push(result.map.order_goods_list[k]);
					}
				}
				
				driverOrderInfo(orderData[status]);
				
			}
		}
	});
}

function driverOrderInfo(result){
	$("#order li").remove();
	if(result.rows != null && result.rows.length > 0){
		$(".e").removeClass("active");
		$.each(result.rows,function(v){
			$("#order").append("<li>"+
					"<div class='h'>"+
						"订单：1028349734859348"+
						"<b>已完成</b>"+
					"</div>"+
					"<ul>"+
						"<li>"+
							"<img src='images/s3.jpg' />"+
							"白色职业装连衣裤衫配吊坠、红色+黑色、GR-1848"+
						"</li>"+
						"<li>"+
							"<img src='images/s3.jpg' />"+
							"白色职业装连衣裤衫配吊坠、红色+黑色、GR-1848"+
						"</li>"+
					"</ul>"+
					"<div class='t'>"+
						"共2件商品 实付款：￥234.08元"+
					"</div>"+
					"<div class='b'>"+
						"<button class='o'>详情</button>"+
						"<button class='o'>付款</button>"+
						"<button class='d'>删除订单</button>"+
					"</div>"+
				"</li>");
		});
		
		jroll.refresh();
		
	}else{
		$(".e").addClass("active");
	}
}

//window.addEventListener("hashchange", function(){
//	 var hashStr = location.hash.replace("#","");
//	 console.log("hashchange:"+hashStr);
//	 alert("hashchange:"+hashStr);
//}, true);
//
//window.addEventListener("popstate", function() {
//    var currentState = history.state;
//    console.log("currentState:"+JSON.stringify(currentState));
//    alert(("currentState:"+JSON.stringify(currentState)));
//});



