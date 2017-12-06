$(function(){

	$("#saveSubmit").click(function(){$("#myForm").attr("action") == "add" ? haiNavAddSubmit() : haiNavEditSubmit() ;});

});


function haiNavAddSubmit(){
	
	if($("#name").val() == undefined || $("#name").val().length == 0){
		layer.msg("请输入名称");return ;
	}
	if($("#isValid").val() == undefined || $("#isValid").val().length == 0){
		layer.msg("请输入生效");return ;
	}
	if($("#sort").val() == undefined || $("#sort").val().length == 0){
		layer.msg("请输入排序");return ;
	}
	if($("#opennew").val() == undefined || $("#opennew").val().length == 0){
		layer.msg("请输入新窗口");return ;
	}
	if($("#classify").val() == undefined || $("#classify").val().length == 0){
		layer.msg("请输入类型");return ;
	}

	
	
	$.ajax({
		url : "haiNavAddSubmit",
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

function haiNavEditSubmit(){
	
	if($("#name").val() == undefined || $("#name").val().length == 0){
		layer.msg("请输入名称");return ;
	}
	if($("#isValid").val() == undefined || $("#isValid").val().length == 0){
		layer.msg("请输入生效");return ;
	}
	if($("#sort").val() == undefined || $("#sort").val().length == 0){
		layer.msg("请输入排序");return ;
	}
	if($("#opennew").val() == undefined || $("#opennew").val().length == 0){
		layer.msg("请输入新窗口");return ;
	}
	if($("#classify").val() == undefined || $("#classify").val().length == 0){
		layer.msg("请输入类型");return ;
	}

	
	
	$.ajax({
		url : "haiNavEditSubmit",
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
