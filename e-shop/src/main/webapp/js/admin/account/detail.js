$(function(){

	$("#saveSubmit").click(function(){$("#haiAccountForm").attr("action") == "add" ? haiAccountAddSubmit() : haiAccountEditSubmit() ;});

});


function haiAccountAddSubmit(){
	
	if($("#accountCode").val() == undefined || $("#accountCode").val().length == 0){
		layer.msg("请输入简码");return ;
	}
	if($("#accountName").val() == undefined || $("#accountName").val().length == 0){
		layer.msg("请输入名称");return ;
	}

	
	
	$.ajax({
		url : "haiAccountAddSubmit",
		type:"post",dataType:"json",data:$("#haiAccountForm").serialize(),
		success:function(result){
			if(result.code != 1){
				layer.msg(result.msg);
				return ;
			}else{
				$('#haiAccountForm')[0].reset();
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

function haiAccountEditSubmit(){
	
	if($("#accountCode").val() == undefined || $("#accountCode").val().length == 0){
		layer.msg("请输入简码");return ;
	}
	if($("#accountName").val() == undefined || $("#accountName").val().length == 0){
		layer.msg("请输入名称");return ;
	}

	
	
	$.ajax({
		url : "haiAccountEditSubmit",
		type:"post",dataType:"json",data:$("#haiAccountForm").serialize(),
		success:function(result){
			if(result.code != 1){
				layer.msg(result.msg);
				return ;
			}else{
				$('#haiAccountForm')[0].reset();
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
