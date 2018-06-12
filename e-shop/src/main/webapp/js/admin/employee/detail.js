$(function(){

	$("#saveSubmit").click(function(){$("#haiEmployeeForm").attr("action") == "add" ? haiEmployeeAddSubmit() : haiEmployeeEditSubmit() ;});

});


function haiEmployeeAddSubmit(){
	
	if($("#employeeCode").val() == undefined || $("#employeeCode").val().length == 0){
		layer.msg("请输入简码");return ;
	}
	if($("#employeeName").val() == undefined || $("#employeeName").val().length == 0){
		layer.msg("请输入名称");return ;
	}

	
	
	$.ajax({
		url : "haiEmployeeAddSubmit",
		type:"post",dataType:"json",data:$("#haiEmployeeForm").serialize(),
		success:function(result){
			if(result.code != 1){
				layer.msg(result.msg);
				return ;
			}else{
				$('#haiEmployeeForm')[0].reset();
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

function haiEmployeeEditSubmit(){
	
	if($("#employeeCode").val() == undefined || $("#employeeCode").val().length == 0){
		layer.msg("请输入简码");return ;
	}
	if($("#employeeName").val() == undefined || $("#employeeName").val().length == 0){
		layer.msg("请输入名称");return ;
	}

	
	
	$.ajax({
		url : "haiEmployeeEditSubmit",
		type:"post",dataType:"json",data:$("#haiEmployeeForm").serialize(),
		success:function(result){
			if(result.code != 1){
				layer.msg(result.msg);
				return ;
			}else{
				$('#haiEmployeeForm')[0].reset();
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
