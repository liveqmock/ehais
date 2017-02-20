/**
 * 
 */
$(function(){
	$("#page li.pay dl dd,#page li.shipping dl dd").mouseover(function(){
		$(this).addClass("activity");
	}).mouseout(function(){
		$(this).removeClass("activity");
	});
	$("#page li.pay dl dd").click(function(){
		$("#page li.pay dl dd").removeClass("selected");
		$(this).addClass("selected");
	});
	$("#page li.shipping dl dd").click(function(){
		$("#page li.shipping dl dd").removeClass("selected");
		$(this).addClass("selected");
	});
	$("#checkout").click(function(){
		if($("#page li.pay dl dd.selected").length == 0){
			console.log("未选中支付信息");return;
		}
		if($("#page li.shipping dl dd.selected").length == 0){
			console.log("未选中货运信息");return;
		}
		var pay = $("#page li.pay dl dd.selected").attr("data-val");
		var shipping = $("#page li.shipping dl dd.selected").attr("data-val");
		var cart = getChooseCart();
		var user_address = getChooseUserAddress();
		console.log("user_address:"+user_address);
	});

});

function getChooseUserAddress(){
	var addressId = 0;
	$("#page li.address dl dd").each(function(){
		if($(this).hasClass("addr-cur")){
			addressId = $(this).attr("data-addressId");
			return false;
		}
	});
	return addressId;
}
