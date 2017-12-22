$(function(){
	$("#saveSubmit").click(function(){$("#mediaDetailFormModal").attr("action") == "add" ? mediaArticleAddSubmit() : mediaArticleEditSubmit() ;});

});


function mediaArticleAddSubmit(){
	$.ajax({
		url : "mediaArticleAddSubmit",
		type:"post",dataType:"json",data:$("#mediaDetailFormModal").serialize(),
		success:function(result){
			if(result.code != 1){
				layer.msg(result.msg);
				return ;
			}else{
				$('#mediaDetailFormModal')[0].reset();
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
