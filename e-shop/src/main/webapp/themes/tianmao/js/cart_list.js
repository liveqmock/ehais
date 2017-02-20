var timeout ;

$(function(){
	totalAmount();
	
	$("#cartUl .subtract").unbind();$("#goods_ul .plus").unbind();
	$("#cartUl .subtract").click(function(){cart_quantity(this,"subtract")});
	$("#cartUl .plus").click(function(){cart_quantity(this,"plus")});
	$("#cartUl .goodsinfo input[type='checkbox']").unbind();
	$("#cartUl .goodsinfo input[type='checkbox']").click(function(){
		$(this).toggleClass("selected");
		totalAmount();
	});
	
	$("#cartUl .totalLi input[type='checkbox']").click(function(){
		
		if($(this).is(':checked')){
			$("#cartUl .goodsinfo input[type='checkbox']").prop("checked","checked");
		}else{
			$("#cartUl .goodsinfo input[type='checkbox']").prop("checked","");
		}
		totalAmount();
	});
	
	
});

//统计总金额
function totalAmount(){
	var sum = 0;
	var number = 0;
	$("#cartUl li.goodsinfo").each(function(){
		var quantity = $(this).children("dl").children("dd.cart-quantity").children(".quantity").html();				
		if(quantity == null || quantity == "")quantity = 0;
		var goodsPrice = $(this).children("dl").children("dd.cart-price").children("span").html();
		
		if($(this).children("dl").children("dd.cart-checkbox").children("input[type='checkbox']").is(':checked')){
			sum += parseInt(quantity) * parseInt(goodsPrice);
			number += parseInt(quantity);
		}
		
		
	});
	
	$("#gTotal").html("合计：￥"+(sum / 100).toFixed(2)+"元");
	$("#gNumber").html("共"+number+"件商品");
}

//减加购物车数量
function cart_quantity(_this,opt){
	var quantity = $(_this).parent().children(".quantity").html();
	if(quantity == null || quantity == "")quantity = 1;	
	
	clearTimeout(timeout);
	if(opt == "plus"){
		
		quantity = parseInt(quantity) + 1;
		
		$(_this).parent().children(".quantity").html(quantity);
		$(_this).parent().children(".subtract").removeClass("isvoid");
		
	}else if(opt == "subtract"){
		if(parseInt(quantity) > 1){
			
			quantity = parseInt(quantity) - 1;
			$(_this).parent().children(".quantity").html(quantity);
		}
		
		if(parseInt(quantity) <= 1){
			$(_this).parent().children(".subtract").addClass("isvoid");			
		}
	}
	var price = $(_this).parent().parent().children("dd.cart-price").children("span").html();
	$(_this).parent().parent().children("dd.cart-sum").html("￥"+(parseInt(quantity) * parseInt(price) / 100).toFixed(2) );
	timeout = setTimeout("updateCartQuantity('"+$(_this).parent().parent().parent().attr("data-recid")+"','"+$(_this).parent().parent().parent().attr("data-goodsid")+"','"+quantity+"')",3000);
	totalAmount();
}


//更新购物车数量
function updateCartQuantity(recId,goods_id,quantity){
	
	$.post("/ws/cart_edit_submit", { recId : recId, goods_id : goods_id, quantity : quantity},
			function(data){
		
				if(data.code != 1){	
					
					return ;
				}
				
				
			},"json"
		);
}

function getChooseCart(){
	var cart = new Array();
	
	$("#cartUl li.goodsinfo").each(function(){
		
		if($(this).children("dl").children("dd.cart-checkbox").children("input[type='checkbox']").is(':checked')){
			cart.push($(this).attr("data-recid"));
		}
		
		
	});
	console.log("选中的购物车项："+cart.join(","));
	return cart;
	
}


