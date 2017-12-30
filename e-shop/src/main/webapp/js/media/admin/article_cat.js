$(function(){
	$("#saveCateSubmit").click(function(){$("#mediaCategoryFormModal").attr("action") == "add" ? mediaArticleCatAddSubmit() : mediaArticleCatEditSubmit() ;});
});


function mediaArticleCatAddSubmit(){

	$.ajax({
		url : "mediaArticleCatAddSubmit",
		type:"post",dataType:"json",data:$("#mediaCategoryFormModal").serialize(),
		success:function(result){
			if(result.code != 1){
				layer.msg(result.msg);
				return ;
			}else{
				$('#mediaCategoryFormModal')[0].reset();
				layer.alert(result.msg, {
					skin: 'layui-layer-molv' //样式类名
					,closeBtn: 0
				}, function(){
					layer.closeAll();
					mediaCategoryModal.modal("hide");
					getTree();
				});
			}
		}
	});
}

function mediaArticleCatEditSubmit(){
	var data = $("#mediaCategoryFormModal").serialize();
	
	data = data+"&catId="+$("#cat_id").val();
	
	$.ajax({
		url : "mediaArticleCatEditSubmit",
		type:"post",dataType:"json",data:data,
		success:function(result){
			if(result.code != 1){
				layer.msg(result.msg);
				return ;
			}else{
				$('#mediaCategoryFormModal')[0].reset();
				layer.alert(result.msg, {
					skin: 'layui-layer-molv' //样式类名
					,closeBtn: 0
				}, function(){
					layer.closeAll();
					mediaCategoryModal.modal("hide");
					getTree();
				});
			}
		}
	});
}


