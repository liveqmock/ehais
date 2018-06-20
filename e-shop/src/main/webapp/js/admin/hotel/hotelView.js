var haiHotelModal ;
var key_hotelName = "";
$(function(){
	haiHotelModal = $("#haiHotelModal").modal({ keyboard: false , show : false });
	
	$("#haiHotelAddDetail").click(function(){
		haiHotelAddDetail();
	});
	
	$("#haiHotelSearch").click(function(){
		key_hotelName = $.trim($("#key_hotelName").val());
		bsTable.bootstrapTable('refresh', { query : { hotelName : key_hotelName , page : 1} });
	});
    
	
	$("#haiHotelSaveSubmit").click(function(){$("#haiHotelForm").attr("action") == "add" ? haiHotelAddSubmit() : haiHotelEditSubmit() ;});

	
});

function haiHotelAddDetail(){
	haiHotelModal.modal("show");
	$("#haiHotelForm").attr("action","add");
	$('#haiHotelForm')[0].reset();
}


function haiHotelEditDetail(hotelId){
	$("#haiHotelForm").attr("action","edit");
	layer.load(0, {shade: false});
	$.ajax({
		url : "haiHotelEditDetail",type:"post",dataType:"json",data:{hotelId:hotelId},
		success:function(result){
			layer.closeAll();
			haiHotelModal.modal("show");
			$.each(result.model,function(id,ele){
				$("#"+id).val(ele);
			});
		}
	});
}



function haiHotelAddSubmit(){
	

	
	
	$.ajax({
		url : "haiHotelAddSubmit",
		type:"post",dataType:"json",data:$("#haiHotelForm").serialize(),
		success:function(result){
			if(result.code != 1){
				layer.msg(result.msg);
				return ;
			}else{
				$('#haiHotelForm')[0].reset();
				layer.confirm(result.msg, {
					btn: ['继续添加','返回列表'] //按钮
				}, function(){
				  //继续添加
					layer.closeAll();
				}, function(){
				  //返回列表
					layer.closeAll();
					haiHotelModal.modal("hide");
					bsTable.bootstrapTable('refresh', { query : {  page : 1} });
				});
			}
		}
	});
}

function haiHotelEditSubmit(){
	

	
	
	$.ajax({
		url : "haiHotelEditSubmit",
		type:"post",dataType:"json",data:$("#haiHotelForm").serialize(),
		success:function(result){
			if(result.code != 1){
				layer.msg(result.msg);
				return ;
			}else{
				$('#haiHotelForm')[0].reset();
				layer.alert(result.msg, {
					skin: 'layui-layer-molv' //样式类名
					,closeBtn: 0
				}, function(){
					layer.closeAll();
					haiHotelModal.modal("hide");
					bsTable.bootstrapTable('refresh', { query : {  page : 1} });
				});
			}
		}
	});
}



function haiHotelDelete(hotelId){
	layer.confirm('您确定要删除此项吗？',{
		btn: ['确定删除','不删除'] //按钮
	}, function(){
		$.ajax({
			url : "haiHotelDelete",type:"post",dataType:"json",data:{hotelId:hotelId},
			success:function(result){
				layer.msg(result.msg);
				bsTable.bootstrapTable('refresh');
			}
		});
	}, function(){
		layer.closeAll();
	});
}

