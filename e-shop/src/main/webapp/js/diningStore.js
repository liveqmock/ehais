var jroll_menu_cate;
var jroll_menu_list;
var jroll_myOrder;
var cartArray = null;//购物车数据
var path = window.location.search;//页面跟的参数地址



//添加菜品入购物车
function addCartMenu(that){
	var badge = $("#badge_"+$(that).attr("value")).html();
	if(badge==null || badge == "" )badge = 0;
	badge ++;
	$("#badge_"+$(that).attr("value")).html(badge).addClass("active");
	badge = null;
	
	if(localStorage.getItem("localCartArray") == null || localStorage.getItem("localCartArray") == "" || localStorage.getItem("localCartArray") == "null"){
		cartArray = new Object();
	}else{
		cartArray = JSON.parse(localStorage.getItem("localCartArray"));
	}
	
	cartArray[""+$(that).attr("value")+""] = {
		"goods_id":$(that).attr("value"),
		"goodsname":$(that).parent().children(".goodsname").html(),
		"price" : $(that).attr("price"),
		"badge" : $(that).parent().children(".badge").html(),
	}
	
	//将购物车数据存储在购物车缓存中
	localStorage.setItem("localCartArray",JSON.stringify(cartArray));
	
	cartArray = null;
	
	//统计数量与金额，购物列表
	cartTotalAmount();
	
}


//初始化商品缓存中的选中
function initGoodsBadge(){
	if(localStorage.getItem("localCartArray") == null || localStorage.getItem("localCartArray") == "" || localStorage.getItem("localCartArray") == "null"){
		cartArray = new Array();
	}else{
		cartArray = JSON.parse(localStorage.getItem("localCartArray"));
	}
	
	$.each(cartArray,function(k,v){
		$("#badge_"+k).html(cartArray[k].badge);
		$("#badge_"+k).addClass("active");
	})
}

//统计数量与金额，购物列表
function cartTotalAmount(){
	if(localStorage.getItem("localCartArray") == null || localStorage.getItem("localCartArray") == "" || localStorage.getItem("localCartArray") == "null"){
		cartArray = new Array();
	}else{
		cartArray = JSON.parse(localStorage.getItem("localCartArray"));
	}
	
	var total = 0,quantity = 0;
	$("#cart_list .dd .fa-plus-square").unbind();
	$("#cart_list .dd .fa-minus-square").unbind();
	$("#cart_list .dd").remove();
	$.each(cartArray,function(index,ele){
		
		var amount = parseFloat(ele.price) * parseInt(ele.badge); 
		total += amount;
		quantity += parseInt(ele.badge);
		
		$("#cart_list").append("<div id=\"cart_dd_"+index+"\" class=\"dd\" value=\""+index+"\">"+
				"<div class=\"fl\">"+ele.goodsname.substr(0,10)+"</div>"+
				"<div class=\"fr fa fa-plus-square\"></div>"+
				"<div class=\"fr quantity\">"+ele.badge+"</div>"+
				"<div class=\"fr fa fa-minus-square\"></div>"+
				"<div class=\"fr price\">￥"+amount+"</div>"+
			"</div>");
		amount = null;		
	});
	
	$("#cart_list .dd .fa-plus-square").click(function(){plusSquare(this);});
	$("#cart_list .dd .fa-minus-square").click(function(){minusSquare(this);});
	
	$("#quantity").html(quantity);
	$("#total").html("￥"+(total / 100).toFixed(2));
	if(total > 0 || quantity > 0){
		$(".footer_dining").addClass("active");
	}else{
		$(".footer_dining").removeClass("active");
	}
	total = null ; quantity = null;
	cartArray = null;
}

//在清单界面加减时计算总金额
function totalAmount(_cartArray){
	var total = 0;
	var amount = 0;
	var quantity = 0;
	$.each(_cartArray,function(index,ele){
		quantity += parseInt(ele.badge);
		amount = parseFloat(ele.price) * parseInt(ele.badge); 
		total += amount;		
	});
	$("#total").html("￥"+(total / 100).toFixed(2));
	$("#quantity").html(quantity);
	amount = null;
	total = null;
}

//购物车加法
function plusSquare(that){
	var quantity = parseInt($(that).parent().children(".quantity").html());
	$(that).parent().children(".quantity").html(quantity+1);	
	cartArray = JSON.parse(localStorage.getItem("localCartArray"));
	var obj = cartArray[$(that).parent().attr("value")];
	obj.badge = quantity + 1;
	$(that).parent().children(".price").html("￥"+(obj.badge * obj.price));
	$("#badge_"+$(that).parent().attr("value")).html(obj.badge);	
	cartArray[$(that).parent().attr("value")] = obj;
	totalAmount(cartArray);//计算显示的总金额
	localStorage.setItem("localCartArray",JSON.stringify(cartArray));
	cartArray = null;obj = null;quantity = null;
}
//购物车减法
function minusSquare(that){
	var quantity = parseInt($(that).parent().children(".quantity").html());
	if(quantity > 1){
		$(that).parent().children(".quantity").html(quantity-1);
		cartArray = JSON.parse(localStorage.getItem("localCartArray"));
		var obj = cartArray[$(that).parent().attr("value")];
		obj.badge = quantity - 1;
		$(that).parent().children(".price").html("￥"+(obj.badge * obj.price));
		$("#badge_"+$(that).parent().attr("value")).html(obj.badge);
		cartArray[$(that).parent().attr("value")] = obj;
		totalAmount(cartArray);//计算显示的总金额
		localStorage.setItem("localCartArray",JSON.stringify(cartArray));
		cartArray = null;obj = null;quantity = null;
	}else{
		elay.confirm({
		    content: '确定要删除此菜吗？'
		    ,btn: ['确定' , '取消']
		    ,sure: function(){  
		    	//清空购物车数组
		    	cartArray = JSON.parse(localStorage.getItem("localCartArray"));
				
				delete cartArray[$(that).parent().attr("value")];
				var count = 0 ;
				$.each(cartArray, function() {
					count ++;
				});
				if(count > 0){
					localStorage.setItem("localCartArray",JSON.stringify(cartArray));
					$("#cart_dd_"+$(that).parent().attr("value")).remove();
					$("#badge_"+$(that).parent().attr("value")).html("").removeClass("active");
				
				}else{
					clearCart();//清空购物车所有
				}
				totalAmount(cartArray);//计算显示的总金额
				count = null;
				cartArray = null;
		    }
		 });
	}
}


//选好了，查看订单
function checkOutCart(){
	if(localStorage.getItem("localCartArray") == null || localStorage.getItem("localCartArray") == "" || localStorage.getItem("localCartArray") == "null"){
		cartArray = new Array();
	}else{
		cartArray = JSON.parse(localStorage.getItem("localCartArray"));
	}
	
	var total = 0,quantity = 0;
	$("#check_out_list >div.dd").remove();
	$.each(cartArray,function(index,ele){
		
		var amount = parseFloat(ele.price) * parseInt(ele.badge); 
		total += amount;
		quantity += parseInt(ele.badge);
		
		$("#check_out_list").append("<div class=\"dd\" id=\"cart_dd_"+index+"\" value=\""+index+"\">"+
				"<div class=\"fl\">"+ele.goodsname.substr(0,10)+"</div>"+
				"<div class=\"fr quantity\">* "+ele.badge+"</div>"+
				"<div class=\"fr price\">￥"+(amount / 100 ).toFixed(2)+"</div>"+
			"</div>");
		amount = null;		
	});

	$("#orderQuantity").html("数量:"+quantity);
	$("#orderTotal").html("合计:￥"+(total / 100).toFixed(2));
	
	total = null ; quantity = null;
	cartArray = null;
}

//////////////##########################%%%%%%%%%%%%%%%%%%%%%%%%%%
var pos;
var wPos;
$(function(){
	var mySwiper = new Swiper ('.swiper-container', {
	    direction: 'horizontal',
	    loop: true,
	    autoplay: 5000,
	    pagination: '.swiper-pagination',
	});  
				
	$(".swiper-container").width($(window).width());
	$(".swiper-container").height($(window).width() * 15 / 32);
	
	
	$("#menu_cate").height($(window).height() - $(".swiper-container").height() / 2 - $(".tabs").height() - $("footer").height() - 20);
	$("#menu_list").height($(window).height() - $(".swiper-container").height() / 2 - $(".tabs").height() - $("footer").height() - 20);
	$("#myOrderList").height($(window).height() - $(".swiper-container").height() / 2 - $(".tabs").height() - $("footer").height() - 40 );

	console.log("window height:"+$(window).outerHeight(true));
	console.log("body height:"+$(document.body).outerHeight(true));
	
	wPos = $(document.body).outerHeight(true) - $(window).outerHeight(true);
	
	jroll_menu_cate = new JRoll("#menu_cate", {scrollBarY:false});
	jroll_menu_list = new JRoll("#menu_list", {scrollBarY:false});
	jroll_myOrderList = new JRoll("#myOrderList", {scrollBarY:false});
	
	jroll_menu_cate.on("scrollEnd", function() {
	    if(-this.y > wPos && $(window).scrollTop() < wPos ){
	    	$('body,html').animate({scrollTop: wPos}, 800);
	    }
	});
	jroll_menu_list.on("scrollEnd", function() {
	    if(-this.y > wPos && $(window).scrollTop() < wPos ){
	    	$('body,html').animate({scrollTop: wPos}, 800);
	    }
	});
	
	jroll_menu_list.on("touchEnd", function() {
	    if(this.y < this.maxScrollY - 40){
	    	changeNext($("#scroller_menu_cate li.active").index());
	    }else if(this.y > 40){
	    	changePrevious($("#scroller_menu_cate li.active").index());
	    }
	});
	
	$(".cat0").removeClass("dn");//推荐、热门菜品
	$("#scroller_menu_cate > li").click(function(){$("#scroller_menu_goods_list > li").addClass("dn");$("#scroller_menu_cate > li").removeClass("active");$(this).addClass("active");$("#scroller_menu_goods_list > li.cat"+$(this).attr("v")).removeClass("dn");jroll_menu_list.refresh();});
	
	var num = $("img").length;
	$("img").load(function() {
		num--;
		if (num > 0) {
			return;
		}
		jroll_menu_list.refresh(); 
	}).error(function(){
		$(this).attr("src","http://ovug9f17p.bkt.clouddn.com/dining121.jpg");
		jroll_menu_list.refresh();
	});
	num = null;
	
	
	
	$("#scroller_menu_goods_list .addCart").click(function(){addCartMenu(this);});
	
	//初始化选择数量
	initGoodsBadge();
	//统计数量与金额，购物列表
	cartTotalAmount();
	
	//购物车点击事件
	$("footer.footer_dining .cart").click(function(){
		if(!$("footer.footer_dining").hasClass("active"))return ;
		if($("#cart_layer").hasClass("active")){
			$("#cart_layer").removeClass("active");
		}else{
			$("#cart_layer").addClass("active");
		}
	});
	//清空购物车事件
	$(".clear_cart").click(function(){		  
		elay.confirm({
		    content: '确定要清空点餐清单吗？'
		    ,btn: ['清空' , '取消']
		    ,sure: function(){  
		    	clearCart();//清空购物车			
		    }
		 });		
	});//end 清空购物车
	

	
	$("#cart_layer .bg , .close_cart").click(function(){$("#cart_layer").removeClass("active");});
	
	///////////////选好了
	$("#checkOut").click(function(){
		if($("footer.footer_dining").hasClass("active")){
			$("#localCheckOut").addClass("active");
			$("#shoppingCart,#checkOut").hide();
			$(".orderCart,.orderSubmit,#orderTotal,#orderQuantity").show();
			
			checkOutCart();
		}
	});
	
	//继续点餐
	$(".goOrderFood").click(function(){
		$("#localCheckOut,#cart_layer").removeClass("active");
		$("#shoppingCart,#checkOut").show();
		$(".orderCart,.orderSubmit,#orderTotal,#orderQuantity").hide();
		
	});
	
	//确认下单
	$("#orderSubmit").click(function(){
		var layerIndex = elay.confirm({
		    content: '陛下，订单'+$("#orderTotal").html()+'确认提交吗'
		    ,btn: ['确认' , '取消']
		    ,sure: function(){		    	
				if($("#check_out_pay >.weixin").hasClass("active")){
					diningSubmitOrder("weixin");
				}else if($("#check_out_pay >.cash").hasClass("active")){
					diningSubmitOrder("cash");
				}
		    }
		 });
	});
	
	//现场点餐暂不提交订单
	$("#cancelLocalSOrder,#localCheckOut .layui-m-layershade").click(function(){
		$("#localCheckOut").removeClass("active");
	});
	//现场点餐并支付
	$("#submitOrderPay").click(function(){diningSubmitOrder("weixin")});
	//现场点餐暂不支付
	$("#submitOrderOnly").click(function(){diningSubmitOrder("cash")});
	//点餐选项
	$("#orderFood").click(function(){
		if(!$("#orderFood").hasClass("active")){
			$(this).addClass("active");
			$("#myOrder").removeClass("active");
			$(".menu , footer").show();
			$(".myOrder").hide();
		}
		
	});
	//我的订单
	$("#myOrder").click(function(){
		if(!$("#myOrder").hasClass("active")){
			$(this).addClass("active");
			$("#orderFood").removeClass("active");
			$(".menu , footer").hide();
			$(".myOrder").show();
			jroll_myOrderList.refresh();
		}
	});
	
	//支付方式选择
	$("#check_out_pay >div").click(function(){
		$("#check_out_pay >div").removeClass("active");
		$(this).addClass("active");
	});
	
	diningUserOrderList();//获取用户的订单列表
	
});

function changePrevious(index){
	if(index > 0){
		if($("#scroller_menu_goods_list li.cat"+$("#scroller_menu_cate li").eq(index - 1).attr("v")).length > 0){
			$("#scroller_menu_cate li").eq(index - 1).click();
		}else{
			changePrevious(index-1);
		}
	}
}
//下拉显示下一个菜单
function changeNext(index){
	if(index < $("#scroller_menu_cate li").length - 1){			
		if($("#scroller_menu_goods_list li.cat"+$("#scroller_menu_cate li").eq(index + 1).attr("v")).length > 0){
			$("#scroller_menu_cate li").eq(index + 1).click();
		}else{
			changeNext(index+1);
		}
	}
}

/**
 * 现场点餐
 * @param tpay，0暂不支付，1立即支付
 */
function diningSubmitOrder(tPay){
	
	$.ajax({
		url : "/diningSubmitOrder",
		data : {
			sid:sid,
			tPay:tPay,
			cart:localStorage.getItem("localCartArray")
		},
		type:"post",
		dataType:"json",
		success:function(result){
			elay.toast({content: result.msg,time: 3 });
			if(result.code != 1)return ;
			$("#localCheckOut").removeClass("active");
			clearCart();//下单成功，清空购物车
			if(tPay == "weixin"){
				
				WeiXinWCPay = result.map.WeiXinWCPay;
				WeiXinWCPay["package"] = WeiXinWCPay["pack_age"] 
				//支付调起
				onBridgeReadyCall(WeiXinWCPay,function(){
					diningUserOrderList();
				},function(){
					diningUserOrderList();
				});
			}else{
				diningUserOrderList();
			}
		}
	});
}

//清空购物车事件
function clearCart(){
	
	//清空购物车数组
	localStorage.setItem("localCartArray",null);
    $("#cart_dl dd").remove();
	$("#cart_layer").removeClass("active");
	$(".badge").removeClass("active").html("");
	$("#quantity").html("");
	$("#total").html("");
	$("footer.footer_dining").removeClass("active");	
	$("#shoppingCart,#checkOut").show();
	$("#orderCart,#orderSubmit").hide();
}

var page = 1;
var rows = 10;
function diningUserOrderList(){
	$.ajax({
		url : "diningUserOrderList",data : {page:page,rows:rows},
		success:function(result){
			var rows = result.rows;
			$("#myOrderUl li").remove();
			$.each(rows,function(k,v){
				$("#myOrderUl").append("<li>"+
						"<div>消费餐厅："+v.consignee+"</div>"+
						"<div>消费时间："+v.addTime+"</div>"+
						"<div>消费金额："+(v.orderAmount / 100).toFixed(2)+"元</div>"+
					"</li>");
			});
			jroll_myOrderList.refresh();
		},beforeSend: function () {}
	});
}


