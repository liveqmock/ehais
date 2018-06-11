$(function(){

	$("#saveSubmit").click(function(){$("#haiBusinessTypeForm").attr("action") == "add" ? haiBusinessTypeAddSubmit() : haiBusinessTypeEditSubmit() ;});

});


function haiBusinessTypeAddSubmit(){
	
	if($("#businessTypeCode").val() == undefined || $("#businessTypeCode").val().length == 0){
		layer.msg("请输入商业/往来单位简码");return ;
	}
	if($("#businessTypeName").val() == undefined || $("#businessTypeName").val().length == 0){
		layer.msg("请输入商业/往来单位名称");return ;
	}

	
	
	$.ajax({
		url : "haiBusinessTypeAddSubmit",
		type:"post",dataType:"json",data:$("#haiBusinessTypeForm").serialize(),
		success:function(result){
			if(result.code != 1){
				layer.msg(result.msg);
				return ;
			}else{
				$('#haiBusinessTypeForm')[0].reset();
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

function haiBusinessTypeEditSubmit(){
	
	if($("#businessTypeCode").val() == undefined || $("#businessTypeCode").val().length == 0){
		layer.msg("请输入商业/往来单位简码");return ;
	}
	if($("#businessTypeName").val() == undefined || $("#businessTypeName").val().length == 0){
		layer.msg("请输入商业/往来单位名称");return ;
	}

	
	
	$.ajax({
		url : "haiBusinessTypeEditSubmit",
		type:"post",dataType:"json",data:$("#haiBusinessTypeForm").serialize(),
		success:function(result){
			if(result.code != 1){
				layer.msg(result.msg);
				return ;
			}else{
				$('#haiBusinessTypeForm')[0].reset();
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
