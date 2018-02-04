$(function(){

	$("#saveSubmit").click(function(){$("#myForm").attr("action") == "add" ? haiBrandAddSubmit() : haiBrandEditSubmit() ;});

});


function haiBrandAddSubmit(){
	
	if($("#brandName").val() == undefined || $("#brandName").val().length == 0){
		layer.msg("请输入品牌名称");return ;
	}
	if($("#brandLogo").val() == undefined || $("#brandLogo").val().length == 0){
		layer.msg("请输入LOGO");return ;
	}
	if($("#brandDesc").val() == undefined || $("#brandDesc").val().length == 0){
		layer.msg("请输入品牌描述");return ;
	}
	if($("#siteUrl").val() == undefined || $("#siteUrl").val().length == 0){
		layer.msg("请输入描述地址");return ;
	}
	if($("#sortOrder").val() == undefined || $("#sortOrder").val().length == 0){
		layer.msg("请输入排序");return ;
	}
	if($("#isShow").val() == undefined || $("#isShow").val().length == 0){
		layer.msg("请输入是否显示");return ;
	}

	
	
	$.ajax({
		url : "haiBrandAddSubmit",
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

function haiBrandEditSubmit(){
	
	if($("#brandName").val() == undefined || $("#brandName").val().length == 0){
		layer.msg("请输入品牌名称");return ;
	}
	if($("#brandLogo").val() == undefined || $("#brandLogo").val().length == 0){
		layer.msg("请输入LOGO");return ;
	}
	if($("#brandDesc").val() == undefined || $("#brandDesc").val().length == 0){
		layer.msg("请输入品牌描述");return ;
	}
	if($("#siteUrl").val() == undefined || $("#siteUrl").val().length == 0){
		layer.msg("请输入描述地址");return ;
	}
	if($("#sortOrder").val() == undefined || $("#sortOrder").val().length == 0){
		layer.msg("请输入排序");return ;
	}
	if($("#isShow").val() == undefined || $("#isShow").val().length == 0){
		layer.msg("请输入是否显示");return ;
	}

	
	
	$.ajax({
		url : "haiBrandEditSubmit",
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
