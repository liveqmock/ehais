var haiCustomerTypeModal ;
var key_businessTypeName = "";
var validform = null;
$(function(){
	haiCustomerTypeModal = $("#haiCustomerTypeModal").modal({backdrop: 'static', keyboard: false , show : false });
	
	$("#haiCustomerTypeAddDetail").click(function(){
		haiCustomerTypeAddDetail();
	});
	
	$("#haiCustomerTypeSearch").click(function(){
		key_businessTypeName = $.trim($("#key_businessTypeName").val());
		bsTable.bootstrapTable('refresh', { query : { businessTypeName : key_businessTypeName , page : 1} });
	});
    
	
	$("#haiCustomerTypeSaveSubmit").click(function(){$("#haiCustomerTypeForm").attr("action") == "add" ? haiCustomerTypeAddSubmit() : haiCustomerTypeEditSubmit() ;});

	//设置自动简码
	$("#businessTypeName").setPinyin({"code":"businessTypeCode"});
});

function haiCustomerTypeAddDetail(){
	haiCustomerTypeModal.modal("show");
	$("#haiCustomerTypeForm").attr("action","add");
	$('#haiCustomerTypeForm')[0].reset();
}


function haiCustomerTypeEditDetail(businessTypeId){
	$("#haiCustomerTypeForm").attr("action","edit");
	layer.load(0, {shade: false});
	$.ajax({
		url : "haiCustomerTypeEditDetail",type:"post",dataType:"json",data:{businessTypeId:businessTypeId},
		success:function(result){
			layer.closeAll();
			haiCustomerTypeModal.modal("show");
			$.each(result.model,function(id,ele){
				$("#"+id).val(ele);
			});
		}
	});
}



function haiCustomerTypeAddSubmit(){
	
	if($("#businessTypeCode").val() == undefined || $("#businessTypeCode").val().length == 0){
		layer.msg("请输入简码");return ;
	}
	if($("#businessTypeName").val() == undefined || $("#businessTypeName").val().length == 0){
		layer.msg("请输入名称");return ;
	}

	if(!validform.form())return ;
	
	$.ajax({
		url : "haiCustomerTypeAddSubmit",
		type:"post",dataType:"json",data:$("#haiCustomerTypeForm").serialize(),
		success:function(result){
			if(result.code != 1){
				layer.msg(result.msg);
				return ;
			}else{
				$('#haiCustomerTypeForm')[0].reset();
				layer.confirm(result.msg, {
					btn: ['继续添加','返回列表'] //按钮
				}, function(){
				  //继续添加
					layer.closeAll();
				}, function(){
				  //返回列表
					layer.closeAll();
					haiCustomerTypeModal.modal("hide");
					bsTable.bootstrapTable('refresh', { query : {  page : 1} });
				});
			}
		}
	});
}

function haiCustomerTypeEditSubmit(){
	
	if($("#businessTypeCode").val() == undefined || $("#businessTypeCode").val().length == 0){
		layer.msg("请输入简码");return ;
	}
	if($("#businessTypeName").val() == undefined || $("#businessTypeName").val().length == 0){
		layer.msg("请输入名称");return ;
	}

	if(!validform.form())return ;
	
	$.ajax({
		url : "haiCustomerTypeEditSubmit",
		type:"post",dataType:"json",data:$("#haiCustomerTypeForm").serialize(),
		success:function(result){
			if(result.code != 1){
				layer.msg(result.msg);
				return ;
			}else{
				$('#haiCustomerTypeForm')[0].reset();
				layer.alert(result.msg, {
					skin: 'layui-layer-molv' //样式类名
					,closeBtn: 0
				}, function(){
					layer.closeAll();
					haiCustomerTypeModal.modal("hide");
					bsTable.bootstrapTable('refresh', { query : {  page : 1} });
				});
			}
		}
	});
}



function haiCustomerTypeDelete(businessTypeId){
	layer.confirm('您确定要删除此项吗？',{
		btn: ['确定删除','不删除'] //按钮
	}, function(){
		$.ajax({
			url : "haiCustomerTypeDelete",type:"post",dataType:"json",data:{businessTypeId:businessTypeId},
			success:function(result){
				layer.msg(result.msg);
				bsTable.bootstrapTable('refresh');
			}
		});
	}, function(){
		layer.closeAll();
	});
}

