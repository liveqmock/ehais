var haiBusinessModal ;
var key_businessName = "";
var validform = null;
$(function(){
	haiBusinessModal = $("#haiBusinessModal").modal({backdrop: 'static', keyboard: false , show : false });
	
	$("#haiBusinessAddDetail").click(function(){
		haiBusinessAddDetail();
	});
	
	$("#haiBusinessSearch").click(function(){
		key_businessName = $.trim($("#key_businessName").val());
		bsTable.bootstrapTable('refresh', { query : { businessName : key_businessName , page : 1} });
	});
    
	
	$("#haiBusinessSaveSubmit").click(function(){$("#haiBusinessForm").attr("action") == "add" ? haiBusinessAddSubmit() : haiBusinessEditSubmit() ;});

	//设置自动简码
	$("#businessName").setPinyin({"code":"businessCode"});
});

function haiBusinessAddDetail(){
	haiBusinessModal.modal("show");
	$("#haiBusinessForm").attr("action","add");
	$('#haiBusinessForm')[0].reset();
}


function haiBusinessEditDetail(businessId){
	$("#haiBusinessForm").attr("action","edit");
	layer.load(0, {shade: false});
	$.ajax({
		url : "haiBusinessEditDetail",type:"post",dataType:"json",data:{businessId:businessId},
		success:function(result){
			layer.closeAll();
			haiBusinessModal.modal("show");
			$.each(result.model,function(id,ele){
				$("#"+id).val(ele);
			});
		}
	});
}



function haiBusinessAddSubmit(){
	
	if($("#businessName").val() == undefined || $("#businessName").val().length == 0){
		layer.msg("请输入企业名称");return ;
	}

	if(!validform.form())return ;
	
	$.ajax({
		url : "haiBusinessAddSubmit",
		type:"post",dataType:"json",data:$("#haiBusinessForm").serialize(),
		success:function(result){
			if(result.code != 1){
				layer.msg(result.msg);
				return ;
			}else{
				$('#haiBusinessForm')[0].reset();
				layer.confirm(result.msg, {
					btn: ['继续添加','返回列表'] //按钮
				}, function(){
				  //继续添加
					layer.closeAll();
				}, function(){
				  //返回列表
					layer.closeAll();
					haiBusinessModal.modal("hide");
					bsTable.bootstrapTable('refresh', { query : {  page : 1} });
				});
			}
		}
	});
}

function haiBusinessEditSubmit(){
	
	if($("#businessName").val() == undefined || $("#businessName").val().length == 0){
		layer.msg("请输入企业名称");return ;
	}

	if(!validform.form())return ;
	
	$.ajax({
		url : "haiBusinessEditSubmit",
		type:"post",dataType:"json",data:$("#haiBusinessForm").serialize(),
		success:function(result){
			if(result.code != 1){
				layer.msg(result.msg);
				return ;
			}else{
				$('#haiBusinessForm')[0].reset();
				layer.alert(result.msg, {
					skin: 'layui-layer-molv' //样式类名
					,closeBtn: 0
				}, function(){
					layer.closeAll();
					haiBusinessModal.modal("hide");
					bsTable.bootstrapTable('refresh', { query : {  page : 1} });
				});
			}
		}
	});
}



function haiBusinessDelete(businessId){
	layer.confirm('您确定要删除此项吗？',{
		btn: ['确定删除','不删除'] //按钮
	}, function(){
		$.ajax({
			url : "haiBusinessDelete",type:"post",dataType:"json",data:{businessId:businessId},
			success:function(result){
				layer.msg(result.msg);
				bsTable.bootstrapTable('refresh');
			}
		});
	}, function(){
		layer.closeAll();
	});
}

