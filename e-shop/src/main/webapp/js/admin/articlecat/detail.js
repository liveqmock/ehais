$(function(){

	$("#saveSubmit").click(function(){$("#haiArticleCatForm").attr("action") == "add" ? haiArticleCatAddSubmit() : haiArticleCatEditSubmit() ;});

});


function haiArticleCatAddSubmit(){
	
	if($("#catName").val() == undefined || $("#catName").val().length == 0){
		layer.msg("请输入cat_name");return ;
	}

	
	
	$.ajax({
		url : "haiArticleCatAddSubmit",
		type:"post",dataType:"json",data:$("#haiArticleCatForm").serialize(),
		success:function(result){
			if(result.code != 1){
				layer.msg(result.msg);
				return ;
			}else{
				$('#haiArticleCatForm')[0].reset();
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

function haiArticleCatEditSubmit(){
	
	if($("#catName").val() == undefined || $("#catName").val().length == 0){
		layer.msg("请输入cat_name");return ;
	}

	
	
	$.ajax({
		url : "haiArticleCatEditSubmit",
		type:"post",dataType:"json",data:$("#haiArticleCatForm").serialize(),
		success:function(result){
			if(result.code != 1){
				layer.msg(result.msg);
				return ;
			}else{
				$('#haiArticleCatForm')[0].reset();
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
