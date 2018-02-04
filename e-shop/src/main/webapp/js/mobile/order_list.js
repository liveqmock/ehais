/**
 * 
 */

var order_page = 1;
var len = 10;
$(function(){
	$(".view header").prepend("<div id='goback' class='backButton back goBackClick'>返回</div>");
	$(".goBackClick").click(function(){
		window.history.go(-1);
	});
	
	order_list(0);
});

function order_list(parent_id){
	$.ajax({
		url : "/ws/orderinfo_list",
		data : { page : order_page , len : len},
		type : "post",
		dataType : "json",
		success : function(data){
			if(data.code != 1){
				$.afui.toast(data.msg);
				return ;
			}
			
			var list = (typeof(data.rows) == undefined ) ? new Array() : data.rows;
			
			for (var i = 0; i < list.length; i++) {
				$("#order_ul").append('<li id="orderid_'+list[i].orderId+'" data-order_id="'+list[i].orderId+'" class="list" >'+

				'<div class="order_info flw">'+
				'	<div>订单编号：'+list[i].orderSn+'</div>'+
				'</div>'+
				
				'<div class="order_status flw">'+
				'	<div>'+
				'	订单状态：'+
				'	<span class="ml5">'+os[list[i].orderStatus]+'</span>'+
				'	<span class="ml5">'+ps[list[i].payStatus]+'</span>'+
				'	<span class="ml5">'+ss[list[i].shippingStatus]+'</span>'+
				'	</div>'+
				'</div>'+
				
				'<div class="order_goods_list flw">'+
				'	<ul class="ul_goods">'+
				'	</ul>'+
					
				'</div>'+
				
				
				'</li>'
			
				);
				
			}
			
			
			if(typeof(data.map.order_goods_list) != undefined){
				var goods_list = data.map.order_goods_list;
				if(goods_list == null)goods_list=[];
				for(var i = 0 ; i < goods_list.length; i++){
					$("#orderid_"+goods_list[i].orderId).children(".order_goods_list").children(".ul_goods").append(
							'<li>'+
							"<div class='img'>"+	
							"<a class='img' href='javascript:;'>"+
							"<img class='' src='"+list[i].goodsThumb+"' />"+
							"</a>"+
							"</div>"+
							'<div class="goods_info">'+
							'		<div class="title">'+goods_list[i].goodsName+'</div>'+
							'		<div class="">'+
							'			<div class="goods_price fl mr5">￥'+goods_list[i].goodsPrice+'</div>'+
							'			<div class="fl mr5">X</div>'+
							'			<div class="fl goods_number mr5">'+goods_list[i].goodsNumber+'件</div>'+
							'			<div class="fl mr5">=</div>'+
							'			<div class="fl subtotal">￥'+(parseFloat(goods_list[i].goodsPrice) * parseInt(goods_list[i].goodsNumber)).toFixed(2)+'</div>'+
							'		</div>'+
							'</div>'+
							
							'</li>'
					);
				}
			}
			
			
			
		},
		error : function(e){}
	});
}

