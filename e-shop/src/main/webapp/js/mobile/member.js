/**
 * 
 */
$(function(){
	$(".view header").prepend("<div class='backButton back goBackClick'>返回</div>");
	$(".view header").prepend("<div id='home' class='pa'><i></i></div>");
	$(".goBackClick").click(function(){window.history.go(-1);});
	$("#home").click(function(){window.location.href = "index-"+localStorage["store_id"];});
	
	$("#member_ul li.member_item").click(function(){
		if(typeof($(this).attr("data-href")) == "undefined" || $(this).attr("data-href") == "")return;
		
		window.location.href = $(this).attr("data-href");
		
	});

	$("#logout").click(function(){logout();});
	
	//localStorage["userId"] = 1;

	member();
	

	
	$(".goUserInfo").click(function(){
		window.location.href = "user_info";
	});
});


function member(){
	
		$.ajax({
			type:"post",
			url:"/ws/member",
			data:null,
			async:true,
			dataType : "json",
			success : function(data){
				console.log(JSON.stringify(data));
				
				$("#div_username").html(data.model.userName);
//				$("#div_username").html(formatNull(data.user.realname,data.user.user_name));
//				$("#face_img").attr("src",formatNull(data.user.face_image));
//				
//				$("#pay_0").html(formatNull(data.statistics[0].pay_0,0));
//				$("#shipping_0").html(formatNull(data.statistics[0].shipping_0,0));
//				$("#shipping_2").html(formatNull(data.statistics[0].shipping_2,0));
				
				var order_status = data.map.order_status;
				$.each(order_status,function(index,ele){
					$("#"+ele.ostatus).html(ele.count);
				});
			},
			error : function(e,m){
				console.log(JSON.stringify(e));
			},beforeSend:function(jqXHR){
				$.ejq.showMask("获取用户信息中...");
			},complete : function(){
				$.ejq.hideMask();
			}
		});
}

function logout(){
	$.ajax({
		type:"post",
		url:"/ws/logout",
		data:null,
		async:true,
		dataType : "json",
		success : function(data){
			console.log(JSON.stringify(data));
			localStorage["userId"] = undefined;
			localStorage["username"] = undefined;
			window.location.href = "index-"+localStorage["store_id"];
		},
		error : function(e,m){
			console.log(JSON.stringify(e));
		},beforeSend:function(jqXHR){
			$.ejq.showMask("获取用户信息中...");
		},complete : function(){
			$.ejq.hideMask();
		}
	});
	
	
}