/**
 * 
 */

$(function(){
	order_info();
});

function order_info(){
	$.ajax({
		url : "/ws/orderinfo_info",
		data : { orderId : orderId},
		type : "post",
		dataType : "json",
		success : function(data){},
		error : function(e){}
	});
}

