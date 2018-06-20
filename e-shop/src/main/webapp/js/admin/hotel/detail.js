$(function(){

	$("#saveSubmit").click(function(){$("#haiHotelForm").attr("action") == "add" ? haiHotelAddSubmit() : haiHotelEditSubmit() ;});

});


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
					window.history.back();
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
					window.history.back();
				});
			}
		}
	});
}
