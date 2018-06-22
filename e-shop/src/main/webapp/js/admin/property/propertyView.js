var haiPropertyModal ;
var key_propertyName = "";
var validform = null;
$(function(){
	haiPropertyModal = $("#haiPropertyModal").modal({backdrop: 'static', keyboard: false , show : false });
	
	$("#haiPropertyAddDetail").click(function(){
		haiPropertyAddDetail();
	});
	
	$("#haiPropertySearch").click(function(){
		key_propertyName = $.trim($("#key_propertyName").val());
		bsTable.bootstrapTable('refresh', { query : { propertyName : key_propertyName , page : 1} });
	});
    
	
	$("#haiPropertySaveSubmit").click(function(){$("#haiPropertyForm").attr("action") == "add" ? haiPropertyAddSubmit() : haiPropertyEditSubmit() ;});

	//设置自动简码
	$("#propertyName").setPinyin({"code":"propertyCode"});
});

function haiPropertyAddDetail(){
	haiPropertyModal.modal("show");
	$("#haiPropertyForm").attr("action","add");
	$('#haiPropertyForm')[0].reset();
}


function haiPropertyEditDetail(propertyId){
	$("#haiPropertyForm").attr("action","edit");
	layer.load(0, {shade: false});
	$.ajax({
		url : "haiPropertyEditDetail",type:"post",dataType:"json",data:{propertyId:propertyId},
		success:function(result){
			layer.closeAll();
			haiPropertyModal.modal("show");
			$.each(result.model,function(id,ele){
				$("#"+id).val(ele);
			});
		}
	});
}



function haiPropertyAddSubmit(){
	
	if($("#propertyCode").val() == undefined || $("#propertyCode").val().length == 0){
		layer.msg("请输入简码");return ;
	}
	if($("#propertyName").val() == undefined || $("#propertyName").val().length == 0){
		layer.msg("请输入名称");return ;
	}

	if(!validform.form())return ;
	
	$.ajax({
		url : "haiPropertyAddSubmit",
		type:"post",dataType:"json",data:$("#haiPropertyForm").serialize(),
		success:function(result){
			if(result.code != 1){
				layer.msg(result.msg);
				return ;
			}else{
				$('#haiPropertyForm')[0].reset();
				layer.confirm(result.msg, {
					btn: ['继续添加','返回列表'] //按钮
				}, function(){
				  //继续添加
					layer.closeAll();
				}, function(){
				  //返回列表
					layer.closeAll();
					haiPropertyModal.modal("hide");
					bsTable.bootstrapTable('refresh', { query : {  page : 1} });
				});
			}
		}
	});
}

function haiPropertyEditSubmit(){
	
	if($("#propertyCode").val() == undefined || $("#propertyCode").val().length == 0){
		layer.msg("请输入简码");return ;
	}
	if($("#propertyName").val() == undefined || $("#propertyName").val().length == 0){
		layer.msg("请输入名称");return ;
	}

	if(!validform.form())return ;
	
	$.ajax({
		url : "haiPropertyEditSubmit",
		type:"post",dataType:"json",data:$("#haiPropertyForm").serialize(),
		success:function(result){
			if(result.code != 1){
				layer.msg(result.msg);
				return ;
			}else{
				$('#haiPropertyForm')[0].reset();
				layer.alert(result.msg, {
					skin: 'layui-layer-molv' //样式类名
					,closeBtn: 0
				}, function(){
					layer.closeAll();
					haiPropertyModal.modal("hide");
					bsTable.bootstrapTable('refresh', { query : {  page : 1} });
				});
			}
		}
	});
}



function haiPropertyDelete(propertyId){
	layer.confirm('您确定要删除此项吗？',{
		btn: ['确定删除','不删除'] //按钮
	}, function(){
		$.ajax({
			url : "haiPropertyDelete",type:"post",dataType:"json",data:{propertyId:propertyId},
			success:function(result){
				layer.msg(result.msg);
				bsTable.bootstrapTable('refresh');
			}
		});
	}, function(){
		layer.closeAll();
	});
}

