var haiOrderFinanceModal ;
var key_financeName = "";
var validform = null;
$(function(){
	haiOrderFinanceModal = $("#haiOrderFinanceModal").modal({backdrop: 'static', keyboard: false , show : false });
	
	$("#haiOrderFinanceAddDetail").click(function(){
		haiOrderFinanceAddDetail();
	});
	
	$("#haiOrderFinanceSearch").click(function(){
		key_financeName = $.trim($("#key_financeName").val());
		bsTable.bootstrapTable('refresh', { query : { financeName : key_financeName , page : 1} });
	});
    
	
	$("#haiOrderFinanceSaveSubmit").click(function(){$("#haiOrderFinanceForm").attr("action") == "add" ? haiOrderFinanceAddSubmit() : haiOrderFinanceEditSubmit() ;});

	validform = $("#haiOrderFinanceForm").validate({rules:{},messages:{}});
	
});

function haiOrderFinanceAddDetail(){
	haiOrderFinanceModal.modal("show");
	$("#haiOrderFinanceForm").attr("action","add");
	$('#haiOrderFinanceForm')[0].reset();
	select_reset_parentId();
}


function haiOrderFinanceEditDetail(financeId){
	$("#haiOrderFinanceForm").attr("action","edit");
	layer.load(0, {shade: false});
	$.ajax({
		url : "haiOrderFinanceEditDetail",type:"post",dataType:"json",data:{financeId:financeId},
		success:function(result){
			layer.closeAll();
			haiOrderFinanceModal.modal("show");
			$.each(result.model,function(id,ele){
				$("#"+id).val(ele);
			});
			select_reset_parentId();
		}
	});
}



function haiOrderFinanceAddSubmit(){
	

	
	
	if(!validform.form())return ;
	
	$.ajax({
		url : "haiOrderFinanceAddSubmit",
		type:"post",dataType:"json",data:$("#haiOrderFinanceForm").serialize(),
		success:function(result){
			if(result.code != 1){
				layer.msg(result.msg);
				return ;
			}else{
				$('#haiOrderFinanceForm')[0].reset();
				layer.confirm(result.msg, {
					btn: ['继续添加','返回列表'] //按钮
				}, function(){
				  //继续添加
					layer.closeAll();
					select_reset_parentId();
				}, function(){
				  //返回列表
					layer.closeAll();
					haiOrderFinanceModal.modal("hide");
					bsTable.bootstrapTable('refresh', { query : {  page : 1} });
					select_reset_parentId();
				});
			}
		}
	});
}

function haiOrderFinanceEditSubmit(){
	

	
	if(!validform.form())return ;
	
	$.ajax({
		url : "haiOrderFinanceEditSubmit",
		type:"post",dataType:"json",data:$("#haiOrderFinanceForm").serialize(),
		success:function(result){
			if(result.code != 1){
				layer.msg(result.msg);
				return ;
			}else{
				$('#haiOrderFinanceForm')[0].reset();
				layer.alert(result.msg, {
					skin: 'layui-layer-molv' //样式类名
					,closeBtn: 0
				}, function(){
					layer.closeAll();
					haiOrderFinanceModal.modal("hide");
					bsTable.bootstrapTable('refresh', { query : {  page : 1} });
					select_reset_parentId();
				});
			}
		}
	});
}



function haiOrderFinanceDelete(financeId){
	layer.confirm('您确定要删除此项吗？',{
		btn: ['确定删除','不删除'] //按钮
	}, function(){
		$.ajax({
			url : "haiOrderFinanceDelete",type:"post",dataType:"json",data:{financeId:financeId},
			success:function(result){
				layer.msg(result.msg);
				bsTable.bootstrapTable('refresh');
			}
		});
	}, function(){
		layer.closeAll();
	});
}

