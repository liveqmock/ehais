var haiLabourModal ;
var key_labourName = "";
var validform = null;
$(function(){
	haiLabourModal = $("#haiLabourModal").modal({backdrop: 'static', keyboard: false , show : false });
	
	$("#haiLabourAddDetail").click(function(){
		haiLabourAddDetail();
	});
	
	$("#haiLabourSearch").click(function(){
		key_labourName = $.trim($("#key_labourName").val());
		bsTable.bootstrapTable('refresh', { query : { labourName : key_labourName , page : 1} });
	});
    
	
	$("#haiLabourSaveSubmit").click(function(){$("#haiLabourForm").attr("action") == "add" ? haiLabourAddSubmit() : haiLabourEditSubmit() ;});

	//设置自动简码
	$("#labourName").setPinyin({"code":"labourCode"});
});

function haiLabourAddDetail(){
	haiLabourModal.modal("show");
	$("#haiLabourForm").attr("action","add");
	$('#haiLabourForm')[0].reset();
}


function haiLabourEditDetail(labourId){
	$("#haiLabourForm").attr("action","edit");
	layer.load(0, {shade: false});
	$.ajax({
		url : "haiLabourEditDetail",type:"post",dataType:"json",data:{labourId:labourId},
		success:function(result){
			layer.closeAll();
			haiLabourModal.modal("show");
			$.each(result.model,function(id,ele){
				$("#"+id).val(ele);
			});
		}
	});
}



function haiLabourAddSubmit(){
	
	if($("#labourCode").val() == undefined || $("#labourCode").val().length == 0){
		layer.msg("请输入简码");return ;
	}
	if($("#labourName").val() == undefined || $("#labourName").val().length == 0){
		layer.msg("请输入名称");return ;
	}

	if(!validform.form())return ;
	
	$.ajax({
		url : "haiLabourAddSubmit",
		type:"post",dataType:"json",data:$("#haiLabourForm").serialize(),
		success:function(result){
			if(result.code != 1){
				layer.msg(result.msg);
				return ;
			}else{
				$('#haiLabourForm')[0].reset();
				layer.confirm(result.msg, {
					btn: ['继续添加','返回列表'] //按钮
				}, function(){
				  //继续添加
					layer.closeAll();
				}, function(){
				  //返回列表
					layer.closeAll();
					haiLabourModal.modal("hide");
					bsTable.bootstrapTable('refresh', { query : {  page : 1} });
				});
			}
		}
	});
}

function haiLabourEditSubmit(){
	
	if($("#labourCode").val() == undefined || $("#labourCode").val().length == 0){
		layer.msg("请输入简码");return ;
	}
	if($("#labourName").val() == undefined || $("#labourName").val().length == 0){
		layer.msg("请输入名称");return ;
	}

	if(!validform.form())return ;
	
	$.ajax({
		url : "haiLabourEditSubmit",
		type:"post",dataType:"json",data:$("#haiLabourForm").serialize(),
		success:function(result){
			if(result.code != 1){
				layer.msg(result.msg);
				return ;
			}else{
				$('#haiLabourForm')[0].reset();
				layer.alert(result.msg, {
					skin: 'layui-layer-molv' //样式类名
					,closeBtn: 0
				}, function(){
					layer.closeAll();
					haiLabourModal.modal("hide");
					bsTable.bootstrapTable('refresh', { query : {  page : 1} });
				});
			}
		}
	});
}



function haiLabourDelete(labourId){
	layer.confirm('您确定要删除此项吗？',{
		btn: ['确定删除','不删除'] //按钮
	}, function(){
		$.ajax({
			url : "haiLabourDelete",type:"post",dataType:"json",data:{labourId:labourId},
			success:function(result){
				layer.msg(result.msg);
				bsTable.bootstrapTable('refresh');
			}
		});
	}, function(){
		layer.closeAll();
	});
}

