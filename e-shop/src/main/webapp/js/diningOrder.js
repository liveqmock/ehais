$(function(){
	user_address_default();
	$("#submitOrder").click(function(){submitOrder();});//提交订单点击
	if(localStorage.getItem("localCartArray") != null){
		var cartArray = JSON.parse(localStorage.getItem("localCartArray"));
		totalAmount(cartArray);
		cartArray = null;
	}else{
		//需要回退
	}
	
});

//获取用户的默认地址
function user_address_default(){
	$.ajax({
		type:"post",
		dataType:"json",
		url:"/api.php/Api/DiningApi/user_address_default",
		async:true,
		success:function(result){
			
			var model = result.model;
			if(model != null && model != "null" && model != ""){
				$("#consignee").val(model.consignee);
				$("#mobile").val(model.mobile);
				$("#address").val(model.address);
			}
			
			model = null;
		}
	});
}
//提交订单事件
function submitOrder(){
	if($.trim($("#consignee").val()) == ""){
		layer.open({content: '收餐人不能为空',skin: 'msg',time: 3 });
		$("#consignee").focus(); 
		return ;
	}
	if($.trim($("#mobile").val()) == ""){
		layer.open({content: '手机号不能为空',skin: 'msg',time: 3 });
		$("#mobile").focus(); 
		return ;
	}
	if(!(/^1[3|4|5|8][0-9]\d{4,8}$/.test($.trim($("#mobile").val())))){ 
        layer.open({content: '手机号不正确',skin: 'msg',time: 3 });
        $("#mobile").focus(); 
        return false; 
    } 
	if($.trim($("#address").val()) == ""){
		layer.open({content: '收餐地址不能为空',skin: 'msg',time: 3 });
		$("#address").focus();
		return ;
	}
	
	$.ajax({
		url : "/api.php/Api/DiningApi/submitOrder",
		data : {
			consignee:$.trim($("#consignee").val()),
			mobile:$.trim($("#mobile").val()),
			address:$.trim($("#address").val()),
			remark:$.trim($("#remark").val()),
			cart:localStorage.getItem("localCartArray"),
			dining:localStorage.getItem("dining"),
		},
		type:"post",
		dataType:"json",
		success:function(result){
			if(result.code != 1){
				layer.open({content: result.msg,skin: 'msg',time: 3 });
				return ;
			}
			//清空以下数据
			$("#consignee").val("");
			$("#mobile").val("");
			$("#address").val("");
			$("#remark").val("");
			localStorage.setItem("localCartArray",null);
			
			var model = result.model;
			
			if(model == null || model == "null" || model == "")return ;
			
			$(".wrapper_dining_order_success").addClass("active");
			$("#order_sn").html(model.order_sn);
			$("#amount").html("￥"+model.total_amount);
			//调起微信的请求
			onBridgeReady(result.payment);
			
		}
	});
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
