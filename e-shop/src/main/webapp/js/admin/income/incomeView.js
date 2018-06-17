var haiIncomeModal ;
var key_incomeName = "";
$(function(){
	haiIncomeModal = $("#haiIncomeModal").modal({ keyboard: false , show : false });
	
	$("#haiIncomeAddDetail").click(function(){
		haiIncomeAddDetail();
	});
	
	$("#haiIncomeSearch").click(function(){
		key_incomeName = $.trim($("#key_incomeName").val());
		bsTable.bootstrapTable('refresh', { query : { incomeName : key_incomeName , page : 1} });
	});
    
	
	$("#haiIncomeSaveSubmit").click(function(){$("#haiIncomeForm").attr("action") == "add" ? haiIncomeAddSubmit() : haiIncomeEditSubmit() ;});

	//设置自动简码
	$("#incomeName").setPinyin({"code":"incomeCode"});
});

function haiIncomeAddDetail(){
	haiIncomeModal.modal("show");
	$("#haiIncomeForm").attr("action","add");
	$('#haiIncomeForm')[0].reset();
}


function haiIncomeEditDetail(incomeId){
	$("#haiIncomeForm").attr("action","edit");
	layer.load(0, {shade: false});
	$.ajax({
		url : "haiIncomeEditDetail",type:"post",dataType:"json",data:{incomeId:incomeId},
		success:function(result){
			layer.closeAll();
			haiIncomeModal.modal("show");
			$.each(result.model,function(id,ele){
				$("#"+id).val(ele);
			});
		}
	});
}



function haiIncomeAddSubmit(){
	
	if($("#incomeCode").val() == undefined || $("#incomeCode").val().length == 0){
		layer.msg("请输入简码");return ;
	}
	if($("#incomeName").val() == undefined || $("#incomeName").val().length == 0){
		layer.msg("请输入名称");return ;
	}

	
	
	$.ajax({
		url : "haiIncomeAddSubmit",
		type:"post",dataType:"json",data:$("#haiIncomeForm").serialize(),
		success:function(result){
			if(result.code != 1){
				layer.msg(result.msg);
				return ;
			}else{
				$('#haiIncomeForm')[0].reset();
				layer.confirm(result.msg, {
					btn: ['继续添加','返回列表'] //按钮
				}, function(){
				  //继续添加
					layer.closeAll();
				}, function(){
				  //返回列表
					layer.closeAll();
					haiIncomeModal.modal("hide");
					bsTable.bootstrapTable('refresh', { query : {  page : 1} });
				});
			}
		}
	});
}

function haiIncomeEditSubmit(){
	
	if($("#incomeCode").val() == undefined || $("#incomeCode").val().length == 0){
		layer.msg("请输入简码");return ;
	}
	if($("#incomeName").val() == undefined || $("#incomeName").val().length == 0){
		layer.msg("请输入名称");return ;
	}

	
	
	$.ajax({
		url : "haiIncomeEditSubmit",
		type:"post",dataType:"json",data:$("#haiIncomeForm").serialize(),
		success:function(result){
			if(result.code != 1){
				layer.msg(result.msg);
				return ;
			}else{
				$('#haiIncomeForm')[0].reset();
				layer.alert(result.msg, {
					skin: 'layui-layer-molv' //样式类名
					,closeBtn: 0
				}, function(){
					layer.closeAll();
					haiIncomeModal.modal("hide");
					bsTable.bootstrapTable('refresh', { query : {  page : 1} });
				});
			}
		}
	});
}



function haiIncomeDelete(incomeId){
	layer.confirm('您确定要删除此项吗？',{
		btn: ['确定删除','不删除'] //按钮
	}, function(){
		$.ajax({
			url : "haiIncomeDelete",type:"post",dataType:"json",data:{incomeId:incomeId},
			success:function(result){
				layer.msg(result.msg);
				bsTable.bootstrapTable('refresh');
			}
		});
	}, function(){
		layer.closeAll();
	});
}

