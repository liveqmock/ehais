
$(function(){
	$("#morning").click(function(){create_time(6,11,"morning");});
	$("#midday").click(function(){create_time(12,18,"midday");});
	$("#night").click(function(){create_time(18,23,"night");});	
	$("dl.minute dd").unbind().remove();	
	for(var i = 0 ; i < 12 ; i ++){
		$("dl.minute").append("<dd>00:"+PrefixInteger(i*5,2)+"<i class='fa fa-hand-o-left'></i></dd>");
	}
	$("dl.minute dd").click(function(){
		$("dl.minute dd").removeClass("active");
		$(this).addClass("active");
	});
	
	$("#sureTime").click(function(){
		$(".vtu_time").removeClass("active");
		var hour = $("dl.hour .active").text();
		var minute = $("dl.minute .active").text();
		$("#"+$("dl.hour").attr("val")).html(hour + minute.substr(2));
	});
	
	
	$("#qrcode").click(function(){
		chooseImage("showPic","pic");
		$(".pic").addClass("active");
	});
	
	$("#nowShare").click(function(){
		window.location.href = $(this).attr("href");
	});
	
	
	//保存事件
	$("#saveSign").click(function(){
		var morning = $("#morning").text();
		var midday = $("#midday").text();
		var night = $("#night").text();
		var inspire = $("#inspire").val();
		var mobile = $("#mobile").val();
		var realname = $("#realname").val();
		var business = $("#business").val();
		var pic = $("#pic").val();
		
		$.ajax({
			url:"/vtuSignSave!"+sid,
			data : {morning:morning,midday:midday,night:night,inspire:inspire,mobile:mobile,realname:realname,business:business,pic:pic},
			success : function(result){
//				elay.open({content:result.msg,btn:"知道了"});
				elay.confirm({content:result.msg,btn:["即时签到","知道了"],sure:function(){
					window.location.href = result.action;
				}});
			}
		});
		
	});
	if($("#pic").val() != null && $("#pic").val()!=""){
		$(".pic").addClass("active");
	}
});

function create_time(start,end,time){
	$(".vtu_time").addClass("active");
	$("dl.hour dd").unbind();
	$("dl.hour dd").remove();
	$("dl.minute dd").removeClass("active");
	$("dl.hour").attr("val",time);
	for(var i = start ; i <= end ; i++){
		$("dl.hour").append("<dd >"+PrefixInteger(i,2)+"<i class=\"fa fa-hand-o-left\"></i></dd>");
	}
	$("dl.hour dd").click(function(){
		$("dl.hour dd").removeClass("active");
		$(this).addClass("active");
	});
	var timeValue = $("#"+time).text();
	var tve = timeValue.split(":");
	$("dl.hour dd").each(function(){
		if(parseInt($(this).text()) == parseInt(tve[0])){
			$(this).addClass("active");
		}
	});
	$("dl.minute dd").each(function(){
		if(parseInt($(this).text().replace("00:","")) == parseInt(tve[1])){
			$(this).addClass("active");
		}
	});
	
}

function PrefixInteger(num, length) {
 return (Array(length).join('0') + num).slice(-length);
}
