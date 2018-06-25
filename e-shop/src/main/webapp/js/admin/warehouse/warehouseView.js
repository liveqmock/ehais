var haiWarehouseModal ;
var key_warehouseName = "";
var validform = null;
$(function(){
	haiWarehouseModal = $("#haiWarehouseModal").modal({backdrop: 'static', keyboard: false , show : false });
	
	$("#haiWarehouseAddDetail").click(function(){
		haiWarehouseAddDetail();
	});
	
	$("#haiWarehouseSearch").click(function(){
		key_warehouseName = $.trim($("#key_warehouseName").val());
		bsTable.bootstrapTable('refresh', { query : { warehouseName : key_warehouseName , page : 1} });
	});
    
	
	$("#haiWarehouseSaveSubmit").click(function(){$("#haiWarehouseForm").attr("action") == "add" ? haiWarehouseAddSubmit() : haiWarehouseEditSubmit() ;});

	//设置自动简码
	$("#warehouseName").setPinyin({"code":"warehouseCode"});
	
	validform = $("#haiWarehouseForm").validate({rules:{},messages:{}});
});

function haiWarehouseAddDetail(){
	haiWarehouseModal.modal("show");
	$("#haiWarehouseForm").attr("action","add");
	$('#haiWarehouseForm')[0].reset();
}


function haiWarehouseEditDetail(warehouseId){
	$("#haiWarehouseForm").attr("action","edit");
	layer.load(0, {shade: false});
	$.ajax({
		url : "haiWarehouseEditDetail",type:"post",dataType:"json",data:{warehouseId:warehouseId},
		success:function(result){
			layer.closeAll();
			haiWarehouseModal.modal("show");
			$.each(result.model,function(id,ele){
				$("#"+id).val(ele);
			});
		}
	});
}



function haiWarehouseAddSubmit(){
	
	if($("#warehouseCode").val() == undefined || $("#warehouseCode").val().length == 0){
		layer.msg("请输入简码");return ;
	}
	if($("#warehouseName").val() == undefined || $("#warehouseName").val().length == 0){
		layer.msg("请输入名称");return ;
	}

	if(!validform.form())return ;
	
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
					haiWarehouseModal.modal("hide");
					bsTable.bootstrapTable('refresh', { query : {  page : 1} });
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

	if(!validform.form())return ;
	
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
					haiWarehouseModal.modal("hide");
					bsTable.bootstrapTable('refresh', { query : {  page : 1} });
				});
			}
		}
	});
}



function haiWarehouseDelete(warehouseId){
	layer.confirm('您确定要删除此项吗？',{
		btn: ['确定删除','不删除'] //按钮
	}, function(){
		$.ajax({
			url : "haiWarehouseDelete",type:"post",dataType:"json",data:{warehouseId:warehouseId},
			success:function(result){
				layer.msg(result.msg);
				bsTable.bootstrapTable('refresh');
			}
		});
	}, function(){
		layer.closeAll();
	});
}

