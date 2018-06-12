$(function(){

	$("#saveSubmit").click(function(){$("#haiWarehouseForm").attr("action") == "add" ? haiWarehouseAddSubmit() : haiWarehouseEditSubmit() ;});

});


function haiWarehouseAddSubmit(){
	
	if($("#warehouseCode").val() == undefined || $("#warehouseCode").val().length == 0){
		layer.msg("请输入简码");return ;
	}
	if($("#warehouseName").val() == undefined || $("#warehouseName").val().length == 0){
		layer.msg("请输入名称");return ;
	}

	
	
	$.ajax({
		url : "haiWarehouseAddSubmit",
		type:"post",dataType:"json",data:$("#haiWarehouseForm").serialize(),
		success:function(result){
			if(result.code != 1){
				layer.msg(result.msg);
				return ;
			}else{
				$('#haiWarehouseForm')[0].reset();
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

function haiWarehouseEditSubmit(){
	
	if($("#warehouseCode").val() == undefined || $("#warehouseCode").val().length == 0){
		layer.msg("请输入简码");return ;
	}
	if($("#warehouseName").val() == undefined || $("#warehouseName").val().length == 0){
		layer.msg("请输入名称");return ;
	}

	
	
	$.ajax({
		url : "haiWarehouseEditSubmit",
		type:"post",dataType:"json",data:$("#haiWarehouseForm").serialize(),
		success:function(result){
			if(result.code != 1){
				layer.msg(result.msg);
				return ;
			}else{
				$('#haiWarehouseForm')[0].reset();
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
