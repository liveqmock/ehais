var haiCategoryModal ;
var key_catName = "";
var validform = null;
$(function(){
	haiCategoryModal = $("#haiCategoryModal").modal({backdrop: 'static', keyboard: false , show : false });
	
	$("#haiCategoryAddDetail").click(function(){
		haiCategoryAddDetail();
	});
	
	$("#haiCategorySearch").click(function(){
		key_catName = $.trim($("#key_catName").val());
		bsTable.bootstrapTable('refresh', { query : { catName : key_catName , page : 1} });
	});
    
	
	$("#haiCategorySaveSubmit").click(function(){$("#haiCategoryForm").attr("action") == "add" ? haiCategoryAddSubmit() : haiCategoryEditSubmit() ;});

	//设置自动简码
	$("#catName").setPinyin({"code":"catCode"});
	
	validform = $("#haiCategoryForm").validate({rules:{},messages:{}});
});

function haiCategoryAddDetail(){
	haiCategoryModal.modal("show");
	$("#haiCategoryForm").attr("action","add");
	$('#haiCategoryForm')[0].reset();
}


function haiCategoryEditDetail(catId){
	$("#haiCategoryForm").attr("action","edit");
	layer.load(0, {shade: false});
	$.ajax({
		url : "haiCategoryEditDetail",type:"post",dataType:"json",data:{catId:catId},
		success:function(result){
			layer.closeAll();
			haiCategoryModal.modal("show");
			$.each(result.model,function(id,ele){
				$("#"+id).val(ele);
			});
		}
	});
}



function haiCategoryAddSubmit(){
	
	if($("#catCode").val() == undefined || $("#catCode").val().length == 0){
		layer.msg("请输入简码");return ;
	}
	if($("#catName").val() == undefined || $("#catName").val().length == 0){
		layer.msg("请输入名称");return ;
	}

	if(!validform.form())return ;
	
	$.ajax({
		url : "haiCategoryAddSubmit",
		type:"post",dataType:"json",data:$("#haiCategoryForm").serialize(),
		success:function(result){
			if(result.code != 1){
				layer.msg(result.msg);
				return ;
			}else{
				$('#haiCategoryForm')[0].reset();
				layer.confirm(result.msg, {
					btn: ['继续添加','返回列表'] //按钮
				}, function(){
				  //继续添加
					layer.closeAll();
				}, function(){
				  //返回列表
					layer.closeAll();
					haiCategoryModal.modal("hide");
					bsTable.bootstrapTable('refresh', { query : {  page : 1} });
				});
			}
		}
	});
}

function haiCategoryEditSubmit(){
	
	if($("#catCode").val() == undefined || $("#catCode").val().length == 0){
		layer.msg("请输入简码");return ;
	}
	if($("#catName").val() == undefined || $("#catName").val().length == 0){
		layer.msg("请输入名称");return ;
	}

	if(!validform.form())return ;
	
	$.ajax({
		url : "haiCategoryEditSubmit",
		type:"post",dataType:"json",data:$("#haiCategoryForm").serialize(),
		success:function(result){
			if(result.code != 1){
				layer.msg(result.msg);
				return ;
			}else{
				$('#haiCategoryForm')[0].reset();
				layer.alert(result.msg, {
					skin: 'layui-layer-molv' //样式类名
					,closeBtn: 0
				}, function(){
					layer.closeAll();
					haiCategoryModal.modal("hide");
					bsTable.bootstrapTable('refresh', { query : {  page : 1} });
				});
			}
		}
	});
}



function haiCategoryDelete(catId){
	layer.confirm('您确定要删除此项吗？',{
		btn: ['确定删除','不删除'] //按钮
	}, function(){
		$.ajax({
			url : "haiCategoryDelete",type:"post",dataType:"json",data:{catId:catId},
			success:function(result){
				layer.msg(result.msg);
				bsTable.bootstrapTable('refresh');
			}
		});
	}, function(){
		layer.closeAll();
	});
}

