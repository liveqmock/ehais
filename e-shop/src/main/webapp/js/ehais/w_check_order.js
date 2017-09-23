var recIds = null;
var chooseAddressId = null;
var WeiXinWCPay = null;
$(function(){
	$("header .fa-chevron-left").click(function(){window.history.go(-1);});
	recIds = sessionStorage.getItem("recIds");//缓存中的选中购物车的商品信息
	chooseAddressId = sessionStorage.getItem("chooseAddressId");//地址选择页面选择地配送地址对象信息
	
	$("#coupon").click(function(){
		elay.open({content:"暂无优惠券",btn:"朕知道了"});
	});
	
	w_check_order_data();//加载购物车信息
	
	//添加新地址
	$("#new_address").click(function(){
		new_address();
	});
	
	$("#orderSubmit").click(function(){
		orderSubmit();
	});
	
	//重新支付
	$("#btnRepay").click(function(){
		if(WeiXinWCPay!=null)onBridgeReady(WeiXinWCPay);
	});
});

//返回强制刷新的代码
window.onpageshow = function(event){
	if (event.persisted) {
		w_check_order_data();//加载购物车信息
	}
}

/**
 * 提交信息
 */
function orderSubmit(){
	
	//判断是否存在收货人地址
	var addressId = $("#address").attr("value");
	var message = $("#message").val();
	if(addressId == null || addressId.length == 0 ){
		elay.open({content:"请选择或添加收货人信息",btn:"朕马上处理"});
		return ;
	}
	
	$.ajax({
		url : "/ws/wxdone",type:"post",dataType:"json",
		data : {sid:sid,pay_id:1,ship_id:1,address_id:addressId,message:message,recIds:recIds},
		success : function(result){
			if(result.code != 1){
				elay.open({content:result.msg,btn:"朕知道了"});
				return ;
			}
			
			WeiXinWCPay = result.map.WeiXinWCPay;
			WeiXinWCPay["package"] = WeiXinWCPay["pack_age"] 
			//支付调起
			onBridgeReady(WeiXinWCPay);
			
			
		},error : function(err,xhr){
			
		}
	})
}

/**
 * 配送地址数据设置
 */
function shippingAddress(region,address){
	$("#address").removeClass("dn");
	var regionStr = "";
	for(var i in region){
		regionStr+=region[i].regionName;
	}
	
	$("#address").html(
			"<i class='iconfont icon-shouhuodizhi'></i>"+
			"<div class='info'>"+
				"<div class='username'>"+
					"收货人：<span class='consignee'>"+address.consignee+"</span>"+
					"<span class='mobile'>"+address.mobile+"</span>"+
				"</div>"+
				"<div class='address'>"+regionStr+address.address+"</div>"+
			"</div>"+
			"<i class='iconfont icon-xiangyoujiantou'></i>")
		.attr("value",address.addressId);
	
	regionStr = null;
	
	$("#address_edit").removeClass("dn").click(function(){
		window.location.href = "w_address_list";
	});
	$("#address").click(function(){
		window.location.href = "w_address_list";
	});
}
//请求最新的订单信息
function w_check_order_data(){
	$.ajax({
		url : "/ws/checkout" ,type:"post",dataType:"json",
		data : {recIds : recIds},
		success : function(result){
			if(result.code != 1){
				elay.open({content:result.msg + recIds, btn:"朕知道了"});
				return ;
			}
			//alert("加载中.."+chooseAddressId);
			if(chooseAddressId != null){//重新选择的地址
				useraddress_info(chooseAddressId);
				localStorage.removeItem("chooseAddressId");
			}else{//初始化进入的地址
				if(result.map == null || result.map.address == null){//显示添加收货人信息
					$("#new_address").removeClass("dn");
				}else{//////默认收货人信息					
					var address = result.map.address;
					var region = result.map.region;
					shippingAddress(region,address);
					region = null;
					address = null;
				}				
			}
			
			
			
			if(result.map == null || result.map.cart == null){
				//购物车为空，返回上一层
				//elay.confirm({content:"购物车已清空",btn:["朕知道了",""],sure:function(index){elay.closeAll();window.history.go(-1);}});
				window.history.go(-1);
				return ;
			}
			
			var cart = result.map.cart;
			var total = 0;
			for(var i in cart){
				$("#cart").append("<li class='item'>"+
						"<div class='pic'><img src='"+cart[i].goodsThumb+"' /></div>"+
						"<div class='t'>"+cart[i].goodsName+"</div>"+
						"<div class='intro'>"+
							"<div class='price'>￥"+(cart[i].goodsPrice / 100 ).toFixed(2)+"</div>"+
							"<div class='quantity'>数量  x "+cart[i].goodsNumber+"</div>"+
						"</div>"+
					"</li>");
				total+=parseInt(cart[i].goodsPrice);
			}
			
			$("#total").html("￥"+(total/100).toFixed(2));
			
		},error : function(err,xhr){
			
		}
	})
}

function new_address(){
	window.location.href = "w_address_add";
}

function useraddress_info(addressId){
	$.ajax({
		url : "/ws/useraddress_info",type:"post",dataType:"json",
		data : {addressId:addressId},
		success : function(result){
			var address = result.model;
			var region = result.map.region;
			shippingAddress(region,address);
			region = null;
			address = null;
		},error : function(err,xhr){
			
		}
	})
}
