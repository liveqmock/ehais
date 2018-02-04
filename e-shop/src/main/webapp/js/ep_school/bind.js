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

	$("#submit").click(function(){
		
		elay.confirm({content:"数据无误确认提交吗？",btn:["确定提交","暂不提交"],sure:function(){
			bind();
		}});
	});
});


function bind(){
	var user_name = $("#user_name").val();
	var realname = $("#realname").val();
	
	if($.trim(realname) == ""){
		elay.toast({"content":"请输入姓名"});return ;
	}
	
	if($.trim(user_name) == ""){
		elay.toast({"content":"请输入学号"});return ;
	}
	
	
	$.ajax({
		url : "ep_school_bind_submit",data:{user_name:user_name,realname:realname},
		success:function(result){
			elay.open({content:result.msg});
			if(result.code == 1){
				$("#user_name").attr("readonly",true);
				$("#realname").attr("readonly",true);
				$("#submit").attr("disabled",true);
				$("#submit").html("绑定成功");
			}
		}
	});
	
	
}

