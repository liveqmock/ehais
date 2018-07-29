var haiGoodsGalleryModal ;
var key_tableName = "";
var validform = null;
$(function(){
	haiGoodsGalleryModal = $("#haiGoodsGalleryModal").modal({backdrop: 'static', keyboard: false , show : false });
	
	$("#haiGoodsGalleryAddDetail").click(function(){
		haiGoodsGalleryAddDetail();
	});
	
	$("#haiGoodsGallerySearch").click(function(){
		key_tableName = $.trim($("#key_tableName").val());
		bsTable.bootstrapTable('refresh', { query : { tableName : key_tableName , page : 1} });
	});
    
	
	$("#haiGoodsGallerySaveSubmit").click(function(){$("#haiGoodsGalleryForm").attr("action") == "add" ? haiGoodsGalleryAddSubmit() : haiGoodsGalleryEditSubmit() ;});

	validform = $("#haiGoodsGalleryForm").validate({rules:{},messages:{}});
	
});

function haiGoodsGalleryAddDetail(){
	haiGoodsGalleryModal.modal("show");
	$("#haiGoodsGalleryForm").attr("action","add");
	$('#haiGoodsGalleryForm')[0].reset();
	select_reset_parentId();
}


function haiGoodsGalleryEditDetail(imgId){
	$("#haiGoodsGalleryForm").attr("action","edit");
	layer.load(0, {shade: false});
	$.ajax({
		url : "haiGoodsGalleryEditDetail",type:"post",dataType:"json",data:{imgId:imgId},
		success:function(result){
			layer.closeAll();
			haiGoodsGalleryModal.modal("show");
			$.each(result.model,function(id,ele){
				$("#"+id).val(ele);
			});
			select_reset_parentId();
		}
	});
}



function haiGoodsGalleryAddSubmit(){
	

	
	
	if(!validform.form())return ;
	
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
					select_reset_parentId();
				}, function(){
				  //返回列表
					layer.closeAll();
					haiGoodsGalleryModal.modal("hide");
					bsTable.bootstrapTable('refresh', { query : {  page : 1} });
					select_reset_parentId();
				});
			}
		}
	});
}

function haiGoodsGalleryEditSubmit(){
	

	
	if(!validform.form())return ;
	
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
					haiGoodsGalleryModal.modal("hide");
					bsTable.bootstrapTable('refresh', { query : {  page : 1} });
					select_reset_parentId();
				});
			}
		}
	});
}



function haiGoodsGalleryDelete(imgId){
	layer.confirm('您确定要删除此项吗？',{
		btn: ['确定删除','不删除'] //按钮
	}, function(){
		$.ajax({
			url : "haiGoodsGalleryDelete",type:"post",dataType:"json",data:{imgId:imgId},
			success:function(result){
				layer.msg(result.msg);
				bsTable.bootstrapTable('refresh');
			}
		});
	}, function(){
		layer.closeAll();
	});
}

