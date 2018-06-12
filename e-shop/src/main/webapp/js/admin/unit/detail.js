$(function(){

	$("#saveSubmit").click(function(){$("#haiUnitForm").attr("action") == "add" ? haiUnitAddSubmit() : haiUnitEditSubmit() ;});

});


function haiUnitAddSubmit(){
	
	if($("#unitCode").val() == undefined || $("#unitCode").val().length == 0){
		layer.msg("请输入简码");return ;
	}
	if($("#unitName").val() == undefined || $("#unitName").val().length == 0){
		layer.msg("请输入名称");return ;
	}
	if($("#storeId").val() == undefined || $("#storeId").val().length == 0){
		layer.msg("请输入store_id");return ;
	}

	
	
	$.ajax({
		url : "haiUnitAddSubmit",
		type:"post",dataType:"json",data:$("#haiUnitForm").serialize(),
		success:function(result){
			if(result.code != 1){
				layer.msg(result.msg);
				return ;
			}else{
				$('#haiUnitForm')[0].reset();
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

function haiUnitEditSubmit(){
	
	if($("#unitCode").val() == undefined || $("#unitCode").val().length == 0){
		layer.msg("请输入简码");return ;
	}
	if($("#unitName").val() == undefined || $("#unitName").val().length == 0){
		layer.msg("请输入名称");return ;
	}
	if($("#storeId").val() == undefined || $("#storeId").val().length == 0){
		layer.msg("请输入store_id");return ;
	}

	
	
	$.ajax({
		url : "haiUnitEditSubmit",
		type:"post",dataType:"json",data:$("#haiUnitForm").serialize(),
		success:function(result){
			if(result.code != 1){
				layer.msg(result.msg);
				return ;
			}else{
				$('#haiUnitForm')[0].reset();
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
