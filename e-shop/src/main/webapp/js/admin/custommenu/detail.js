$(function(){

	$("#saveSubmit").click(function(){$("#myForm").attr("action") == "add" ? wpCustomMenuAddSubmit() : wpCustomMenuEditSubmit() ;});
	
	$(".weixin_type_"+$("#type").val()).show();
	if($("#type").val() == ""){
		$("#radioPicker_type li:first").addClass("active");
	}
	$("#radioPicker_type li").click(function(){
		$(".weixin_type").hide();
		$(".weixin_type_"+$(this).attr("value")).show();
	});
});


function wpCustomMenuAddSubmit(){
	$.ajax({
		url : "wpCustomMenuAddSubmit",
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

function wpCustomMenuEditSubmit(){
	$.ajax({
		url : "wpCustomMenuEditSubmit",
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
