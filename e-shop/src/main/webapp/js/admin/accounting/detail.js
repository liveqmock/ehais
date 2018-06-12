$(function(){

	$("#saveSubmit").click(function(){$("#haiAccountingForm").attr("action") == "add" ? haiAccountingAddSubmit() : haiAccountingEditSubmit() ;});

});


function haiAccountingAddSubmit(){
	
	if($("#accountingCode").val() == undefined || $("#accountingCode").val().length == 0){
		layer.msg("请输入简码");return ;
	}
	if($("#accountingName").val() == undefined || $("#accountingName").val().length == 0){
		layer.msg("请输入名称");return ;
	}

	
	
	$.ajax({
		url : "haiAccountingAddSubmit",
		type:"post",dataType:"json",data:$("#haiAccountingForm").serialize(),
		success:function(result){
			if(result.code != 1){
				layer.msg(result.msg);
				return ;
			}else{
				$('#haiAccountingForm')[0].reset();
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

function haiAccountingEditSubmit(){
	
	if($("#accountingCode").val() == undefined || $("#accountingCode").val().length == 0){
		layer.msg("请输入简码");return ;
	}
	if($("#accountingName").val() == undefined || $("#accountingName").val().length == 0){
		layer.msg("请输入名称");return ;
	}

	
	
	$.ajax({
		url : "haiAccountingEditSubmit",
		type:"post",dataType:"json",data:$("#haiAccountingForm").serialize(),
		success:function(result){
			if(result.code != 1){
				layer.msg(result.msg);
				return ;
			}else{
				$('#haiAccountingForm')[0].reset();
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
