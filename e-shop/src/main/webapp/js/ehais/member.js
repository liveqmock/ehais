$(function(){
	member();
	
	$(".t b:first").click(function(){
		$(".b,.f,.t b").removeClass("active");
		$(".a,.s").addClass("active");
		$(this).addClass("active");
	});
	$(".t b:last").click(function(){
		$(".a,.s,.t b").removeClass("active");
		$(".b,.f").addClass("active");
		$(this).addClass("active");
	});
	
	$("#oq li").click(function(){
		window.location.href="w_member_order#"+$(this).attr("for");
	});
	
	$("#mh li,#fh li").click(function(){
		if($(this).attr("h") != null)window.location.href=$(this).attr("h");
	});
	
});

function member(){
	$.ajax({
		url : "/ws/member",
		success : function(result){
			console.log(JSON.stringify(result));
			
			if(result.map.mOrder!=null){
				$.each(result.map.mOrder,function(k,v){
					$("#"+k).html(v);
					if(v!=null && parseInt(v) > 0)$("#"+k).addClass("active");
				});
			}
			
			var model = result.model;
			$(".u").html(model.nickname);
			$("#faceImage").attr("src",model.faceImage);
			if(model.mobilePhone != null && model.mobilePhone != ""){
				$(".p").html(model.mobilePhone);
			}else{
				$(".p").html("未绑定");
			}
			$("#amount").html("帐户余额:￥"+(model.userMoney / 100).toFixed(2) );
			$("#integral").html("积分:"+model.payPoints );
			
		}
	});
}

