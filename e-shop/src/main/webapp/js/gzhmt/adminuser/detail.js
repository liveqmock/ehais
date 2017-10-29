$(function(){

	$("#saveSubmit").click(function(){$("#myForm").attr("action") == "add" ? haiAdminUserAddSubmit() : haiAdminUserEditSubmit() ;});

});


function haiAdminUserAddSubmit(){
	
	if($("#userName").val() == undefined || $("#userName").val().length == 0){
		layer.msg("请输入用户名");return ;
	}
	if($("#email").val() == undefined || $("#email").val().length == 0){
		layer.msg("请输入邮箱");return ;
	}
//	if($("#password").val() == undefined || $("#password").val().length == 0){
//		layer.msg("请输入密码");return ;
//	}

	
	
	$.ajax({
		url : "haiAdminUserAddSubmit",
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

function haiAdminUserEditSubmit(){
	
	if($("#userName").val() == undefined || $("#userName").val().length == 0){
		layer.msg("请输入用户名");return ;
	}
	if($("#email").val() == undefined || $("#email").val().length == 0){
		layer.msg("请输入邮箱");return ;
	}
//	if($("#password").val() == undefined || $("#password").val().length == 0){
//		layer.msg("请输入密码");return ;
//	}

	
	
	$.ajax({
		url : "haiAdminUserEditSubmit",
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
