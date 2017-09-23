var recIdTime;
var recIdTemp = 0;
var recid = 0 ;
var quantity = 0;
$(function(){
	$("header .fa-chevron-left").click(function(){window.history.go(-1);});
	$(".icon-jia-xianxingfangkuang").click(function(){
		var recid = $(this).parent().parent().attr("recid");
		var goodsid = $(this).parent().parent().attr("goodsid");
		var quantity = $(this).parent().parent().attr("quantity");
		quantity = parseInt(quantity) + 1;
		$(this).parent().parent().attr("quantity",quantity);
		$(this).parent().children(".quantity").html(quantity);
		
		if(recIdTemp == recid)clearTimeout(recIdTime);
		recIdTime = setTimeout(function(){
			cart_edit_submit(recid,goodsid,quantity);
			recid = null,goodsid=null,quantity=null;
		},1000);
		recIdTemp = recid;
		cartListItem();
	});
	$(".icon-jian-xianxingfangkuang").click(function(){
		var recid = $(this).parent().parent().attr("recid");
		var goodsid = $(this).parent().parent().attr("goodsid");
		var quantity = $(this).parent().parent().attr("quantity");
		if(parseInt(quantity) <= 1 )return ;
		quantity = parseInt(quantity) - 1;
		$(this).parent().parent().attr("quantity",quantity);
		$(this).parent().children(".quantity").html(quantity);
		
		if(recIdTemp == recid)clearTimeout(recIdTime);
		recIdTime = setTimeout(function(){
			cart_edit_submit(recid,goodsid,quantity);
			recid = null,goodsid=null,quantity=null;
		},1000);
		recIdTemp = recid;
		cartListItem();
	});
	$(".icon-lajixiang").click(function(){
		cart_delete_submit();
	});
	
	$(".singleCheck").click(function(){
		$(this).toggleClass("active");
		if($(".list > .item > .fa-check-circle.active").length == $(".list > .item").length){
			$(".allCheck").addClass("active");
		}else{
			$(".allCheck").removeClass("active");
		}
		cartListItem();
	});
	$(".allCheck").click(function(){
		if($(this).hasClass("active")){
			$(".list > .item > .fa-check-circle").removeClass("active");
		}else{
			$(".list > .item > .fa-check-circle").addClass("active");
		}
		$(this).toggleClass("active");
		cartListItem();
	});
	//立即结算
	$("#checkOrder").click(function(){
		var recIds = new Array();
		$(".list > .item").each(function(index,ele){
			if($(ele).children(".singleCheck").hasClass("active")){
				recIds.push($(ele).attr("recid"));
			}
		});
		if(recIds.length == 0){
			elay.open({
			    content: '请选择购物车要结算的商品'
			    ,btn: '朕知道了'
			});
		}else{
			sessionStorage.setItem("recIds",recIds.join(","));
			window.location.href = "w_check_order!"+sid;
		}
	});
	$("#indexLink").click(function(){window.location.href=$(this).attr("href");});
});

function cart_edit_submit(recid,goodsid,quantity){
	$.ajax({
		url : "/ws/cart_edit_submit",type:"post",dataType:"json",
		data:{recId:recid,goods_id:goodsid,quantity:quantity},
		beforeSend: function () {},
		success:function(result){
			
		}
	});
}

function cart_delete_submit(){
	var recIds = new Array();
	$(".list > .item").each(function(index,ele){
		if($(ele).children(".singleCheck").hasClass("active")){
			recIds.push($(ele).attr("recid"));
		}
	});
	if(recIds.length == 0){
		elay.open({
		    content: '请选择购物车要结算的商品',
		    btn: '朕知道了'
		});
		return ;
	}
	
	var recid = recIds.join(",");
	elay.confirm({
	    content: '确定要删除此商品吗？'
	    ,btn: ['确定' , '取消']
	    ,sure: function(index){ 
	    	
	    	$.ajax({
	    		url : "/ws/cart_delete_submit",
	    		data:{recIds:recid},
	    		success:function(result){
	    			if(result.code != 1){
	    				elay.open({content:result.msg});
	    				return ;
	    			}
	    			
	    			$(".list > .item").each(function(index,ele){
    					if($(ele).children(".singleCheck").hasClass("active")){
    						$(ele).remove();
    					}
    				});
	    			
	    			if($(".list > .item").length > 0){
	    				cartListItem();
	    			}else{
	    				$(".empty").addClass("active");
	    				$(".icon-lajixiang").hide();
	    				$("footer").hide();
	    			}
    				
    				
	    		}
	    	});
	    }
	 });
	
	
}

function cartListItem(){
	var totle = 0;
	$(".list > .item").each(function(index,ele){
		if($(ele).children(".singleCheck").hasClass("active")){
			totle += (parseInt($(ele).attr("price")) * parseInt($(ele).attr("quantity")));
		}
	});
	$("#total").html("￥"+(totle / 100 ).toFixed(2));//
}
