$(function(){

	$("#saveSubmit").click(function(){$("#haiLabourForm").attr("action") == "add" ? haiLabourAddSubmit() : haiLabourEditSubmit() ;});

});


function haiLabourAddSubmit(){
	
	if($("#labourCode").val() == undefined || $("#labourCode").val().length == 0){
		layer.msg("请输入简码");return ;
	}
	if($("#labourName").val() == undefined || $("#labourName").val().length == 0){
		layer.msg("请输入名称");return ;
	}

	
	
	$.ajax({
		url : "haiLabourAddSubmit",
		type:"post",dataType:"json",data:$("#haiLabourForm").serialize(),
		success:function(result){
			if(result.code != 1){
				layer.msg(result.msg);
				return ;
			}else{
				$('#haiLabourForm')[0].reset();
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

function haiLabourEditSubmit(){
	
	if($("#labourCode").val() == undefined || $("#labourCode").val().length == 0){
		layer.msg("请输入简码");return ;
	}
	if($("#labourName").val() == undefined || $("#labourName").val().length == 0){
		layer.msg("请输入名称");return ;
	}

	
	
	$.ajax({
		url : "haiLabourEditSubmit",
		type:"post",dataType:"json",data:$("#haiLabourForm").serialize(),
		success:function(result){
			if(result.code != 1){
				layer.msg(result.msg);
				return ;
			}else{
				$('#haiLabourForm')[0].reset();
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
