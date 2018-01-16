$(function(){
	$("#saveSubmit").click(function(){$("#mediaDetailFormModal").attr("action") == "add" ? mediaArticleAddSubmit() : mediaArticleEditSubmit() ;});

});

function checkVal(){
	var title = $("#title").val();
	var catId = $("#catId").val();
	var videoUrl = $("#videoUrl").val();
	var articleThumb = $("#articleThumb").val();
	if(title.length == 0){
		layer.msg("请输入标题");
		return false;
	}
	if(catId == 0){
		layer.msg("请选择分类");
		return false;
	}
	if(videoUrl.length == 0){
		layer.msg("请上传视频");
		return false;
	}
	if(articleThumb.length == 0){
		layer.msg("请上传图片");
		return false;
	}
	return true;
}


function mediaArticleAddSubmit(){
	if(!checkVal())return;
	$.ajax({
		url : "mediaArticleAddSubmit",
		type:"post",dataType:"json",data:$("#mediaDetailFormModal").serialize(),
		success:function(result){
			if(result.code != 1){
				layer.msg(result.msg);
				return ;
			}else{
				$('#mediaDetailFormModal')[0].reset();
//				ue.setContent("");
				layer.confirm(result.msg, {
					btn: ['继续添加','返回列表'] //按钮
				}, function(){
				  //继续添加
					layer.closeAll();
				}, function(){
				  //返回列表
					layer.closeAll();
					mediaDetailModal.modal("hide");
					
					bsTable.bootstrapTable('refresh', { query : {cat_id : cat_id , title : keyword , page : 1} });
					
				});
			}
		}
	});
}

function mediaArticleEditSubmit(){
	$.ajax({
		url : "mediaArticleEditSubmit",
		type:"post",dataType:"json",data:$("#mediaDetailFormModal").serialize(),
		success:function(result){
			if(result.code != 1){
				layer.msg(result.msg);
				return ;
			}else{
				$('#mediaDetailFormModal')[0].reset();
				layer.alert(result.msg, {
					skin: 'layui-layer-molv' //样式类名
					,closeBtn: 0
				}, function(){
					layer.closeAll();
					mediaDetailModal.modal("hide");
					
					bsTable.bootstrapTable('refresh', { query : {cat_id : cat_id , title : keyword , page : 1} });
					
					
				});
			}
		}
	});
}
