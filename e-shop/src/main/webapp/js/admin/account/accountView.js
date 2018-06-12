var haiAccountModal ;
var key_accountName = "";
$(function(){
	haiAccountModal = $("#haiAccountModal").modal({ keyboard: false , show : false });
	
	$("#haiAccountAddDetail").click(function(){
		haiAccountAddDetail();
	});
	
	$("#haiAccountSearch").click(function(){
		key_accountName = $.trim($("#key_accountName").val());
		bsTable.bootstrapTable('refresh', { query : { accountName : key_accountName , page : 1} });
	});
    
	
	$("#haiAccountSaveSubmit").click(function(){$("#haiAccountForm").attr("action") == "add" ? haiAccountAddSubmit() : haiAccountEditSubmit() ;});

	
});

function haiAccountAddDetail(){
	haiAccountModal.modal("show");
	$("#haiAccountForm").attr("action","add");
	$('#haiAccountForm')[0].reset();
}


function haiAccountEditDetail(accountId){
	$("#haiAccountForm").attr("action","edit");
	layer.load(0, {shade: false});
	$.ajax({
		url : "haiAccountEditDetail",type:"post",dataType:"json",data:{accountId:accountId},
		success:function(result){
			layer.closeAll();
			haiAccountModal.modal("show");
			$.each(result.model,function(id,ele){
				$("#"+id).val(ele);
			});
		}
	});
}



function haiAccountAddSubmit(){
	
	if($("#accountCode").val() == undefined || $("#accountCode").val().length == 0){
		layer.msg("请输入简码");return ;
	}
	if($("#accountName").val() == undefined || $("#accountName").val().length == 0){
		layer.msg("请输入名称");return ;
	}

	
	
	$.ajax({
		url : "haiAccountAddSubmit",
		type:"post",dataType:"json",data:$("#haiAccountForm").serialize(),
		success:function(result){
			if(result.code != 1){
				layer.msg(result.msg);
				return ;
			}else{
				$('#haiAccountForm')[0].reset();
				layer.confirm(result.msg, {
					btn: ['继续添加','返回列表'] //按钮
				}, function(){
				  //继续添加
					layer.closeAll();
				}, function(){
				  //返回列表
					layer.closeAll();
					haiAccountModal.modal("hide");
					bsTable.bootstrapTable('refresh', { query : {  page : 1} });
				});
			}
		}
	});
}

function haiAccountEditSubmit(){
	
	if($("#accountCode").val() == undefined || $("#accountCode").val().length == 0){
		layer.msg("请输入简码");return ;
	}
	if($("#accountName").val() == undefined || $("#accountName").val().length == 0){
		layer.msg("请输入名称");return ;
	}

	
	
	$.ajax({
		url : "haiAccountEditSubmit",
		type:"post",dataType:"json",data:$("#haiAccountForm").serialize(),
		success:function(result){
			if(result.code != 1){
				layer.msg(result.msg);
				return ;
			}else{
				$('#haiAccountForm')[0].reset();
				layer.alert(result.msg, {
					skin: 'layui-layer-molv' //样式类名
					,closeBtn: 0
				}, function(){
					layer.closeAll();
					haiAccountModal.modal("hide");
					bsTable.bootstrapTable('refresh', { query : {  page : 1} });
				});
			}
		}
	});
}



function haiAccountDelete(accountId){
	layer.confirm('您确定要删除此项吗？',{
		btn: ['确定删除','不删除'] //按钮
	}, function(){
		$.ajax({
			url : "haiAccountDelete",type:"post",dataType:"json",data:{accountId:accountId},
			success:function(result){
				layer.msg(result.msg);
				bsTable.bootstrapTable('refresh');
			}
		});
	}, function(){
		layer.closeAll();
	});
}

