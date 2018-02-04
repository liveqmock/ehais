$(function(){

	$("#saveSubmit").click(function(){$("#myForm").attr("action") == "add" ? thinkRoleAddSubmit() : thinkRoleEditSubmit() ;});

});


function thinkRoleAddSubmit(){
	
	if($("#name").val() == undefined || $("#name").val().length == 0){
		layer.msg("请输入角色名称");return ;
	}
	if($("#parentId").val() == undefined || $("#parentId").val().length == 0){
		layer.msg("请输入上级编号");return ;
	}

	
	
	$.ajax({
		url : "thinkRoleAddSubmit",
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

function thinkRoleEditSubmit(){
	
	if($("#name").val() == undefined || $("#name").val().length == 0){
		layer.msg("请输入角色名称");return ;
	}
	if($("#parentId").val() == undefined || $("#parentId").val().length == 0){
		layer.msg("请输入上级编号");return ;
	}

	
	
	$.ajax({
		url : "thinkRoleEditSubmit",
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
