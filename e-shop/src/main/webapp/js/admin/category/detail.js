$(function(){

	$("#saveSubmit").click(function(){$("#haiCategoryForm").attr("action") == "add" ? haiCategoryAddSubmit() : haiCategoryEditSubmit() ;});

});


function haiCategoryAddSubmit(){
	
	if($("#catCode").val() == undefined || $("#catCode").val().length == 0){
		layer.msg("请输入简码");return ;
	}
	if($("#catName").val() == undefined || $("#catName").val().length == 0){
		layer.msg("请输入名称");return ;
	}

	
	
	$.ajax({
		url : "haiCategoryAddSubmit",
		type:"post",dataType:"json",data:$("#haiCategoryForm").serialize(),
		success:function(result){
			if(result.code != 1){
				layer.msg(result.msg);
				return ;
			}else{
				$('#haiCategoryForm')[0].reset();
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

function haiCategoryEditSubmit(){
	
	if($("#catCode").val() == undefined || $("#catCode").val().length == 0){
		layer.msg("请输入简码");return ;
	}
	if($("#catName").val() == undefined || $("#catName").val().length == 0){
		layer.msg("请输入名称");return ;
	}

	
	
	$.ajax({
		url : "haiCategoryEditSubmit",
		type:"post",dataType:"json",data:$("#haiCategoryForm").serialize(),
		success:function(result){
			if(result.code != 1){
				layer.msg(result.msg);
				return ;
			}else{
				$('#haiCategoryForm')[0].reset();
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
