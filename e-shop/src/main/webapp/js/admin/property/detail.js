$(function(){

	$("#saveSubmit").click(function(){$("#haiPropertyForm").attr("action") == "add" ? haiPropertyAddSubmit() : haiPropertyEditSubmit() ;});

});


function haiPropertyAddSubmit(){
	
	if($("#propertyCode").val() == undefined || $("#propertyCode").val().length == 0){
		layer.msg("请输入简码");return ;
	}
	if($("#propertyName").val() == undefined || $("#propertyName").val().length == 0){
		layer.msg("请输入名称");return ;
	}

	
	
	$.ajax({
		url : "haiPropertyAddSubmit",
		type:"post",dataType:"json",data:$("#haiPropertyForm").serialize(),
		success:function(result){
			if(result.code != 1){
				layer.msg(result.msg);
				return ;
			}else{
				$('#haiPropertyForm')[0].reset();
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

function haiPropertyEditSubmit(){
	
	if($("#propertyCode").val() == undefined || $("#propertyCode").val().length == 0){
		layer.msg("请输入简码");return ;
	}
	if($("#propertyName").val() == undefined || $("#propertyName").val().length == 0){
		layer.msg("请输入名称");return ;
	}

	
	
	$.ajax({
		url : "haiPropertyEditSubmit",
		type:"post",dataType:"json",data:$("#haiPropertyForm").serialize(),
		success:function(result){
			if(result.code != 1){
				layer.msg(result.msg);
				return ;
			}else{
				$('#haiPropertyForm')[0].reset();
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
