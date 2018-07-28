$(function(){

	$("#saveSubmit").click(function(){$("#haiGoodsGalleryForm").attr("action") == "add" ? haiGoodsGalleryAddSubmit() : haiGoodsGalleryEditSubmit() ;});

});


function haiGoodsGalleryAddSubmit(){
	
	if($("#goodsId").val() == undefined || $("#goodsId").val().length == 0){
		layer.msg("请输入goods_id");return ;
	}
	if($("#imgUrl").val() == undefined || $("#imgUrl").val().length == 0){
		layer.msg("请输入img_url");return ;
	}
	if($("#imgDesc").val() == undefined || $("#imgDesc").val().length == 0){
		layer.msg("请输入img_desc");return ;
	}
	if($("#thumbUrl").val() == undefined || $("#thumbUrl").val().length == 0){
		layer.msg("请输入thumb_url");return ;
	}
	if($("#imgOriginal").val() == undefined || $("#imgOriginal").val().length == 0){
		layer.msg("请输入img_original");return ;
	}

	
	
	$.ajax({
		url : "haiGoodsGalleryAddSubmit",
		type:"post",dataType:"json",data:$("#haiGoodsGalleryForm").serialize(),
		success:function(result){
			if(result.code != 1){
				layer.msg(result.msg);
				return ;
			}else{
				$('#haiGoodsGalleryForm')[0].reset();
				layer.confirm(result.msg, {
					btn: ['继续添加','返回列表'] //按钮
				}, function(){
				  //继续添加
					layer.closeAll();
				}, function(){
				  //返回列表
					layer.closeAll();
					window.history.back();
				});
			}
		}
	});
}

function haiGoodsGalleryEditSubmit(){
	
	if($("#goodsId").val() == undefined || $("#goodsId").val().length == 0){
		layer.msg("请输入goods_id");return ;
	}
	if($("#imgUrl").val() == undefined || $("#imgUrl").val().length == 0){
		layer.msg("请输入img_url");return ;
	}
	if($("#imgDesc").val() == undefined || $("#imgDesc").val().length == 0){
		layer.msg("请输入img_desc");return ;
	}
	if($("#thumbUrl").val() == undefined || $("#thumbUrl").val().length == 0){
		layer.msg("请输入thumb_url");return ;
	}
	if($("#imgOriginal").val() == undefined || $("#imgOriginal").val().length == 0){
		layer.msg("请输入img_original");return ;
	}

	
	
	$.ajax({
		url : "haiGoodsGalleryEditSubmit",
		type:"post",dataType:"json",data:$("#haiGoodsGalleryForm").serialize(),
		success:function(result){
			if(result.code != 1){
				layer.msg(result.msg);
				return ;
			}else{
				$('#haiGoodsGalleryForm')[0].reset();
				layer.alert(result.msg, {
					skin: 'layui-layer-molv' //样式类名
					,closeBtn: 0
				}, function(){
					layer.closeAll();
					window.history.back();
				});
			}
		}
	});
}
