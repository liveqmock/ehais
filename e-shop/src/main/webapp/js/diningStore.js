var jroll_menu_cate;
var jroll_menu_list;
var jroll_myOrder;
var cartArray = null;//购物车数据
var path = window.location.search;//页面跟的参数地址



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
	if(parseInt(badge) == 0)$(that).removeClass("active").parent().removeClass("active");
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
		if(parseInt($(this).parent().parent().parent().attr("cid")) > 0){
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
	$("#ot").html("￥"+(total / 100).toFixed(2));
	
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
	$(".menu").height($(window).height() - ($(".swiper-container").height() / 2) - $(".tab").height() - $("footer").height() );
	$("#menu_cate").height($(".menu").height());
	$("#menu_list").height($(".menu").height());
	$("#myOrderList").height($(".menu").height());

//	console.log($(".menu").height() +"+"+ $(window).height() +"+"+ $(".swiper-container").height() +"+"+ $(".tab").height() +"+"+ $("footer").height());
//	console.log("window height:"+$(window).outerHeight(true));
//	console.log("body height:"+$(document.body).outerHeight(true));
	
	wPos = $(document.body).outerHeight(true) - $(window).outerHeight(true);
//	console.log("wPos:"+wPos);
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
	
	var num = $("img").length;
	$("img").load(function() {
		num--;
		if (num > 0) {
			return;
		}
		jroll_menu_list.refresh(); 
		getScrollTop();
	}).error(function(){
		$(this).attr("src","http://ovug9f17p.bkt.clouddn.com/dining121.jpg");
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
	$(".icon-weixin").click(function(){
		$(".icon-xianjin").removeClass("active");
		$(this).addClass("active");
	});
	$(".icon-xianjin").click(function(){
		$(".icon-weixin").removeClass("active");
		$(this).addClass("active");
	});
	
	diningUserOrderList();//获取用户的订单列表
	
	$("#resultBtn").click(function(){$(".result").removeClass("active");});
	
});
var offsets = [];
var targets = [];
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
	
	$(".badge.active").each(function(){
		if(parseInt($(this).parent().parent().parent().attr("cid")) > 0){
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
}
function orderFail(){
	$(".cart_layer,.wco").removeClass("active");
	$(".result").addClass("active");
	$(".result i").attr("class","icon-shibai");
	$(".result div").html("下单失败");
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
						"<div>共"+quantity+"件菜品，"+v.payName+"￥"+(v.orderAmount / 100).toFixed(2)+"元</div>"+
					"</li>");
				
				item = null;
				quantity = null;
				
			});
			jroll_myOrderList.refresh();
		},beforeSend: function () {}
	});
}



