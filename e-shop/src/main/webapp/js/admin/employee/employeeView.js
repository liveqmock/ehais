var haiEmployeeModal ;
var key_employeeName = "";
var validform = null;
$(function(){
	haiEmployeeModal = $("#haiEmployeeModal").modal({backdrop: 'static', keyboard: false , show : false });
	
	$("#haiEmployeeAddDetail").click(function(){
		haiEmployeeAddDetail();
	});
	
	$("#haiEmployeeSearch").click(function(){
		key_employeeName = $.trim($("#key_employeeName").val());
		bsTable.bootstrapTable('refresh', { query : { employeeName : key_employeeName , page : 1} });
	});
    
	
	$("#haiEmployeeSaveSubmit").click(function(){$("#haiEmployeeForm").attr("action") == "add" ? haiEmployeeAddSubmit() : haiEmployeeEditSubmit() ;});

	//设置自动简码
	$("#employeeName").setPinyin({"code":"employeeCode"});
	
	validform = $("#haiEmployeeForm").validate({rules:{},messages:{}});
});

function haiEmployeeAddDetail(){
	haiEmployeeModal.modal("show");
	$("#haiEmployeeForm").attr("action","add");
	$('#haiEmployeeForm')[0].reset();
}


function haiEmployeeEditDetail(employeeId){
	$("#haiEmployeeForm").attr("action","edit");
	layer.load(0, {shade: false});
	$.ajax({
		url : "haiEmployeeEditDetail",type:"post",dataType:"json",data:{employeeId:employeeId},
		success:function(result){
			layer.closeAll();
			haiEmployeeModal.modal("show");
			$.each(result.model,function(id,ele){
				$("#"+id).val(ele);
			});
		}
	});
}



function haiEmployeeAddSubmit(){
	
	if($("#employeeCode").val() == undefined || $("#employeeCode").val().length == 0){
		layer.msg("请输入简码");return ;
	}
	if($("#employeeName").val() == undefined || $("#employeeName").val().length == 0){
		layer.msg("请输入名称");return ;
	}

	if(!validform.form())return ;
	
	$.ajax({
		url : "haiEmployeeAddSubmit",
		type:"post",dataType:"json",data:$("#haiEmployeeForm").serialize(),
		success:function(result){
			if(result.code != 1){
				layer.msg(result.msg);
				return ;
			}else{
				$('#haiEmployeeForm')[0].reset();
				layer.confirm(result.msg, {
					btn: ['继续添加','返回列表'] //按钮
				}, function(){
				  //继续添加
					layer.closeAll();
				}, function(){
				  //返回列表
					layer.closeAll();
					haiEmployeeModal.modal("hide");
					bsTable.bootstrapTable('refresh', { query : {  page : 1} });
				});
			}
		}
	});
}

function haiEmployeeEditSubmit(){
	
	if($("#employeeCode").val() == undefined || $("#employeeCode").val().length == 0){
		layer.msg("请输入简码");return ;
	}
	if($("#employeeName").val() == undefined || $("#employeeName").val().length == 0){
		layer.msg("请输入名称");return ;
	}

	if(!validform.form())return ;
	
	$.ajax({
		url : "haiEmployeeEditSubmit",
		type:"post",dataType:"json",data:$("#haiEmployeeForm").serialize(),
		success:function(result){
			if(result.code != 1){
				layer.msg(result.msg);
				return ;
			}else{
				$('#haiEmployeeForm')[0].reset();
				layer.alert(result.msg, {
					skin: 'layui-layer-molv' //样式类名
					,closeBtn: 0
				}, function(){
					layer.closeAll();
					haiEmployeeModal.modal("hide");
					bsTable.bootstrapTable('refresh', { query : {  page : 1} });
				});
			}
		}
	});
}



function haiEmployeeDelete(employeeId){
	layer.confirm('您确定要删除此项吗？',{
		btn: ['确定删除','不删除'] //按钮
	}, function(){
		$.ajax({
			url : "haiEmployeeDelete",type:"post",dataType:"json",data:{employeeId:employeeId},
			success:function(result){
				layer.msg(result.msg);
				bsTable.bootstrapTable('refresh');
			}
		});
	}, function(){
		layer.closeAll();
	});
}

