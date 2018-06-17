var haiUnitModal ;
var key_unitName = "";
$(function(){
	haiUnitModal = $("#haiUnitModal").modal({ keyboard: false , show : false });
	
	$("#haiUnitAddDetail").click(function(){
		haiUnitAddDetail();
	});
	
	$("#haiUnitSearch").click(function(){
		key_unitName = $.trim($("#key_unitName").val());
		bsTable.bootstrapTable('refresh', { query : { unitName : key_unitName , page : 1} });
	});
    
	
	$("#haiUnitSaveSubmit").click(function(){$("#haiUnitForm").attr("action") == "add" ? haiUnitAddSubmit() : haiUnitEditSubmit() ;});

	
	$("#unitName").setPinyin({"code":"unitCode"});
});

function haiUnitAddDetail(){
	haiUnitModal.modal("show");
	$("#haiUnitForm").attr("action","add");
	$('#haiUnitForm')[0].reset();
}


function haiUnitEditDetail(unitId){
	$("#haiUnitForm").attr("action","edit");
	layer.load(0, {shade: false});
	$.ajax({
		url : "haiUnitEditDetail",type:"post",dataType:"json",data:{unitId:unitId},
		success:function(result){
			layer.closeAll();
			haiUnitModal.modal("show");
			$.each(result.model,function(id,ele){
				$("#"+id).val(ele);
			});
		}
	});
}



function haiUnitAddSubmit(){
	
	if($("#unitCode").val() == undefined || $("#unitCode").val().length == 0){
		layer.msg("请输入简码");return ;
	}
	if($("#unitName").val() == undefined || $("#unitName").val().length == 0){
		layer.msg("请输入名称");return ;
	}

	
	
	$.ajax({
		url : "haiUnitAddSubmit",
		type:"post",dataType:"json",data:$("#haiUnitForm").serialize(),
		success:function(result){
			if(result.code != 1){
				layer.msg(result.msg);
				return ;
			}else{
				$('#haiUnitForm')[0].reset();
				layer.confirm(result.msg, {
					btn: ['继续添加','返回列表'] //按钮
				}, function(){
				  //继续添加
					layer.closeAll();
				}, function(){
				  //返回列表
					layer.closeAll();
					haiUnitModal.modal("hide");
					bsTable.bootstrapTable('refresh', { query : {  page : 1} });
				});
			}
		}
	});
}

function haiUnitEditSubmit(){
	
	if($("#unitCode").val() == undefined || $("#unitCode").val().length == 0){
		layer.msg("请输入简码");return ;
	}
	if($("#unitName").val() == undefined || $("#unitName").val().length == 0){
		layer.msg("请输入名称");return ;
	}

	
	
	$.ajax({
		url : "haiUnitEditSubmit",
		type:"post",dataType:"json",data:$("#haiUnitForm").serialize(),
		success:function(result){
			if(result.code != 1){
				layer.msg(result.msg);
				return ;
			}else{
				$('#haiUnitForm')[0].reset();
				layer.alert(result.msg, {
					skin: 'layui-layer-molv' //样式类名
					,closeBtn: 0
				}, function(){
					layer.closeAll();
					haiUnitModal.modal("hide");
					bsTable.bootstrapTable('refresh', { query : {  page : 1} });
				});
			}
		}
	});
}



function haiUnitDelete(unitId){
	layer.confirm('您确定要删除此项吗？',{
		btn: ['确定删除','不删除'] //按钮
	}, function(){
		$.ajax({
			url : "haiUnitDelete",type:"post",dataType:"json",data:{unitId:unitId},
			success:function(result){
				layer.msg(result.msg);
				bsTable.bootstrapTable('refresh');
			}
		});
	}, function(){
		layer.closeAll();
	});
}

