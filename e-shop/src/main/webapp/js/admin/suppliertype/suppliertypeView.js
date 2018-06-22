var haiSupplierTypeModal ;
var key_businessTypeName = "";
var validform = null;
$(function(){
	haiSupplierTypeModal = $("#haiSupplierTypeModal").modal({backdrop: 'static', keyboard: false , show : false });
	
	$("#haiSupplierTypeAddDetail").click(function(){
		haiSupplierTypeAddDetail();
	});
	
	$("#haiSupplierTypeSearch").click(function(){
		key_businessTypeName = $.trim($("#key_businessTypeName").val());
		bsTable.bootstrapTable('refresh', { query : { businessTypeName : key_businessTypeName , page : 1} });
	});
    
	
	$("#haiSupplierTypeSaveSubmit").click(function(){$("#haiSupplierTypeForm").attr("action") == "add" ? haiSupplierTypeAddSubmit() : haiSupplierTypeEditSubmit() ;});

	//设置自动简码
	$("#businessTypeName").setPinyin({"code":"businessTypeCode"});
});

function haiSupplierTypeAddDetail(){
	haiSupplierTypeModal.modal("show");
	$("#haiSupplierTypeForm").attr("action","add");
	$('#haiSupplierTypeForm')[0].reset();
}


function haiSupplierTypeEditDetail(businessTypeId){
	$("#haiSupplierTypeForm").attr("action","edit");
	layer.load(0, {shade: false});
	$.ajax({
		url : "haiSupplierTypeEditDetail",type:"post",dataType:"json",data:{businessTypeId:businessTypeId},
		success:function(result){
			layer.closeAll();
			haiSupplierTypeModal.modal("show");
			$.each(result.model,function(id,ele){
				$("#"+id).val(ele);
			});
		}
	});
}



function haiSupplierTypeAddSubmit(){
	
	if($("#businessTypeCode").val() == undefined || $("#businessTypeCode").val().length == 0){
		layer.msg("请输入商业/往来单位简码");return ;
	}
	if($("#businessTypeName").val() == undefined || $("#businessTypeName").val().length == 0){
		layer.msg("请输入商业/往来单位名称");return ;
	}


	if(!validform.form())return ;
	
	
	$.ajax({
		url : "haiSupplierTypeAddSubmit",
		type:"post",dataType:"json",data:$("#haiSupplierTypeForm").serialize(),
		success:function(result){
			if(result.code != 1){
				layer.msg(result.msg);
				return ;
			}else{
				$('#haiSupplierTypeForm')[0].reset();
				layer.confirm(result.msg, {
					btn: ['继续添加','返回列表'] //按钮
				}, function(){
				  //继续添加
					layer.closeAll();
				}, function(){
				  //返回列表
					layer.closeAll();
					haiSupplierTypeModal.modal("hide");
					bsTable.bootstrapTable('refresh', { query : {  page : 1} });
				});
			}
		}
	});
}

function haiSupplierTypeEditSubmit(){
	
	if($("#businessTypeCode").val() == undefined || $("#businessTypeCode").val().length == 0){
		layer.msg("请输入商业/往来单位简码");return ;
	}
	if($("#businessTypeName").val() == undefined || $("#businessTypeName").val().length == 0){
		layer.msg("请输入商业/往来单位名称");return ;
	}

	if(!validform.form())return ;
	
	$.ajax({
		url : "haiSupplierTypeEditSubmit",
		type:"post",dataType:"json",data:$("#haiSupplierTypeForm").serialize(),
		success:function(result){
			if(result.code != 1){
				layer.msg(result.msg);
				return ;
			}else{
				$('#haiSupplierTypeForm')[0].reset();
				layer.alert(result.msg, {
					skin: 'layui-layer-molv' //样式类名
					,closeBtn: 0
				}, function(){
					layer.closeAll();
					haiSupplierTypeModal.modal("hide");
					bsTable.bootstrapTable('refresh', { query : {  page : 1} });
				});
			}
		}
	});
}



function haiSupplierTypeDelete(businessTypeId){
	layer.confirm('您确定要删除此项吗？',{
		btn: ['确定删除','不删除'] //按钮
	}, function(){
		$.ajax({
			url : "haiSupplierTypeDelete",type:"post",dataType:"json",data:{businessTypeId:businessTypeId},
			success:function(result){
				layer.msg(result.msg);
				bsTable.bootstrapTable('refresh');
			}
		});
	}, function(){
		layer.closeAll();
	});
}

