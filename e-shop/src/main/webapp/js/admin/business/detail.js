$(function(){

	$("#saveSubmit").click(function(){$("#haiBusinessForm").attr("action") == "add" ? haiBusinessAddSubmit() : haiBusinessEditSubmit() ;});

});


function haiBusinessAddSubmit(){
	
	if($("#businessName").val() == undefined || $("#businessName").val().length == 0){
		layer.msg("请输入企业名称");return ;
	}

	
	
	$.ajax({
		url : "haiBusinessAddSubmit",
		type:"post",dataType:"json",data:$("#haiBusinessForm").serialize(),
		success:function(result){
			if(result.code != 1){
				layer.msg(result.msg);
				return ;
			}else{
				$('#haiBusinessForm')[0].reset();
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

function haiBusinessEditSubmit(){
	
	if($("#businessName").val() == undefined || $("#businessName").val().length == 0){
		layer.msg("请输入企业名称");return ;
	}

	
	
	$.ajax({
		url : "haiBusinessEditSubmit",
		type:"post",dataType:"json",data:$("#haiBusinessForm").serialize(),
		success:function(result){
			if(result.code != 1){
				layer.msg(result.msg);
				return ;
			}else{
				$('#haiBusinessForm')[0].reset();
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
