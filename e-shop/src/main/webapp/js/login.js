/**
 * 
 */

$(function(){
	$("#submit_login").click(function(){
		login();
	});
	
	console.log("document.domain:"+document.domain);
	if(document.domain == "localhost"){
		$("#username").val("lgj628");
		$("#password").val("111111");
	}
	
	if(typeof(localStorage["userId"]) != "undefined" && parseInt(localStorage["userId"]) > 0){
		window.location.href = "member";
	}
});

function login(){
	var username = $("#username").val();
	var password = $("#password").val();
	if(username == null || username.length == 0){
		$.afui.toast("请输入用户名");
		return ;
	}
	if(password == null || password.length == 0){
		$.afui.toast("请输入密码");
		return ;
	}
	$.ajax({
		url : "/ws/login",
		data : {username : username , password : password},
		type : "post",
		dataType : "json",
		success : function(data){
			if(data.code != 1){
				$.afui.toast(data.msg);
				return ;
			}
			localStorage["userId"] = data.model.userId;
			localStorage["username"] = username;
//			if(typeof(data.map)=="undefined")return ;
			if(typeof(data.map.back_shop_url)!="undefined"){
				window.location.href = data.map.back_shop_url;
			}else{
				window.location.href = "member";
			}
		},
		beforeSend:function(XHR){},
		complete:function(XHR, TS){},
		error : function(e) { }
	});
}

