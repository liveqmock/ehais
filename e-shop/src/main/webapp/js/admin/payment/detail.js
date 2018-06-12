$(function(){

	$("#saveSubmit").click(function(){$("#haiPaymentForm").attr("action") == "add" ? haiPaymentAddSubmit() : haiPaymentEditSubmit() ;});

});


function haiPaymentAddSubmit(){
	
	if($("#payCode").val() == undefined || $("#payCode").val().length == 0){
		layer.msg("请输入简码");return ;
	}
	if($("#payName").val() == undefined || $("#payName").val().length == 0){
		layer.msg("请输入名称");return ;
	}

	
	
	$.ajax({
		url : "haiPaymentAddSubmit",
		type:"post",dataType:"json",data:$("#haiPaymentForm").serialize(),
		success:function(result){
			if(result.code != 1){
				layer.msg(result.msg);
				return ;
			}else{
				$('#haiPaymentForm')[0].reset();
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

function haiPaymentEditSubmit(){
	
	if($("#payCode").val() == undefined || $("#payCode").val().length == 0){
		layer.msg("请输入简码");return ;
	}
	if($("#payName").val() == undefined || $("#payName").val().length == 0){
		layer.msg("请输入名称");return ;
	}

	
	
	$.ajax({
		url : "haiPaymentEditSubmit",
		type:"post",dataType:"json",data:$("#haiPaymentForm").serialize(),
		success:function(result){
			if(result.code != 1){
				layer.msg(result.msg);
				return ;
			}else{
				$('#haiPaymentForm')[0].reset();
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
