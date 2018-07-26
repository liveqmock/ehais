var haiNavModal ;
var key_name = "";
var validform = null;
$(function(){
	haiNavModal = $("#haiNavModal").modal({backdrop: 'static', keyboard: false , show : false });
	
	$("#haiNavAddDetail").click(function(){
		haiNavAddDetail();
	});
	
	$("#haiNavSearch").click(function(){
		key_name = $.trim($("#key_name").val());
		bsTable.bootstrapTable('refresh', { query : { name : key_name , page : 1} });
	});
    
	
	$("#haiNavSaveSubmit").click(function(){$("#haiNavForm").attr("action") == "add" ? haiNavAddSubmit() : haiNavEditSubmit() ;});

	validform = $("#haiNavForm").validate({rules:{},messages:{}});
	
});

function haiNavAddDetail(){
	haiNavModal.modal("show");
	$("#haiNavForm").attr("action","add");
	$('#haiNavForm')[0].reset();
	select_reset_parentId();
}


function haiNavEditDetail(id){
	$("#haiNavForm").attr("action","edit");
	layer.load(0, {shade: false});
	$.ajax({
		url : "haiNavEditDetail",type:"post",dataType:"json",data:{id:id},
		success:function(result){
			layer.closeAll();
			haiNavModal.modal("show");
			$.each(result.model,function(id,ele){
				$("#"+id).val(ele);
			});
			select_reset_parentId();
		}
	});
}



function haiNavAddSubmit(){
	
	if($("#name").val() == undefined || $("#name").val().length == 0){
		layer.msg("请输入name");return ;
	}

	
	
	if(!validform.form())return ;
	
	$.ajax({
		url : "haiNavAddSubmit",
		type:"post",dataType:"json",data:$("#haiNavForm").serialize(),
		success:function(result){
			if(result.code != 1){
				layer.msg(result.msg);
				return ;
			}else{
				$('#haiNavForm')[0].reset();
				layer.confirm(result.msg, {
					btn: ['继续添加','返回列表'] //按钮
				}, function(){
				  //继续添加
					layer.closeAll();
					select_reset_parentId();
				}, function(){
				  //返回列表
					layer.closeAll();
					haiNavModal.modal("hide");
					bsTable.bootstrapTable('refresh', { query : {  page : 1} });
					select_reset_parentId();
				});
			}
		}
	});
}

function haiNavEditSubmit(){
	
	if($("#name").val() == undefined || $("#name").val().length == 0){
		layer.msg("请输入name");return ;
	}

	
	if(!validform.form())return ;
	
	$.ajax({
		url : "haiNavEditSubmit",
		type:"post",dataType:"json",data:$("#haiNavForm").serialize(),
		success:function(result){
			if(result.code != 1){
				layer.msg(result.msg);
				return ;
			}else{
				$('#haiNavForm')[0].reset();
				layer.alert(result.msg, {
					skin: 'layui-layer-molv' //样式类名
					,closeBtn: 0
				}, function(){
					layer.closeAll();
					haiNavModal.modal("hide");
					bsTable.bootstrapTable('refresh', { query : {  page : 1} });
					select_reset_parentId();
				});
			}
		}
	});
}



function haiNavDelete(id){
	layer.confirm('您确定要删除此项吗？',{
		btn: ['确定删除','不删除'] //按钮
	}, function(){
		$.ajax({
			url : "haiNavDelete",type:"post",dataType:"json",data:{id:id},
			success:function(result){
				layer.msg(result.msg);
				bsTable.bootstrapTable('refresh');
			}
		});
	}, function(){
		layer.closeAll();
	});
}

