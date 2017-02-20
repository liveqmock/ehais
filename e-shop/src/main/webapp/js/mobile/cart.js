/**
 * 
 */

var cart_page = 1;
var len = 10;
var timeout ;
$(function(){
	$(".view header").prepend("<div class='backButton back goBackClick'>返回</div>");
	$(".goBackClick").click(function(){window.history.go(-1);});
	cart_list(0);
	
	$(".all_choose em.checkbox").click(function(){
		$(this).toggleClass("selected");
		if($(this).hasClass("selected")){
			$("#goods_ul em.checkbox").addClass("selected");
		}else{
			$("#goods_ul em.checkbox").removeClass("selected");
		}
		totalAmount();
	});
	
	$("#checkout").click(function(){
		checkout();
	});
});

function cart_list(parent_id){
	$.ajax({
		url : "/ws/cart_list",
		data : {page : cart_page  , len : len},
		type : "post",
		dataType : "json",
		success : function(data){
			if(data.code != 1){
				$.afui.toast(data.msg);
				return ;
			}
			$("#goods_ul li").remove();
			var list = (typeof(data.rows) == undefined ) ? new Array() : data.rows;
			if(list.length == 0){
				$("#goods_ul").append('<li class="empty"><h1>:)</h1><p>购物车还没有宝贝哦， 去看看其他商品吧！</p></li>');
				return ;
			}
			for (var i = 0; i < list.length; i++) {
				$("#goods_ul").append("<li data-id='"+list[i].goodsId+"' class='goodsinfo pr'>"+
										
										"<div class='img'>"+	
										"<a class='img' href='javascript:;'>"+
										"<img class='' src='"+list[i].goodsThumb+"' />"+
										"</a>"+
										"</div>"+
										
										"<div class='item'>"+
										    "<h2>"+list[i].goodsName+"</h2>"+										    
										"</div>"+
										
										"<div class='cart_quantity_div pa' data-RGId='"+list[i].recId+"|"+list[i].goodsId+"|"+list[i].storeId+"' data-price='"+list[i].goodsPrice+"'>"+
									    	"<div class='price fl'>￥"+(parseInt(list[i].goodsPrice) / 100 ).toFixed(2)+"</div>"+
									    	"<div class='subtract fl'>-</div>"+
									    	"<div class='quantity fl'>"+list[i].goodsNumber+"</div>"+
									    	"<div class='plus fl'>+</div>"+
									    "</div>" + 
									    
									    "<em class='checkbox selected pa' data-rid='"+list[i].recId+"'></em>" +
									    
										"</li>"
										);
			}
			
			$("#goods_ul .cart_quantity_div").each(function(){
				var quantity = $(this).children(".quantity").html();				
				if(quantity == null || quantity == "")quantity = 0;				
				if(parseInt(quantity) <= 1)$(this).children(".subtract").addClass("isvoid");
			});
			
			$("#goods_ul .subtract").unbind();$("#goods_ul .plus").unbind();
			$("#goods_ul .subtract").click(function(){cart_quantity(this,"subtract")});
			$("#goods_ul .plus").click(function(){cart_quantity(this,"plus")});
			$("#goods_ul em.checkbox").unbind();
			$("#goods_ul em.checkbox").click(function(){
				$(this).toggleClass("selected");
				totalAmount();
			});
			totalAmount();
		},
		error : function(e){}
	});
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
	
	timeout = setTimeout("updateCartQuantity('"+$(_this).parent().attr("data-RGId")+"|"+quantity+"')",3000);
	totalAmount();
}

//更新购物车数量
function updateCartQuantity(rgId){
	console.log("rgId:"+rgId);
	var rgIds = rgId.split("|");
	$.post("/ws/cart_edit_submit", { store_id : rgIds[2] , recId : rgIds[0], goods_id : rgIds[1], quantity : rgIds[3]},
			function(data){
		
				if(data.code != 1){	
					$.ejq.toast(data.msg);
					return ;
				}
				
				
			},"json"
		);
}
//统计总金额
function totalAmount(){
	var sum = 0;
	$("#goods_ul li.goodsinfo").each(function(){
		var quantity = $(this).children(".cart_quantity_div").children(".quantity").html();				
		if(quantity == null || quantity == "")quantity = 0;
		var goodsPrice = $(this).children(".cart_quantity_div").attr("data-price");
		
		if($(this).children("em.checkbox").hasClass("selected"))
		sum += parseInt(quantity) * parseInt(goodsPrice);
		
	});
	
	$("#gTotal").html("￥"+(sum / 100).toFixed(2));
}
//结算
function checkout(){
	var recIds = new Array();
	$("#goods_ul li.goodsinfo").each(function(){
		if($(this).children("em.checkbox").hasClass("selected")){
			recIds.push($(this).children("em.checkbox").attr("data-rid"));
		}
	});
	if(recIds.length == 0){
		$.ejq.toast("请选择购物车商品");
		return ;
	}
	localStorage["cart_rec_id"] = recIds.join(",");
	
	
	window.location.href = "checkout";
	
}