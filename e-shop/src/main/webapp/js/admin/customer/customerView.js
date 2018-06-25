var haiCustomerModal ;
var key_businessName = "";
var validform = null;
$(function(){
	haiCustomerModal = $("#haiCustomerModal").modal({backdrop: 'static', keyboard: false , show : false });
	
	$("#haiCustomerAddDetail").click(function(){
		haiCustomerAddDetail();
	});
	
	$("#haiCustomerSearch").click(function(){
		key_businessName = $.trim($("#key_businessName").val());
		bsTable.bootstrapTable('refresh', { query : { businessName : key_businessName , page : 1} });
	});
    
	
	$("#haiCustomerSaveSubmit").click(function(){$("#haiCustomerForm").attr("action") == "add" ? haiCustomerAddSubmit() : haiCustomerEditSubmit() ;});

	//设置自动简码
	$("#businessName").setPinyin({"code":"businessCode"});
	
	validform = $("#haiCustomerForm").validate({rules:{},messages:{}});
});

function haiCustomerAddDetail(){
	haiCustomerModal.modal("show");
	$("#haiCustomerForm").attr("action","add");
	$('#haiCustomerForm')[0].reset();
}


function haiCustomerEditDetail(businessId){
	$("#haiCustomerForm").attr("action","edit");
	layer.load(0, {shade: false});
	$.ajax({
		url : "haiCustomerEditDetail",type:"post",dataType:"json",data:{businessId:businessId},
		success:function(result){
			layer.closeAll();
			haiCustomerModal.modal("show");
			$.each(result.model,function(id,ele){
				$("#"+id).val(ele);
			});
		}
	});
}



function haiCustomerAddSubmit(){
	
	if($("#businessName").val() == undefined || $("#businessName").val().length == 0){
		layer.msg("请输入企业名称");return ;
	}

	if(!validform.form())return ;
	
	$.ajax({
		url : "haiCustomerAddSubmit",
		type:"post",dataType:"json",data:$("#haiCustomerForm").serialize(),
		success:function(result){
			if(result.code != 1){
				layer.msg(result.msg);
				return ;
			}else{
				$('#haiCustomerForm')[0].reset();
				layer.confirm(result.msg, {
					btn: ['继续添加','返回列表'] //按钮
				}, function(){
				  //继续添加
					layer.closeAll();
				}, function(){
				  //返回列表
					layer.closeAll();
					haiCustomerModal.modal("hide");
					bsTable.bootstrapTable('refresh', { query : {  page : 1} });
				});
			}
		}
	});
}

function haiCustomerEditSubmit(){
	
	if($("#businessName").val() == undefined || $("#businessName").val().length == 0){
		layer.msg("请输入企业名称");return ;
	}

	if(!validform.form())return ;
	
	$.ajax({
		url : "haiCustomerEditSubmit",
		type:"post",dataType:"json",data:$("#haiCustomerForm").serialize(),
		success:function(result){
			if(result.code != 1){
				layer.msg(result.msg);
				return ;
			}else{
				$('#haiCustomerForm')[0].reset();
				layer.alert(result.msg, {
					skin: 'layui-layer-molv' //样式类名
					,closeBtn: 0
				}, function(){
					layer.closeAll();
					haiCustomerModal.modal("hide");
					bsTable.bootstrapTable('refresh', { query : {  page : 1} });
				});
			}
		}
	});
}



function haiCustomerDelete(businessId){
	layer.confirm('您确定要删除此项吗？',{
		btn: ['确定删除','不删除'] //按钮
	}, function(){
		$.ajax({
			url : "haiCustomerDelete",type:"post",dataType:"json",data:{businessId:businessId},
			success:function(result){
				layer.msg(result.msg);
				bsTable.bootstrapTable('refresh');
			}
		});
	}, function(){
		layer.closeAll();
	});
}

