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
	$(".submit").click(function(){
		var approve = $(this).attr("approve");
		elay.confirm({content:"数据无误确认提交吗？",btn:["确定提交","暂不提交"],sure:function(){
			approve_submit(approve);
		}});
	});
});

function approve_submit(approve){
	$.ajax({
		url : "ep_school_begapprove_submit",data:{begid:$("#begid").val(),approve:approve},
		success : function(result){
			elay.open({content:result.msg});
			if(result.code == 1){
				$(".submit").attr("disabled",true);
			}
		}
	});
}
