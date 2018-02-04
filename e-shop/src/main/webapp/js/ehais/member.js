$(function(){
	if(sessionStorage.getItem("header")!=null && !is_iphone())$(".wm").addClass("header");
	
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
	
	$("#sign").click(function(){
		sign();
	});
	
});
var isTodaySign = 0;
var signCount= 0;
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
			$("#amount").html("帐户:￥"+(model.userMoney / 100).toFixed(2) );
			$("#integral").html("积分:"+model.payPoints );
			$("#signCount").html("签到:"+result.map.signCount );
			
			isTodaySign = result.map.isTodaySign;
			signCount = result.map.signCount;
			
		}
	});
}


function sign(){
	if(isTodaySign > 0){
		elay.toast({content:"今天已签到了"});
		return ;
	}
	$.ajax({
		url : "/ws/sign",success:function(d){
			if(d.code == 1){
				isTodaySign = 1;
				signCount ++;
				$("#signCount").html("签到天数:"+signCount );
				elay.toast({content:d.msg});
			}
		}
	})
}

