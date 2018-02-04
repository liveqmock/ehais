function onBridgeReady() {
    WeixinJSBridge.call('hideOptionMenu');
}

if (typeof WeixinJSBridge == "undefined") {
    if (document.addEventListener) {
        document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
    } else if (document.attachEvent) {
        document.attachEvent('WeixinJSBridgeReady', onBridgeReady);
        document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
    }
} else {
    onBridgeReady();
}



$(function(){
	$("#num li").click(function(){
		$("#num li").removeClass("active");
		$("#number_item").addClass("dn");
		$(this).addClass("active");
		$("#number").val($(this).attr("num"));
		if($(this).attr("num") == "3"){
			$("#number_item").removeClass("dn");
		}
	});
	
	$("#submit").click(function(){
		if($.trim($("#number").val()) == ""){
			elay.toast({"content":"请输入请假天数"});return ;
		}
		elay.confirm({content:"确认提交请假申请吗",btn:["确定提交","暂不提交"],sure:function(){
			submit();
		}});
	});
});

function submit(){
	$.ajax({
		url : "ep_school_begoff_submit",
		data:{
			number:$("#number").val(),
			reason:$("#reason").val()
		},
		success:function(result){
			elay.open({content:result.msg});
			if(result.code == 1){
				$("#num li").unbind();
				$("#number").attr("readonly",true);
				$("#reason").attr("readonly",true);
				$("#submit").attr("disabled",true);
				$("#submit").html("提交成功");
			}
		}
	});
}