$(function(){

	$("#saveSubmit").click(function(){$("#myForm").attr("action") == "add" ? haiPurchaseAddSubmit() : haiPurchaseEditSubmit() ;});

});


function haiPurchaseAddSubmit(){
	
	if($("#purchaseNo").val() == undefined || $("#purchaseNo").val().length == 0){
		layer.msg("请输入进货单");return ;
	}

	
	
	$.ajax({
		url : "haiPurchaseAddSubmit",
		type:"post",dataType:"json",data:$("#myForm").serialize(),
		success:function(result){
			if(result.code != 1){
				layer.msg(result.msg);
				return ;
			}else{
				$('#myForm')[0].reset();
				layer.confirm(result.msg, {
					btn: ['继续添加','返回列表'] //按钮
				}, function(){
				  //继续添加
					layer.closeAll();
				}, function(){
				  //返回列表
					layer.closeAll();
					window.history.back();
				});
			}
		}
	});
}

function haiPurchaseEditSubmit(){
	
	if($("#purchaseNo").val() == undefined || $("#purchaseNo").val().length == 0){
		layer.msg("请输入进货单");return ;
	}

	
	
	$.ajax({
		url : "haiPurchaseEditSubmit",
		type:"post",dataType:"json",data:$("#myForm").serialize(),
		success:function(result){
			if(result.code != 1){
				layer.msg(result.msg);
				return ;
			}else{
				$('#myForm')[0].reset();
				layer.alert(result.msg, {
					skin: 'layui-layer-molv' //样式类名
					,closeBtn: 0
				}, function(){
					layer.closeAll();
					window.history.back();
				});
			}
		}
	});
}
