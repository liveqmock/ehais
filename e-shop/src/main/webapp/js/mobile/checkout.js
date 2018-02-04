/**
 * 
 */

$(function(){
//	console.log(localStorage["cart_rec_id"]);
	checkout();
	$("#change_cart_list").click(function(){window.location.href = "cart_list";});
	$("#done").click(function(){showPopupDone();});
	$("#add_address").click(function(){
		add_address();
	});
	$("#address_li").click(function(){
		switch_address();
	});
	
	//当默认地址不为空时，就选择当前的地址作为默认地址，暂不选择网络的
	if(typeof(localStorage["cur_address"]) != "undefined" && localStorage["cur_address"] != null && localStorage["cur_address"] != ""){
		console.log("使用本地存储地址");
		var address_json = JSON.parse(localStorage["cur_address"]);
		
		$("#addressId").val(address_json.addressId);
		$("#consignee").html(address_json.consignee);
		$("#mobile").html(address_json.mobile);
		$("#address").html(address_json.address);
		
		$("#country").attr("data-id",address_json.country);
		$("#province").attr("data-id",address_json.province);
		$("#city").attr("data-id",address_json.city);
		$("#county").attr("data-id",address_json.county);
		$("#district").attr("data-id",address_json.district);
		$("#street").attr("data-id",address_json.street);
		
		$("#country").html(address_json.country_val);
		$("#province").html(address_json.province_val);
		$("#city").html(address_json.city_val);
		$("#county").html(address_json.county_val);
		$("#district").html(address_json.district_val);
		$("#street").html(address_json.street_val);
		
	}
	
	console.log("进入并刷新...");
});


function checkout(){
	if(typeof(localStorage["cart_rec_id"]) == "undefined" || localStorage["cart_rec_id"] == null || localStorage["cart_rec_id"] == ""){
		window.location.href = "cart_list";
		return ;
	}
	$.post("/ws/checkout",{recIds : localStorage["cart_rec_id"]},function(data){
		if(data.code != 1){
			$.ejq.toast(data.msg);
			window.location.href = "cart_list";
			return ;
		}
		
		if(typeof(data.map) == "undefined"){
			$.afui.toast("重新登录");
			return ;
		}

		if(typeof(data.map.address) == "undefined"){
			$("#address_li").hide();
		}else{
			
			if(typeof(localStorage["cur_address"]) == "undefined" || localStorage["cur_address"] == null || localStorage["cur_address"] == ""){
				//不存在刚才选择的地址，才使用网络默认的

				var address = data.map.address;
				
				
				
				$("#addressId").val(address.addressId);
				$("#consignee").html(address.consignee);
				$("#mobile").html(address.mobile);
				$("#address").html(address.address);
				
				$("#country").attr("data-id",address.country);
				$("#province").attr("data-id",address.province);
				$("#city").attr("data-id",address.city);
				$("#county").attr("data-id",address.county);
				$("#district").attr("data-id",address.district);
				$("#street").attr("data-id",address.street);
				
				var region = data.map.region;
				
				localStorage["region"] = JSON.stringify(region);

				$.each(region,function(index,ele){
					$("#user_address .region").each(function(){
						var regionId = $(this).attr("data-id");
						if(parseInt(regionId) == parseInt(ele.regionId)){
							$(this).html(ele.regionName);
						}
					});				
				});
				
				
			}
			
			
		}
		
		
		if(typeof(data.map.pay) != "undefined"){
			var pay = data.map.pay;
			var selected = "";
			$.each(pay,function(index,ele){
				selected = "";
				if(ele.isDefault)selected = "selected";
				$("#pay_ul").append('<li data-id="'+ele.payId+'"><em class="checkbox '+selected+'"></em>'+ele.payName+'</li>');
			});
			
			$("#pay_ul li").unbind();
			$("#pay_ul li").click(function(){
				$("#pay_ul li").children("em.checkbox").removeClass("selected");
				$(this).children("em.checkbox").addClass("selected");
			});
		}
		
		if(typeof(data.map.ship) != "undefined"){
			var ship = data.map.ship;
			var selected = "";
			$.each(ship,function(index,ele){
				selected = "";
				if(ele.isDefault)selected = "selected";
				$("#shipping_ul").append('<li data-id="'+ele.shippingId+'"><em class="checkbox '+selected+'"></em>'+ele.shippingName+'</li>');
			});
			
			$("#shipping_ul li").unbind();
			$("#shipping_ul li").click(function(){
				$("#shipping_ul li").children("em.checkbox").removeClass("selected");
				$(this).children("em.checkbox").addClass("selected");
			});
		}
		
		if(typeof(data.map.cart) != "undefined"){
			var list = data.map.cart;
			var sum = 0;
			for (var i = 0; i < list.length; i++) {
				var total = parseInt(list[i].goodsPrice) * parseInt(list[i].goodsNumber);
				sum += total;
				$("#goods_ul").append("<li data-id='"+list[i].goodsId+"' class='goodsinfo pr'>"+
										
										"<img class='' src='"+list[i].goodsThumb+"' />"+
										
										"<div class='item'>"+
										    "<h2>"+list[i].goodsName+"</h2>"+										    
										"</div>"+
										
										"<div class='cart_quantity_div pa' data-RGId='"+list[i].recId+"|"+list[i].goodsId+"|"+list[i].storeId+"' data-price='"+list[i].goodsPrice+"'>"+
									    	"<div class='price fl'>￥"+(parseInt(list[i].goodsPrice) / 100 ).toFixed(2)+" X "+ list[i].goodsNumber +" = " + (total / 100 ).toFixed(2) +" 元</div>"+
									    "</div>" + 
									    
										"</li>"
										);
			}
			
			$("#total").html("￥"+(sum / 100).toFixed(2));
			
		}
		
		
	},"json");
}

function showPopupDone() {
    $.ejq.popup({
        title: "温馨提示",
        message: "是否确认提交订单！",
        cancelText: "取消提交",
        cancelCallback: function () {
            
        },
        doneText: "确定提交",
        doneCallback: function () {
            
            done();
        },
        cancelOnly: false
    });
}


function done(){
	if(typeof(localStorage["cart_rec_id"]) == "undefined" || localStorage["cart_rec_id"] == null || localStorage["cart_rec_id"] == ""){
		$.afui.toast("购物车为空");
		return ;
	}
	if($("#addressId").val()==""){
		$.afui.toast("请填写送货地址");
		return ;
	}
	var payId = "";
	$("#pay_ul li").each(function(index,ele){
		if($(ele).children("em.checkbox").hasClass("selected")){
			payId = $(ele).attr("data-id");
		}		
	});
	
	if(payId==""){
		$.afui.toast("请选择支付方式");
		return ;
	}
	
	
	var shipId = "";
	$("#shipping_ul li").each(function(index,ele){
		if($(ele).children("em.checkbox").hasClass("selected")){
			shipId = $(ele).attr("data-id");
		}		
	});
	
	if(shipId==""){
		$.afui.toast("请选择运送方式");
		return ;
	}
	
	$.ejq.showMask("提交中");
	
	
	$.post("/ws/done",{
			recIds : localStorage["cart_rec_id"] , 
			pay_id:payId, 
			ship_id:shipId, 
			address_id:$("#addressId").val(), 
			message:$("#message").val()
		},		
		function(data){
			if(data.code != 1){
				$.ejq.toast(data.msg);
				return ;
			}
			localStorage["cart_rec_id"] = null;
			localStorage["order"] = JSON.stringify(data);
			$.ejq.hideMask();
			window.location.href = "done";
		
		},"json")
	    .success(function() {  $.ejq.hideMask();})
	    .error(function() {  $.ejq.hideMask();})
	    .complete(function() {  $.ejq.hideMask();});
}

//添加收货人地址
function add_address(){
	
	$.get("user_address_add",null,function(data){
		$("#user_address_info").html(data);
	},"html");

	
}

//切换收货地址
function switch_address(){
	window.location.href = "user_address_list?action=choose";
}
