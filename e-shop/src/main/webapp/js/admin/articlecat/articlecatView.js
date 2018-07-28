var haiArticleCatModal ;
var key_catName = "";
var validform = null;
$(function(){
	haiArticleCatModal = $("#haiArticleCatModal").modal({backdrop: 'static', keyboard: false , show : false });
	
	$("#haiArticleCatAddDetail").click(function(){
		haiArticleCatAddDetail();
	});
	
	$("#haiArticleCatSearch").click(function(){
		key_catName = $.trim($("#key_catName").val());
		bsTable.bootstrapTable('refresh', { query : { catName : key_catName , page : 1} });
	});
    
	
	$("#haiArticleCatSaveSubmit").click(function(){$("#haiArticleCatForm").attr("action") == "add" ? haiArticleCatAddSubmit() : haiArticleCatEditSubmit() ;});

	validform = $("#haiArticleCatForm").validate({rules:{},messages:{}});
	
});

function haiArticleCatAddDetail(){
	haiArticleCatModal.modal("show");
	$("#haiArticleCatForm").attr("action","add");
	$('#haiArticleCatForm')[0].reset();
	select_reset_parentId();
}


function haiArticleCatEditDetail(catId){
	$("#haiArticleCatForm").attr("action","edit");
	layer.load(0, {shade: false});
	$.ajax({
		url : "haiArticleCatEditDetail",type:"post",dataType:"json",data:{catId:catId},
		success:function(result){
			layer.closeAll();
			haiArticleCatModal.modal("show");
			$.each(result.model,function(id,ele){
				$("#"+id).val(ele);
			});
			select_reset_parentId();
		}
	});
}



function haiArticleCatAddSubmit(){
	
	if($("#catName").val() == undefined || $("#catName").val().length == 0){
		layer.msg("请输入cat_name");return ;
	}

	
	
	if(!validform.form())return ;
	
	$.ajax({
		url : "haiArticleCatAddSubmit",
		type:"post",dataType:"json",data:$("#haiArticleCatForm").serialize(),
		success:function(result){
			if(result.code != 1){
				layer.msg(result.msg);
				return ;
			}else{
				$('#haiArticleCatForm')[0].reset();
				layer.confirm(result.msg, {
					btn: ['继续添加','返回列表'] //按钮
				}, function(){
				  //继续添加
					layer.closeAll();
					select_reset_parentId();
				}, function(){
				  //返回列表
					layer.closeAll();
					haiArticleCatModal.modal("hide");
					bsTable.bootstrapTable('refresh', { query : {  page : 1} });
					select_reset_parentId();
				});
			}
		}
	});
}

function haiArticleCatEditSubmit(){
	
	if($("#catName").val() == undefined || $("#catName").val().length == 0){
		layer.msg("请输入cat_name");return ;
	}

	
	if(!validform.form())return ;
	
	$.ajax({
		url : "haiArticleCatEditSubmit",
		type:"post",dataType:"json",data:$("#haiArticleCatForm").serialize(),
		success:function(result){
			if(result.code != 1){
				layer.msg(result.msg);
				return ;
			}else{
				$('#haiArticleCatForm')[0].reset();
				layer.alert(result.msg, {
					skin: 'layui-layer-molv' //样式类名
					,closeBtn: 0
				}, function(){
					layer.closeAll();
					haiArticleCatModal.modal("hide");
					bsTable.bootstrapTable('refresh', { query : {  page : 1} });
					select_reset_parentId();
				});
			}
		}
	});
}



function haiArticleCatDelete(catId){
	layer.confirm('您确定要删除此项吗？',{
		btn: ['确定删除','不删除'] //按钮
	}, function(){
		$.ajax({
			url : "haiArticleCatDelete",type:"post",dataType:"json",data:{catId:catId},
			success:function(result){
				layer.msg(result.msg);
				bsTable.bootstrapTable('refresh');
			}
		});
	}, function(){
		layer.closeAll();
	});
}

