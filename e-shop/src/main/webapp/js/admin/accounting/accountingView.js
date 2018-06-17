var haiAccountingModal ;
var key_accountingName = "";
$(function(){
	haiAccountingModal = $("#haiAccountingModal").modal({ keyboard: false , show : false });
	
	$("#haiAccountingAddDetail").click(function(){
		haiAccountingAddDetail();
	});
	
	$("#haiAccountingSearch").click(function(){
		key_accountingName = $.trim($("#key_accountingName").val());
		bsTable.bootstrapTable('refresh', { query : { accountingName : key_accountingName , page : 1} });
	});
    
	
	$("#haiAccountingSaveSubmit").click(function(){$("#haiAccountingForm").attr("action") == "add" ? haiAccountingAddSubmit() : haiAccountingEditSubmit() ;});

	//设置自动简码
	$("#accountingName").setPinyin({"code":"accountingCode"});
});

function haiAccountingAddDetail(){
	haiAccountingModal.modal("show");
	$("#haiAccountingForm").attr("action","add");
	$('#haiAccountingForm')[0].reset();
}


function haiAccountingEditDetail(accountingId){
	$("#haiAccountingForm").attr("action","edit");
	layer.load(0, {shade: false});
	$.ajax({
		url : "haiAccountingEditDetail",type:"post",dataType:"json",data:{accountingId:accountingId},
		success:function(result){
			layer.closeAll();
			haiAccountingModal.modal("show");
			$.each(result.model,function(id,ele){
				$("#"+id).val(ele);
			});
		}
	});
}



function haiAccountingAddSubmit(){
	
	if($("#accountingCode").val() == undefined || $("#accountingCode").val().length == 0){
		layer.msg("请输入简码");return ;
	}
	if($("#accountingName").val() == undefined || $("#accountingName").val().length == 0){
		layer.msg("请输入名称");return ;
	}

	
	
	$.ajax({
		url : "haiAccountingAddSubmit",
		type:"post",dataType:"json",data:$("#haiAccountingForm").serialize(),
		success:function(result){
			if(result.code != 1){
				layer.msg(result.msg);
				return ;
			}else{
				$('#haiAccountingForm')[0].reset();
				layer.confirm(result.msg, {
					btn: ['继续添加','返回列表'] //按钮
				}, function(){
				  //继续添加
					layer.closeAll();
				}, function(){
				  //返回列表
					layer.closeAll();
					haiAccountingModal.modal("hide");
					bsTable.bootstrapTable('refresh', { query : {  page : 1} });
				});
			}
		}
	});
}

function haiAccountingEditSubmit(){
	
	if($("#accountingCode").val() == undefined || $("#accountingCode").val().length == 0){
		layer.msg("请输入简码");return ;
	}
	if($("#accountingName").val() == undefined || $("#accountingName").val().length == 0){
		layer.msg("请输入名称");return ;
	}

	
	
	$.ajax({
		url : "haiAccountingEditSubmit",
		type:"post",dataType:"json",data:$("#haiAccountingForm").serialize(),
		success:function(result){
			if(result.code != 1){
				layer.msg(result.msg);
				return ;
			}else{
				$('#haiAccountingForm')[0].reset();
				layer.alert(result.msg, {
					skin: 'layui-layer-molv' //样式类名
					,closeBtn: 0
				}, function(){
					layer.closeAll();
					haiAccountingModal.modal("hide");
					bsTable.bootstrapTable('refresh', { query : {  page : 1} });
				});
			}
		}
	});
}



function haiAccountingDelete(accountingId){
	layer.confirm('您确定要删除此项吗？',{
		btn: ['确定删除','不删除'] //按钮
	}, function(){
		$.ajax({
			url : "haiAccountingDelete",type:"post",dataType:"json",data:{accountingId:accountingId},
			success:function(result){
				layer.msg(result.msg);
				bsTable.bootstrapTable('refresh');
			}
		});
	}, function(){
		layer.closeAll();
	});
}

