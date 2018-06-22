var haiExpensesModal ;
var key_expensesName = "";
var validform = null;
$(function(){
	haiExpensesModal = $("#haiExpensesModal").modal({backdrop: 'static', keyboard: false , show : false });
	
	$("#haiExpensesAddDetail").click(function(){
		haiExpensesAddDetail();
	});
	
	$("#haiExpensesSearch").click(function(){
		key_expensesName = $.trim($("#key_expensesName").val());
		bsTable.bootstrapTable('refresh', { query : { expensesName : key_expensesName , page : 1} });
	});
    
	
	$("#haiExpensesSaveSubmit").click(function(){$("#haiExpensesForm").attr("action") == "add" ? haiExpensesAddSubmit() : haiExpensesEditSubmit() ;});

	//设置自动简码
	$("#expensesName").setPinyin({"code":"expensesCode"});
});

function haiExpensesAddDetail(){
	haiExpensesModal.modal("show");
	$("#haiExpensesForm").attr("action","add");
	$('#haiExpensesForm')[0].reset();
}


function haiExpensesEditDetail(expensesId){
	$("#haiExpensesForm").attr("action","edit");
	layer.load(0, {shade: false});
	$.ajax({
		url : "haiExpensesEditDetail",type:"post",dataType:"json",data:{expensesId:expensesId},
		success:function(result){
			layer.closeAll();
			haiExpensesModal.modal("show");
			$.each(result.model,function(id,ele){
				$("#"+id).val(ele);
			});
		}
	});
}



function haiExpensesAddSubmit(){
	
	if($("#expensesCode").val() == undefined || $("#expensesCode").val().length == 0){
		layer.msg("请输入简码");return ;
	}
	if($("#expensesName").val() == undefined || $("#expensesName").val().length == 0){
		layer.msg("请输入名称");return ;
	}

	if(!validform.form())return ;
	
	$.ajax({
		url : "haiExpensesAddSubmit",
		type:"post",dataType:"json",data:$("#haiExpensesForm").serialize(),
		success:function(result){
			if(result.code != 1){
				layer.msg(result.msg);
				return ;
			}else{
				$('#haiExpensesForm')[0].reset();
				layer.confirm(result.msg, {
					btn: ['继续添加','返回列表'] //按钮
				}, function(){
				  //继续添加
					layer.closeAll();
				}, function(){
				  //返回列表
					layer.closeAll();
					haiExpensesModal.modal("hide");
					bsTable.bootstrapTable('refresh', { query : {  page : 1} });
				});
			}
		}
	});
}

function haiExpensesEditSubmit(){
	
	if($("#expensesCode").val() == undefined || $("#expensesCode").val().length == 0){
		layer.msg("请输入简码");return ;
	}
	if($("#expensesName").val() == undefined || $("#expensesName").val().length == 0){
		layer.msg("请输入名称");return ;
	}

	if(!validform.form())return ;
	
	$.ajax({
		url : "haiExpensesEditSubmit",
		type:"post",dataType:"json",data:$("#haiExpensesForm").serialize(),
		success:function(result){
			if(result.code != 1){
				layer.msg(result.msg);
				return ;
			}else{
				$('#haiExpensesForm')[0].reset();
				layer.alert(result.msg, {
					skin: 'layui-layer-molv' //样式类名
					,closeBtn: 0
				}, function(){
					layer.closeAll();
					haiExpensesModal.modal("hide");
					bsTable.bootstrapTable('refresh', { query : {  page : 1} });
				});
			}
		}
	});
}



function haiExpensesDelete(expensesId){
	layer.confirm('您确定要删除此项吗？',{
		btn: ['确定删除','不删除'] //按钮
	}, function(){
		$.ajax({
			url : "haiExpensesDelete",type:"post",dataType:"json",data:{expensesId:expensesId},
			success:function(result){
				layer.msg(result.msg);
				bsTable.bootstrapTable('refresh');
			}
		});
	}, function(){
		layer.closeAll();
	});
}

