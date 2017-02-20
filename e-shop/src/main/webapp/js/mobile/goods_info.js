/**
 * 
 */
var winH = 0;
var pageH = 0;
var scrollT = 0;
var ulH = 0;
var goodsinfo_top ;

$(function(){
	$(".view header").prepend("<div class='backButton back goBackClick'>返回</div>");
	$(".goBackClick").click(function(){window.history.go(-1);});
	
	goods_info();
	
	$("#addCart").click(function(){addCart();});	
	$("#addCartSubmit").click(function(){addCartSubmit();});	
	$("#close_popup_layer").click(function(){close_popup_layer();});	
	$("#collect").click(function(){favorites_add();});	
	$("#cartStatus").click(function(){window.location.href = "cart_list";});
	$(".subtract").unbind();$(".plus").unbind();
	$(".subtract").click(function(){cart_quantity(this,"subtract")});
	$(".plus").click(function(){cart_quantity(this,"plus")});
	
	
	$(".goodsinfo").click(function(){
		var ic = $(this).children("div").children("span").children("i");
		if(ic.hasClass("fa-chevron-right")){
			ic.addClass("fa-chevron-down");
			ic.removeClass("fa-chevron-right");
			$("#app_desc").removeClass("dn");
		}else{
			ic.addClass("fa-chevron-right");
			ic.removeClass("fa-chevron-down");
			$("#app_desc").addClass("dn");
		}
		
		
	});
	
	scrollT = $(window).scrollTop(); //滚动条top 
	ulH = $("#main").height();
	
//	console.log("页面高度1："+winH+" / "+ pageH + " / "+scrollT + " / "+ulH);

	
	$("#main").scroll(function (){
		pageH = $(this).height();
		scrollT = $(this).scrollTop();
//		console.log("页面高度2："+winH+" / "+ pageH + " / "+scrollT + " / "+ulH);
		
		//$("#consoleId").html(""+winH+" / "+ pageH + " / "+scrollT + " / "+ulH);
	});
	
	
});

function goods_info(){
	$.ajax({
		url : "/ws/app_goods_info",
		data : {store_id : store_id , goodsId : goods_id },
		type : "post",
		dataType : "json",
		success : function(data){
			
			if(data.code != 1){
				$.ejq.toast(data.msg);
				return ;
			}
			var model = data.model;
			
			$("#goods_name").html(model.goodsName);
			if(typeof(model.goodsBrief) != "undefined" && model.goodsBrief != null && model.goodsBrief != "")$("#goods_brief").html("【"+model.goodsBrief+"】");
			$("#shop_price").html(parseFloat(model.shopPrice / 100).toFixed(2));
			$("#goods_number").html(model.goodsNumber);
			$("#app_desc").html(model.appDesc);
			
			var map = data.map;
			$("#cart_quantity").html(map.cart_quantity);
			if(parseInt(map.favorites_quantity) > 0){
				$("#collect").html("已收藏");
				$("#collect").attr("isFavorites",1);
			}
			
			$(".swiper-wrapper").append('<div class="swiper-slide"><a href="javascript:;"><img src="'+model.goodsImg+'" ></a></div>');
			var gallery = map.gallery;
			
			var mySwiper = new Swiper ('.swiper-container', {
				    direction: 'horizontal',
				    loop: true,
				    autoplay: 3000,
				    // 如果需要分页器
				    pagination: '.swiper-pagination',
				    
				    // 如果需要前进后退按钮
				    nextButton: '.swiper-button-next',
				    prevButton: '.swiper-button-prev',
				    
				    // 如果需要滚动条
				    //scrollbar: '.swiper-scrollbar',
				  }) ;
			
			$(".swiper-slide").css({height:mySwiper.width+"px"});
			$(".swiper-slide a").css({width:mySwiper.width+"px" , height:mySwiper.width+"px"});
			$(".swiper-slide a img").css({"max-width":mySwiper.width+"px" , "max-height":mySwiper.width+"px"});
			
			goodsinfo_top = $(".goodsinfo").position().top;  
			winH = $(window).height();
			pageH = $(document.body).height();
			scrollT = $(window).scrollTop(); //滚动条top 
			ulH = $("#main").height();
				
//			console.log("页面高度3："+winH+" / "+ pageH + " / "+scrollT + " / "+ulH);
			
			 
		},
		error : function(e){}
	});
}

function addCart(){
	$("#popup_layer").removeClass("dn");
}
function close_popup_layer(){
	$("#popup_layer").addClass("dn");
}

function addCartSubmit(){
	$.post("/ws/cart_add_submit", { store_id : store_id , goods_id : goods_id, quantity : $("#quantity").html()},
			function(data){
		
				$.ejq.toast(data.msg);
				
				if(data.code != 1){					
					return ;
				}
				
				$("#cart_quantity").html(parseInt(($("#cart_quantity").html() == null || $("#cart_quantity").html() == "")?0:$("#cart_quantity").html()) + parseInt($("#quantity").html()));
				
			},"json"
		);
}

function favorites_add(){
	if(parseInt($("#collect").attr("isFavorites")) == 1){
		$.ejq.toast("已收藏");
		$("#collect").html("已收藏");
		return ;
	}
	$.post("/ws/favorites_add", { goods_id : goods_id},
		function(data){
			if(data.code != 1){
				$.ejq.toast(data.msg);
				return ;
			}
			$("#collect").html(data.msg);
			$("#collect").attr("isFavorites",1);
			
		},"json"
	);
}


//减加购物车数量
function cart_quantity(_this,opt){
	var quantity = $("#quantity").html();
	if(quantity == null || quantity == "")quantity = 1;	
	
	if(opt == "plus"){
		
		quantity = parseInt(quantity) + 1;
		
		$("#quantity").html(quantity);
		$(".subtract").removeClass("isvoid");
		
	}else if(opt == "subtract"){
		if(parseInt(quantity) > 1){
			
			quantity = parseInt(quantity) - 1;
			$("#quantity").html(quantity);
		}
		
		if(parseInt(quantity) <= 1){
			$(".subtract").addClass("isvoid");			
		}
	}
	
}


