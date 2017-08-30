var store_id = 56;
$(function(){
	$("header .fa-chevron-left").click(function(){window.history.go(-1);});
	$(".swiper-container").width($(".w_goods_wrapper").width());
	$(".swiper-container").height($(".w_goods_wrapper").width());
	
	var mySwiper = new Swiper ('.swiper-container', {
	    direction: 'horizontal',
	    loop: true,
	    autoplay: 5000,
	    pagination: '.swiper-pagination',
	});  
	
	//头顶的切换
	$("header .tab").click(function(){
		$("header .tab").removeClass("active");
		$(this).addClass("active");
		if($(this).attr("tab") == 1){
			$(".base").addClass("active");
			$(".detail").removeClass("active");
		}else if($(this).attr("tab") == 2){
			$(".detail").addClass("active");
			$(".base").removeClass("active");
		}
	});
	
	$("#addCart").click(function(){addCart();});//添加到购物车
	$("#buynow").click(function(){buynow();});//立即购买
	$("#cart").click(function(){window.location.href="w_cart"});
	
	quantityCart();//读取当前购物车数量
});
//加载购物车数量
function quantityCart(){
	$.ajax({
		url : "/ws/cart_quantity",type:"post",dataType:"json",
		success : function(result){
			
			if(result.total > 0){
				$("#quantity").html(result.total);
				$("#quantity").addClass("active");
			}
		}
	});
}
//加入购物车
function addCart(){
	$.ajax({
		url : "/ws/cart_add_submit",type:"post",dataType:"json",
		data : {"goods_id":$("#goodsId").val(),"parent_user_id":$("#parentUserId").val(),"agency_id":$("#agencyId").val(),"article_id":$("#articleId").val(),"quantity":1,store_id:store_id},
		success : function(result){
			if(result.code != 1){
				layer.open({
				    content: result.msg
				    ,btn: '朕知道了'
				  });
				return ;
			}
			//更新购物车数量
			$("#quantity").html(result.total);
			$("#quantity").addClass("active");
		},error : function(err,xhr){
			
		}
	});
}
//立即购买
function buynow(){
	
	
	$.ajax({
		url : "/ws/cart_add_submit",type:"post",dataType:"json",
		data : {"goods_id":$("#goodsId").val(),"parent_user_id":$("#parentUserId").val(),"agency_id":$("#agencyId").val(),"article_id":$("#articleId").val(),"quantity":1,store_id:store_id},
		success : function(result){
			if(result.code != 1 && result.code != 2){
				layer.open({
				    content: result.msg
				    ,btn: '朕知道了'
				  });
				return ;
			}
			//更新购物车数量
			localStorage.setItem("recIds",result.model.recId);
			window.location.href = "w_check_order";
		},error : function(err,xhr){
			
		}
	});
	
}