var haiBusinessTypeModal ;
var key_businessTypeName = "";
$(function(){
	haiBusinessTypeModal = $("#haiBusinessTypeModal").modal({ keyboard: false , show : false });
	
	$("#haiBusinessTypeAddDetail").click(function(){
		haiBusinessTypeAddDetail();
	});
	
	$("#haiBusinessTypeSearch").click(function(){
		key_businessTypeName = $.trim($("#key_businessTypeName").val());
		bsTable.bootstrapTable('refresh', { query : { businessTypeName : key_businessTypeName , page : 1} });
	});
    
	
	$("#haiBusinessTypeSaveSubmit").click(function(){$("#haiBusinessTypeForm").attr("action") == "add" ? haiBusinessTypeAddSubmit() : haiBusinessTypeEditSubmit() ;});

	//设置自动简码
	$("#businessTypeName").setPinyin({"code":"businessTypeCode"});
});

function haiBusinessTypeAddDetail(){
	haiBusinessTypeModal.modal("show");
	$("#haiBusinessTypeForm").attr("action","add");
	$('#haiBusinessTypeForm')[0].reset();
}


function haiBusinessTypeEditDetail(businessTypeId){
	$("#haiBusinessTypeForm").attr("action","edit");
	layer.load(0, {shade: false});
	$.ajax({
		url : "haiBusinessTypeEditDetail",type:"post",dataType:"json",data:{businessTypeId:businessTypeId},
		success:function(result){
			layer.closeAll();
			haiBusinessTypeModal.modal("show");
			$.each(result.model,function(id,ele){
				$("#"+id).val(ele);
			});
		}
	});
}



function haiBusinessTypeAddSubmit(){
	
	if($("#businessTypeCode").val() == undefined || $("#businessTypeCode").val().length == 0){
		layer.msg("请输入商业/往来单位简码");return ;
	}
	if($("#businessTypeName").val() == undefined || $("#businessTypeName").val().length == 0){
		layer.msg("请输入商业/往来单位名称");return ;
	}

	
	
	$.ajax({
		url : "haiBusinessTypeAddSubmit",
		type:"post",dataType:"json",data:$("#haiBusinessTypeForm").serialize(),
		success:function(result){
			if(result.code != 1){
				layer.msg(result.msg);
				return ;
			}else{
				$('#haiBusinessTypeForm')[0].reset();
				layer.confirm(result.msg, {
					btn: ['继续添加','返回列表'] //按钮
				}, function(){
				  //继续添加
					layer.closeAll();
				}, function(){
				  //返回列表
					layer.closeAll();
					haiBusinessTypeModal.modal("hide");
					bsTable.bootstrapTable('refresh', { query : {  page : 1} });
				});
			}
		}
	});
}

function haiBusinessTypeEditSubmit(){
	
	if($("#businessTypeCode").val() == undefined || $("#businessTypeCode").val().length == 0){
		layer.msg("请输入商业/往来单位简码");return ;
	}
	if($("#businessTypeName").val() == undefined || $("#businessTypeName").val().length == 0){
		layer.msg("请输入商业/往来单位名称");return ;
	}

	
	
	$.ajax({
		url : "haiBusinessTypeEditSubmit",
		type:"post",dataType:"json",data:$("#haiBusinessTypeForm").serialize(),
		success:function(result){
			if(result.code != 1){
				layer.msg(result.msg);
				return ;
			}else{
				$('#haiBusinessTypeForm')[0].reset();
				layer.alert(result.msg, {
					skin: 'layui-layer-molv' //样式类名
					,closeBtn: 0
				}, function(){
					layer.closeAll();
					haiBusinessTypeModal.modal("hide");
					bsTable.bootstrapTable('refresh', { query : {  page : 1} });
				});
			}
		}
	});
}



function haiBusinessTypeDelete(businessTypeId){
	layer.confirm('您确定要删除此项吗？',{
		btn: ['确定删除','不删除'] //按钮
	}, function(){
		$.ajax({
			url : "haiBusinessTypeDelete",type:"post",dataType:"json",data:{businessTypeId:businessTypeId},
			success:function(result){
				layer.msg(result.msg);
				bsTable.bootstrapTable('refresh');
			}
		});
	}, function(){
		layer.closeAll();
	});
}

