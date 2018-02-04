/**
 * 
 */

$(function(){
	$("#submit_register").click(function(){
		register();
	});
});

function register(){
	var username = $("#username").val();
	var password = $("#password").val();
	var confirmPassword = $("#confirmPassword").val();
	if(username == null || username.length == 0){
		$.afui.toast("请输入用户名");
		return ;
	}
	if(password == null || password.length == 0){
		$.afui.toast("请输入密码");
		return ;
	}
	if(confirmPassword == null || confirmPassword.length == 0){
		$.afui.toast("请输入确认密码");
		return ;
	}
	if(confirmPassword != password){
		$.afui.toast("密码不一致");
		return ;
	}
	$.ajax({
		url : "/ws/register",
		data : {username : username , password : password , confirmPassword : confirmPassword},
		type : "post",
		dataType : "json",
		success : function(data){},
		beforeSend:function(XHR){},
		complete:function(XHR, TS){},
		error : function(e) { }
	});
}

