var dining = GetQueryString("dining");
if(dining == null || dining == "")dining = "tyler";
localStorage.setItem("dining",dining);

//通过二维码扫描当前台号
var tableno = GetQueryString("tableno");



var jroll_menu_cate ;
var jroll_menu_list ;
var cartArray = null;//购物车数据
var path = window.location.search;//页面跟的参数地址

//菜谱分类与热销商品数据加载
function category(){
	$.ajax({
		type:"post",
		url:"/api.php/Api/DiningApi/category_dining",
		data:{dining : dining,path : path},
		dataType:"json",
		async:true,
		success:function(result){
			var cateList = result.cateList;
			var goodsList = result.goodsList;
			//设置热销推荐数据
			localStorage.setItem("local_goods_list_0",JSON.stringify(goodsList));

			$("#scroller_menu_cate li:not(:first-child)").remove();
			for(var i = 0 ; i < cateList.length; i++){
				$("#scroller_menu_cate").append("<li value='"+cateList[i].id+"'>"+cateList[i].name+"</li>");
				localStorage.setItem("local_goods_list_"+cateList[i].id,null);
			}
			//刷新菜谱滑动
			jroll_menu_cate.refresh(); 
			//菜谱点击事件
			$("#scroller_menu_cate li").click(function(){menu_cate_click(this);});
			//菜品商品展示
			menu_goods_list(goodsList);
			
			var suppliersdata = result.suppliersdata;
			$("title").html(result.title);
			
			wx_config(result.wxConfig);
			
		}
	});
}

//加载菜品商品数据
function goods_dining(catid){
	$.ajax({
		type:"post",
		url:"/api.php/Api/DiningApi/goods_dining",
		data:{catid:catid , dining : dining},
		dataType:"json",
		async:true,
		success:function(result){
			var rows = result.rows;
			if(rows != null && rows != "" && rows != "null" && rows.length > 0)
			localStorage.setItem("local_goods_list_"+catid,JSON.stringify(rows));
			menu_goods_list(rows);
		}
	});
}


//菜谱选择事件
function menu_cate_click(that){
	if(!$(that).hasClass("active")){
		$("#scroller_menu_cate li.active").removeClass();
		$(that).addClass("active");
		var goods_list_data = localStorage.getItem("local_goods_list_"+$(that).val());
		
		if(goods_list_data!=null && goods_list_data != "" && goods_list_data != "null"){
			menu_goods_list(JSON.parse(goods_list_data));
			goods_list_data = null;
		}else{
			goods_dining($(that).val());
		}
	}
}

//菜品商品数据展示
function menu_goods_list(goodsListJson){
	$("#scroller_menu_goods_list li").remove();
	
	if(localStorage.getItem("localCartArray") == null || localStorage.getItem("localCartArray") == "" || localStorage.getItem("localCartArray") == "null"){
		cartArray = new Array();
	}else{
		cartArray = JSON.parse(localStorage.getItem("localCartArray"));
	}
	
	for(var i = 0 ; i < goodsListJson.length ; i++){
		$("#scroller_menu_goods_list").append("<li>"+
							"<div class=\"pic\">"+
							"	<img src=\""+goodsListJson[i].original_img+"\" />"+
							"</div>"+
							"<div class=\"goodsname\">"+goodsListJson[i].goods_name.substr(0,20)+"</div>"+
							"<div class=\"price\">￥"+goodsListJson[i].shop_price+"</div>"+
							"<div value=\""+goodsListJson[i].goods_id+"\" price=\""+goodsListJson[i].shop_price+"\" class=\"addCart glyphicon glyphicon-plus-sign\"></div>"+
							"<div id=\"badge_"+goodsListJson[i].goods_id+"\" class=\"badge\"></div>"+
						"</li>");
		if(cartArray[goodsListJson[i].goods_id] != null){
			$("#badge_"+goodsListJson[i].goods_id).html(cartArray[goodsListJson[i].goods_id].badge);
			$("#badge_"+goodsListJson[i].goods_id).addClass("active");
		}
	}
	cartArray = null;
	
	var num = $("img").length;
	$("img").load(function() {
		num--;
		if (num > 0) {
			return;
		}
		jroll_menu_list.refresh(); 
	}).error(function(){
		$(this).attr("src","upload/dining/122.jpg");
		jroll_menu_list.refresh();
	});
	num = null;
	
	$("#scroller_menu_goods_list .addCart").unbind();
	$("#scroller_menu_goods_list .addCart").click(function(){addCartMenu(this);});
	
	//统计数量与金额，购物列表
	cartTotalAmount();
	
}

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
		"price" : $(that).parent().children(".price").html().replace("￥",""),
		"badge" : $(that).parent().children(".badge").html(),
	}
	
	//将购物车数据存储在购物车缓存中
	localStorage.setItem("localCartArray",JSON.stringify(cartArray));
	
	cartArray = null;
	
	//统计数量与金额，购物列表
	cartTotalAmount();
	
}

//统计数量与金额，购物列表
function cartTotalAmount(){
	if(localStorage.getItem("localCartArray") == null || localStorage.getItem("localCartArray") == "" || localStorage.getItem("localCartArray") == "null"){
		cartArray = new Array();
	}else{
		cartArray = JSON.parse(localStorage.getItem("localCartArray"));
	}
	
	var total = 0,quantity = 0;
	$("#cart_dl dd .fa-plus-square").unbind();
	$("#cart_dl dd .fa-minus-square").unbind();
	$("#cart_dl dd").remove();
	$.each(cartArray,function(index,ele){
		
		var amount = parseFloat(ele.price) * parseInt(ele.badge); 
		total += amount;
		quantity += parseInt(ele.badge);
		
		$("#cart_dl").append("<dd id=\"cart_dd_"+index+"\" value=\""+index+"\">"+
				"<div class=\"fl\">"+ele.goodsname.substr(0,10)+"</div>"+
				"<div class=\"fr fa fa-plus-square\"></div>"+
				"<div class=\"fr quantity\">"+ele.badge+"</div>"+
				"<div class=\"fr fa fa-minus-square\"></div>"+
				"<div class=\"fr price\">￥"+amount+"</div>"+
			"</dd>");
		amount = null;		
	});
	
	$("#cart_dl dd .fa-plus-square").click(function(){plusSquare(this);});
	$("#cart_dl dd .fa-minus-square").click(function(){minusSquare(this);});
	
	$("#quantity").html(quantity);
	$("#total").html("￥"+total);
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
	$("#total").html("￥"+total);
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
		var layerIndex = layer.open({
		    content: '确定要删除此菜吗？'
		    ,btn: ['确定' , '取消']
		    ,skin: 'footer'
		    ,yes: function(index){  
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
				layer.close(layerIndex);				
				layerIndex = null;
				
		    }
		 });
	}
}



$(function(){
	//扫描二维码的默认台号
	if(tableno != null && tableno != "")$("#tableno").val(tableno);
	
	$(".swiper-container").width($(window).width());
	$(".swiper-container").height($(window).width() * 15 / 32);
	
	var mySwiper = new Swiper ('.swiper-container', {
	    direction: 'horizontal',
	    loop: true,
	    autoplay: 5000,
	    pagination: '.swiper-pagination',
	});  
				
				
	$("#menu_cate").height($(window).height() - $(".swiper-container").height() / 2 - $(".tabs").height() - $("footer").height() - 20);
	$("#menu_list").height($(window).height() - $(".swiper-container").height() / 2 - $(".tabs").height() - $("footer").height() - 20);

	
	jroll_menu_cate = new JRoll("#menu_cate", {scrollBarY:false});
	jroll_menu_list = new JRoll("#menu_list", {scrollBarY:false});
					

	//系统初始化事件
	category();
	
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
		
		var layerIndex = layer.open({
		    content: '确定要清空点餐清单吗？'
		    ,btn: ['清空' , '取消']
		    ,skin: 'footer'
		    ,yes: function(index){  
		    	clearCart();//清空购物车
		    	
				layer.close(layerIndex);
				
				layerIndex = null;
				
		    }
		 });		
	});//end 清空购物车
	
	$("#cart_layer .bg").click(function(){$("#cart_layer").removeClass("active");});
	$(".tabs > ul > li").click(function(){
		$(".tabs > ul > li").removeClass("active");
		$(this).addClass("active");
		if($(this).attr("value") == 1){
			$("#checkOut").html("我要下单");
		}else{
			$("#checkOut").html("外卖结算");
		}
	});
	
	$("#checkOut").click(function(){
		if($("footer.footer_dining").hasClass("active")){
			if($(".tabs > ul > li.active").attr("value") == 1){
				//直接支付
				$("#localCheckOut").addClass("active");
			}else{
				//获取订单信息与收货人信息
				window.location.href = "diningOrder.html";
			}
			
		}
	});
	
	//现场点餐暂不提交订单
	$("#cancelLocalSOrder,#localCheckOut .layui-m-layershade").click(function(){
		$("#localCheckOut").removeClass("active");
	});
	//现场点餐并支付
	$("#submitOrderPay").click(function(){localSubmitOrder(1)});
	//现场点餐暂不支付
	$("#submitOrderOnly").click(function(){localSubmitOrder(0)});
});

/**
 * 现场点餐
 * @param tpay，0暂不支付，1立即支付
 */
function localSubmitOrder(tPay){
	if($.trim($("#tableno").val()) == ""){
		layer.open({content: '客官，您在哪张餐台',skin: 'msg',time: 3 });
		$("#tableno").focus(); 
		return ;
	}
	
	$.ajax({
		url : "/api.php/Api/DiningApi/localSubmitOrder",
		data : {
			tableno:$.trim($("#tableno").val()),
			tPay:tPay,
			cart:localStorage.getItem("localCartArray"),
			dining:localStorage.getItem("dining"),
		},
		type:"post",
		dataType:"json",
		success:function(result){
			layer.open({content: result.msg,skin: 'msg',time: 3 });
			if(result.code != 1)return ;
			$("#localCheckOut").removeClass("active");
			clearCart();//下单成功，清空购物车
			if(tPay == 1){
				onBridgeReady(result.payment);
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
	
}



