$(function(){

	$("#saveSubmit").click(function(){$("#myForm").attr("action") == "add" ? haiCouponsAddSubmit() : haiCouponsEditSubmit() ;});

});


function haiCouponsAddSubmit(){
	
	if($("#couponsName").val() == undefined || $("#couponsName").val().length == 0){
		layer.msg("请输入优惠券名称");return ;
	}

	
	
	$.ajax({
		url : "haiCouponsAddSubmit",
		type:"post",dataType:"json",data:$("#myForm").serialize(),
		success:function(result){
			if(result.code != 1){
				layer.msg(result.msg);
				return ;
			}else{
				$('#myForm')[0].reset();
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

function haiCouponsEditSubmit(){
	
	if($("#couponsName").val() == undefined || $("#couponsName").val().length == 0){
		layer.msg("请输入优惠券名称");return ;
	}

	
	
	$.ajax({
		url : "haiCouponsEditSubmit",
		type:"post",dataType:"json",data:$("#myForm").serialize(),
		success:function(result){
			if(result.code != 1){
				layer.msg(result.msg);
				return ;
			}else{
				$('#myForm')[0].reset();
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
