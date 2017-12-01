var jroll_menu_cate;
var jroll_menu_list;
var jroll_myOrderList;
var jroll_couponsList;
var cartArray = null;//购物车数据
var path = window.location.search;//页面跟的参数地址
var offsets = [];
var targets = [];
var couponsId = 0;

//添加菜品入购物车
function addCartMenu(that){
	var badge = $(".badge_"+$(that).parent().parent().parent().attr("value")).html();
	if(badge==null || badge == "" )badge = 0;
	badge ++;
	$(".badge_"+$(that).parent().parent().parent().attr("value")).html(badge).addClass("active").parent().addClass("active");
	badge = null;
	//统计数量与金额，购物列表
	cartTotalAmount();
	
}

function minusCartMenu(that){
	var badge = $(".badge_"+$(that).parent().parent().parent().attr("value")).html();
	if(badge==null || badge == "" )badge = 0;
	badge --;
	$(".badge_"+$(that).parent().parent().parent().attr("value")).html(badge);
	if(parseInt(badge) == 0){
		$(".badge_"+$(that).parent().parent().parent().attr("value")).removeClass("active").parent().removeClass("active").children(".minusCart").removeClass("active");
	}
	badge = null;
	//统计数量与金额，购物列表
	cartTotalAmount();
}


//统计数量与金额，购物列表
function cartTotalAmount(){
	var quantity = 0;
	var total = 0;
	$(".badge.active").each(function(){
		if(parseInt($(this).parent().parent().parent().attr("cid")) > 0){
			var amount = parseFloat($(this).parent().parent().parent().attr("price")) * parseInt($(this).html()); 
			total += amount;
			quantity += parseInt($(this).html());
			amount = null;	
		}
	});

	$("#quantity").html(quantity);
	$("#total").html("￥"+(total / 100).toFixed(2));
	if(total > 0 || quantity > 0){
		$(".fd").addClass("active");
	}else{
		$(".fd").removeClass("active");
	}
	total = null ; quantity = null;
	
}

//购物车加法
function plusSquare(that){
	var q = parseInt($(that).parent().children(".q").html()) + 1;
	var p = parseInt($(that).parent().attr("p"));
	$(that).parent().children(".q").html(q);	
	$(that).parent().children(".p").html("￥"+(q*p/100).toFixed(2));		
	var id = $(that).parent().attr("id");	
	$(".badge_"+id).html(q);
	cartTotalAmount();
	q=p=id=null;
}
//购物车减法
function minusSquare(that){
	var q = parseInt($(that).parent().children(".q").html());
	var p = parseInt($(that).parent().attr("p"));
	var id = $(that).parent().attr("id");
	if(q > 1){
		q = q-1;
		$(that).parent().children(".q").html(q);
		$(that).parent().children(".p").html("￥"+(q*p/100).toFixed(2));	
		$(".badge_"+id).html(q);
	}else{
		$(".badge_"+id).html(0).removeClass("active").parent().removeClass("active");
		$(that).parent().remove();
	}
	cartTotalAmount();
	q=p=id=null;
}


//选好了，查看订单
function checkOutCart(){
	var total = 0,quantity = 0;
	$("#wcod dd").remove();
	
	$(".badge.active").each(function(){
		if(parseInt($(this).parent().parent().parent().attr("cid")) > 0 && parseInt($(this).html()) > 0){
			var amount = parseFloat($(this).parent().parent().parent().attr("price")) * parseInt($(this).html()); 
			total += amount;
			quantity += parseInt($(this).html());
			$("#wcod").append("<dd>"+
					"<div>"+$(this).parent().parent().parent().children(".img").html()+"</div>"+
					"<div class='t'>"+$(this).parent().parent().parent().children(".i").children(".t").html()+"</div>"+
					"<div class='p'>￥"+(amount / 100).toFixed(2)+"</div>"+
					"<div class='g'>份</div>"+
					"<div class='g'>x "+$(this).html()+"</div>"+
				"</dd>");
			
			amount = null;
			
		}
	});

	$("#oq").html("数量:"+quantity);
	$("#ot").html("￥"+(total / 100).toFixed(2)).attr("total",total).attr("wpayamount",total);
	
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
	if($(".swiper-wrapper li").length > 0){
		$(".swiper-container").height($(window).width() * 15 / 32);
	}else{
		$(".swiper-container").height(0);
	}
	
	$(".menu").height($(window).height() - ($(".swiper-container").height() / 2) - $(".tab").height() - $("footer").height() );
	$("#menu_cate").height($(".menu").height());
	$("#menu_list").height($(".menu").height());
	$("#myOrderList").height($(".menu").height());
	if($("#coupons").length>0)$("#couponsList").height($(".menu").height());
	

//	console.log($(".menu").height() +"+"+ $(window).height() +"+"+ $(".swiper-container").height() +"+"+ $(".tab").height() +"+"+ $("footer").height());
//	console.log("window height:"+$(window).outerHeight(true));
//	console.log("body height:"+$(document.body).outerHeight(true));
	
	wPos = $(document.body).outerHeight(true) - $(window).outerHeight(true);
	
//	console.log("wPos:"+wPos);
	jroll_menu_cate = new JRoll("#menu_cate", {scrollBarY:false});
	jroll_menu_list = new JRoll("#menu_list", {scrollBarY:false});
	jroll_myOrderList = new JRoll("#myOrderList", {scrollBarY:false});
	if($("#coupons").length>0)jroll_couponsList = new JRoll("#couponsList", {scrollBarY:false});
	
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
	jroll_menu_list.on("scroll", function() {
		for (i = offsets.length; i--;){
			if(-this.y > offsets[i]){
				$("#scroller_menu_cate li.active").removeClass("active");
				$(".c"+targets[i]).addClass("active");
				return false;
			}
		}
	});
	
	jroll_menu_list.on("touchEnd", function() {

	});
	
	$("#scroller_menu_cate > li").click(function(){
		$("#scroller_menu_cate li.active").removeClass("active");
		$(this).addClass("active");
		for (var i = 0 ; i < targets.length; i++){
			if($(this).attr("v") == targets[i]){
				jroll_menu_list.scrollTo(0, -offsets[i]);
				return false;
			}
		}
	});
	
	getScrollTop();
	
	var num = $("img").length;
	$("img").load(function() {
		num--;
		if (num > 0) {
			return;
		}
		
		jroll_menu_list.refresh(); 
		getScrollTop();
		
	}).error(function(){
		$(this).attr("src","http://q.ehais.com/dining121.jpg");
		jroll_menu_list.refresh();
		getScrollTop();
	});
	num = null;
	
	
	
	$("#scroller_menu_goods_list .addCart").click(function(){addCartMenu(this);});
	$("#scroller_menu_goods_list .minusCart").click(function(){minusCartMenu(this);});
	
	
	//购物车点击事件
	$(".sCart").click(function(){
		
		if(!$("footer.fd").hasClass("active"))return ;
		if($(".cart_layer").hasClass("active")){
			$(".cart_layer").removeClass("active");
		}else{
			$(".cart_layer").addClass("active");
			
			$(".cart_layer dl dd").remove();
			
			$(".badge.active").each(function(index){
				if(parseInt($(this).parent().parent().parent().attr("cid")) > 0){
					$(".cart_layer dl").append("<dd id='"+$(this).parent().parent().parent().attr("value")+"' p='"+$(this).parent().parent().parent().attr("price")+"'>"+
							"<div>"+ $(this).parent().parent().parent().children(".i").children(".t").html().substr(0,10)+"</div>"+
							"<i class='iconfont icon-jia-xianxingfangkuang'></i>"+
							"<div class='q'>"+$(this).html()+"</div>"+
							"<i class='iconfont icon-jian-xianxingfangkuang'></i>"+
							"<div class='p'>￥"+((parseFloat($(this).parent().parent().parent().attr("price")) * parseInt($(this).html()) * parseInt($(this).html())) / 100).toFixed(2) +"</div>"+
						"</dd>");
				}
			});
			
			$(".cart_layer .icon-jia-xianxingfangkuang").click(function(){plusSquare(this)});
			$(".cart_layer .icon-jian-xianxingfangkuang").click(function(){minusSquare(this)});
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
	

	
	$(".cart_layer .bg , .close_cart").click(function(){$(".cart_layer").removeClass("active");});
	
	///////////////选好了
	$("#checkOut").click(function(){
		if($("footer.fd").hasClass("active")){
			$(".wco").addClass("active");
			checkOutCart();
			
			//验证优惠券
			checkCoupons();
		}
	});
	$(".icon-xiangzuojiantou").click(function(){$(".wco").removeClass("active");});
	
	//继续点餐
	$(".goOrderFood").click(function(){
		$("#localCheckOut,.cart_layer").removeClass("active");
		$(".sCart,#checkOut").show();
		$(".orderCart,.orderSubmit,#orderTotal,#orderQuantity").hide();
		
	});
	
	//确认下单
	$("#orderSubmit").click(function(){
		if($(".icon-weixin").hasClass("active") && $(".icon-weixin").attr("disabled")){
			elay.toast({content:"商家未开通微信在线支付"});return ;
		}
		if($(".icon-xianjin").hasClass("active") && $(".icon-xianjin").attr("disabled")){
			elay.toast({content:"商家只支持微信在线支付"});return ;
		}
			
		var layerIndex = elay.confirm({
		    content: '订单'+$("#ot").html()+'确认用'+($(".icon-weixin").hasClass("active")?"微信支付":"现金支付")+'吗'
		    ,btn: ['确认' , '取消']
		    ,sure: function(){		    	
				if($(".icon-weixin").hasClass("active")){
					diningSubmitOrder("weixin");
				}else if($(".icon-xianjin").hasClass("active")){
					diningSubmitOrder("cash");
				}
		    }
		 });
	});
	
	//点餐选项
	$("#orderFood").click(function(){
		if(!$("#orderFood").hasClass("active")){
			$("ul.tab li").removeClass("active");
			$(this).addClass("active");
			$(".menu , footer").show();
			$(".myOrder,.coupons").hide();
		}		
	});
	//我的订单
	$("#myOrder").click(function(){
		if(!$("#myOrder").hasClass("active")){
			$("ul.tab li").removeClass("active");
			$(this).addClass("active");
			$(".menu , footer,.coupons").hide();
			$(".myOrder").show();
			jroll_myOrderList.refresh();
		}
	});
	//优惠券
	$("#coupons").click(function(){
		if($("#coupons").length>0 && !$("#coupons").hasClass("active")){
			$("ul.tab li").removeClass("active");
			$(this).addClass("active");
			$(".menu , footer,.myOrder").hide();
			$(".coupons").show();
			coupons();
		}
	});
	
	//支付方式选择
	$(".icon-weixin").click(function(){
		if($(this).attr("disabled")){
			elay.toast({content:"商家未开通微信在线支付"});return ;
		}
		$(".icon-xianjin").removeClass("active");
		$(this).addClass("active");
	});
	$(".icon-xianjin").click(function(){
		if($(this).attr("disabled")){
			elay.toast({content:"商家只支持微信在线支付"});return ;
		}
		$(".icon-weixin").removeClass("active");
		$(this).addClass("active");
	});
	
	diningUserOrderList();//获取用户的订单列表
	
	$("#resultBtn").click(function(){$(".result").removeClass("active");history.back();});
	
	//点击图片放大
	$("#scroller_menu_goods_list >li >div.img img").click(function(){
		if($("#"+$(this).attr("h")).length>0){
			$("#gd,#"+$(this).attr("h")).addClass("active");
			history.pushState({page:"detail"}, "goods_detail", "dining_goods");
		}else{
			var that = this;
			$.ajax({
				url : "dining_goods!"+$(this).attr("h"),
				success:function(result){
					
					var gli = "";
					if(result.map!=null && result.map.gallery!=null && result.map.gallery.length > 0){
						$.each(result.map.gallery,function(k,v){
							gli+="<li class='swiper-slide'><img src='"+v.imgOriginal+"' /></li>";
						});
					}
					
					$("#gd").append("<div id='"+$(that).attr("h")+"' class='g_d active'>"+

									"<div class='scroll swiper-container'>"+
									"<ul class='swiper-wrapper'>"+
									"<li class='swiper-slide'><img src='"+result.model.originalImg+"' /></li>"+
									gli+
									"</ul>"+
									"<div class='swiper-pagination'></div>"+
									"</div>"+
									(
											result.model.goodsDesc.length > 0 ?(
									"<div class='i'><i class='iconfont icon-xiangqing'></i> 菜品介绍</div>"+
									"<div class='d'>"+result.model.goodsDesc+"</div>"):""
									)
									+
									(
											result.model.goodsBrief.length > 0 ? (
									"<div class='i'><i class='iconfont icon-xiangqing'></i> 菜品故事</div>"+
									"<div class='d'>"+result.model.goodsBrief+"</div>") : ""
									)+
									"</div>");
					
					$("#gd,#"+$(that).attr("h")).addClass("active");
					if(gli.length > 0){
						 new Swiper ('#'+$(that).attr("h")+' .swiper-container', {
							    direction: 'horizontal',
							    loop: true,
							    autoplay: 5000,
							    pagination: '.swiper-pagination',
							});
					}
					gli = null;
					history.pushState({page:"detail"}, "goods_detail", "dining_goods");
				}
			});
		}
	});

	
	
	if(typeof(payModule) == "object" &&  Object.prototype.toString.call(payModule).toLowerCase() == "[object object]" && !payModule.length){
    	if($.isArray(payModule["weixin"])){
    		if(payModule["weixin"][0] == "invisible"){//不可见
    			$(".icon-weixin").hide();
    			$(".icon-xianjin").addClass("active");
    		}
    		if(payModule["weixin"][1] == "disabled"){//不可使用
    			$(".icon-weixin").attr("disabled",true).removeClass("active");
    			$(".icon-xianjin").addClass("active");
    		}
    	}
    	if($.isArray(payModule["cash"])){
    		if(payModule["cash"][0] == "invisible"){//不可见
    			$(".icon-xianjin").hide();
    		}
    		if(payModule["cash"][1] == "disabled"){//不可使用
    			$(".icon-xianjin").attr("disabled",true);
    		}
    	}
    }

	if($("#coupons").length>0)coupons();
	
});


window.onpopState = function(){
    var json = window.history.state;//获取当前所在的state
    console.log(JSON.stringify(json));
    $("#gd , #gd .g_d,.result").removeClass("active");
}


window.addEventListener('popstate', function(e){
		if (history.state){
			var state = e.state;
		    //do something(state.url, state.title);
		}
		$("#gd , #gd .g_d , .result").removeClass("active");
		console.log(JSON.stringify(history));
	}, false);



function getScrollTop(){
	offsets = [];
	targets = [];
	var h = 0;
	$("#scroller_menu_goods_list li").each(function(){
		offsets.push(h);
		targets.push($(this).attr("cid"));
		h = parseFloat(h) + parseFloat($(this)[0].scrollHeight);
	});
	
	
}



/**
 * 现场点餐
 * @param tpay，0暂不支付，1立即支付
 */
function diningSubmitOrder(tPay){
	var cartArray = new Object();
	var wTotal = $("#ot").attr("total");
	var wPayAmount = $("#ot").attr("wpayamount");
	
	$(".badge.active").each(function(){
		if(parseInt($(this).parent().parent().parent().attr("cid")) > 0 && parseInt($(this).html()) > 0 ){
			cartArray[""+$(this).parent().parent().parent().attr("value")+""] = {
					"goods_id":$(this).parent().parent().parent().attr("value"),
					"price" : $(this).parent().parent().parent().attr("price"),
					"badge" : $(this).html(),
				}
		}
	});
	
	$.ajax({
		url : "/diningSubmitOrder",
		data : {
			sid:sid,
			couponsId:couponsId,
			wTotal:wTotal,
			wPayAmount:wPayAmount,
			tPay:tPay,
			cart:JSON.stringify(cartArray),
			message:$("#postscript").val()
		},
		type:"post",
		dataType:"json",
		success:function(result){
			elay.toast({content: result.msg,time: 3 });
			if(result.code != 1)return ;
			$("#localCheckOut").removeClass("active");
			clearCart();//下单成功，清空购物车
			$("#postscript").val("");
			if(tPay == "weixin"){
				
				WeiXinWCPay = result.map.WeiXinWCPay;
				WeiXinWCPay["package"] = WeiXinWCPay["pack_age"] 
				//支付调起
				onBridgeReadyCall(WeiXinWCPay,function(){
					orderSuccess();
					diningUserOrderList();
				},function(){
					orderFail();
					diningUserOrderList();
				});
			}else{
				orderSuccess();
				diningUserOrderList();
			}
		}
	});
}

function orderSuccess(){
	$(".cart_layer,.wco").removeClass("active");
	$(".result").addClass("active");
	$(".result i").attr("class","iconfont icon-chenggong");
	$(".result div").html("下单成功");
	
	history.pushState({page:"orderSuccess"}, "orderSuccess", "orderSuccess");
	
}
function orderFail(){
	$(".cart_layer,.wco").removeClass("active");
	$(".result").addClass("active");
	$(".result i").attr("class","icon-shibai");
	$(".result div").html("下单失败");
	
	history.pushState({page:"orderFail"}, "orderFail", "orderFail");
	
}

//清空购物车事件
function clearCart(){
	
	//清空购物车数组
    $("#cart_dl dd").remove();
	$(".cart_layer").removeClass("active");
	$(".badge").removeClass("active").html(0).parent().removeClass("active");
	$("#quantity").html(0);
	$("#total").html("￥0.00");
	$("footer").removeClass("active");
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
				var goods_desc = v.goodsDesc;
				var item = "";
				var quantity = 0;
				if(goods_desc != null && $.trim(goods_desc) != ""){
					goods_desc = goods_desc.replaceAll(" ","");
					var gd = $.trim(goods_desc).split("】");
					$.each(gd,function(k,v){
						if(v == null || v == "")return false;
						item += "<li>"+
							"<span>"+v.substring(0,v.indexOf("【"))+"</span>"+
							"<span>x "+v.substring(v.indexOf("【")+1,v.length)+"</span>"+
						"</li>";
						
						quantity += parseInt(v.substring(v.indexOf("【")+1,v.length));
					});
					
				}
				
				$("#myOrderUl").append("<li>"+
						"<div>"+
							"<img src='css/images/cart.png'>"+
							"<span>"+v.consignee+"</span>"+
							"<i class='iconfont icon-xiangyoujiantou'></i>"+
							"<span>订单已完成</span>"+
						"</div>"+
						"<ul>"+item+"</ul>"+
						"<div>共"+quantity+"件菜品，"+(v.discount > 0 ? ("优惠￥"+(v.discount / 100).toFixed(2) ) +"元，" : "" )+v.payName+"￥"+(v.orderAmount / 100).toFixed(2)+"元</div>"+
					"</li>");
				
				item = null;
				quantity = null;
				
			});
			jroll_myOrderList.refresh();
		},beforeSend: function () {}
	});
}

function coupons(){
	if($("#couponsUl li").length > 0)return ;
	$.ajax({
		url : "coupons!"+sid,
		success:function(result){
			if(!isBlank(result.rows)){

				$.each(result.rows,function(k,v){
					$("#couponsUl").append("<li value='"+v.couponsId+"' couponsName='"+v.couponsName+"' quota='"+v.quota+"' couponsType='"+v.couponsType+"' discounts='"+v.discounts+"' couponsQuantity='"+v.couponsQuantity+"' startDate='"+v.startDate+"' endDate='"+v.endDate+"' >"+
							"<div class='t'>"+(v.couponsType == "reduce"?"￥":"折")+"<b>"+v.discounts+"</b></div>"+
							"<div class='c'>"+
								"<b>"+v.couponsName+"</b>"+
								"满"+v.quota+"元使用"+(v.couponsQuantity > 0 ? "(限"+v.couponsQuantity+"名)" : "")+
							"</div>"+
							"<div class='g cg"+v.couponsId+"'>领取</div>"+
							"<div class='l'>"+(
								(!isBlank(v.startDate) && !isBlank(v.endDate)) ? 
									(v.startDate.substr(0,10) +"~"+ v.endDate.substr(0,10)) : (
											(!isBlank(v.startDate) && isBlank(v.endDate))? (v.startDate.substr(0,10)+"开始"):(
													(isBlank(v.startDate) && !isBlank(v.endDate))? (v.endDate.substr(0,10)+"结束"):""
											)
									)
								) +
							"</div>"+
						"</li>");
				});
			}
			
			
			
			if(!isBlank(result.map) && !isBlank(result.map.user_coupons)){
				$.each(result.map.user_coupons,function(k,v){
					$(".cg"+v.couponsId).addClass("h").html("已领取");
				});
			}
			
			
			$("#couponsUl >li >.g").click(function(){
				if($(this).hasClass("h"))return;
				receive_coupons($(this).parent().attr("value"));
			});
		}
	});
}

function receive_coupons(_couponsId){
	$.ajax({
		url : "receive_coupons!"+sid,data:{couponsId:_couponsId},
		success:function(result){
			elay.toast({content:result.msg});
			if(result.code == 1){
				$(".cg"+_couponsId).addClass("h").html("已领取");
			}
		}
	});
}

//验证优惠券
function checkCoupons(){
	couponsId = 0;
	if($("#couponsUl").length == 0)return ;
	var _amount = 0;
	var _couponsId = 0;
	var usercoupons = "";
	$("#coup_amount").html("");
	$("#couponsUl li").each(function(i,e){
		if(parseInt($("#ot").attr("total")) >= parseInt($(e).attr("quota")) * 100){
			if($(e).attr("couponstype") == "reduce"){//减
				if(parseInt($(e).attr("discounts")) * 100 > _amount ){
					_amount = parseInt($(e).attr("discounts")) * 100;
					_couponsId = $(e).attr("value");
					usercoupons = $(e).attr("couponsName")+"满"+$(e).attr("quota")+"减"+$(e).attr("discounts");
				}
			}else if($(e).attr("couponstype") == "rebate"){//折扣
				if((parseInt($("#ot").attr("total")) * (100 - parseInt($(e).attr("discounts"))) / 100 ) > _amount ){
					_amount = parseInt($("#ot").attr("total")) * (100 - parseInt($(e).attr("discounts"))) / 100;
					_couponsId = $(e).attr("value");
					usercoupons = $(e).attr("couponsName")+"满"+$(e).attr("quota")+"打折"+$(e).attr("discounts");
				}
			}
		}
	});
	
	if(parseInt(_couponsId) > 0){
		couponsId = _couponsId;
		var amount = parseInt($("#ot").attr("total")) - parseInt(_amount);
		$("#choose_coupons").attr("couponsId",_couponsId).attr("amount",amount.toFixed(0));
		$("#usercoupons").html(usercoupons);
		$("#ot").html("￥"+ (amount / 100 ).toFixed(2)).attr("wpayamount",amount.toFixed(0));
		$("#coup_amount").html("<div>￥"+($("#ot").attr("total") / 100).toFixed(2)+"</div><div>优惠￥"+(_amount / 100).toFixed(2)+"</div>");
		amount = null;
		$("#choose_coupons").removeClass("dn");
	}else{
		$("#choose_coupons").addClass("dn");
	}
	
	
}
