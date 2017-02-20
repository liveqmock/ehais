	$(function() {
		getCode();
	});
	/** 为登录按钮绑定点击事件 */
	function submitForm(){
		/** 表单输入校验 */
		var userName = $("#userName");
		var password = $("#password");
		var vcode = $("#vcode");
		var msg = "";
		if ($.trim(userName.val()) == ""){
			msg = "用户名不能为空！";
			userName.focus();
		}else if ($.trim(password.val()) == ""){
			msg = "密码不能为空！";
			password.focus();
		}else if (!/^\w{6,20}$/.test($.trim(password.val()))){
			msg = "密码格式6-20位！";
			password.focus();
		}else if ($.trim(vcode.val()) == ""){
			msg = "验证码不能为空！";
			vcode.focus();
		}else if (!/^[0-9A-Za-z]{4}$/.test($.trim(vcode.val()))){
			msg = "验证码格式不正确！";
			vcode.focus();
		}
		
		if (msg != ""){
			$("#tip").html(msg);
			$("#tipimg").attr("src", "/themes/default/images/ico_error.png");
			$('#tipModal').modal('show');
		}else{
			/** 把表单的请求参数序列化成get请求的字符串 */
			var params = $("#loginForm").serialize();
			/** 异步请求登录 */
			$.post("login_submit.html", params, function(data, status){
				if (data.code == 1){
					
					//判断是否有前置记录，跳回前置记录中去
					if(data.map != undefined && data.map.before_url != undefined && data.map.before_url != ""){
						window.location = data.map.before_url;
					}else{
						window.location = "/index.html";
					}
					
				}else{
					$("#tip").html(data.msg);
					$("#tipimg").attr("src", "/themes/default/images/ico_error.png");
					$('#tipModal').modal('show');
					$("#vimg").trigger("click");
				}
			}, "json");
		}
	};
	
	/** 监听用户是不是按了回车键 */
	$(document).keydown(function(event) {
		if (event.keyCode == 13) {
			if($("#userName").is(":focus")){
				$("#password").focus();
			}else if($("#password").is(":focus")){
				$("#vcode").focus();
			}else if($("#vcode").is(":focus")){
				submitForm();
			}else{
				submitForm();
			}
			
		}
	});
	

	/** 验证看不清*/
	function getCode() {
	$("#vimg").attr("src", "/getCode.html?random=" + Math.random());
};

/** 注册 */
function register(){
	/** 表单输入校验 */
	var userName = $("#userName1");
	var password = $("#password1");
	var state = $("input[name='state']:checked");
	var vcod = $("#vcode_m");
	var mobile = $("#mobile");
	
	var comfirmPassword = $("#comfirmPassword");
	var msg = "";
	if ($.trim(state.val()) == ""){
		msg = "请选择用户类型！";
		state.focus();
	}else if ($.trim(userName.val()) == ""){
		msg = "用户名不能为空！";
		userName.focus();
	}else if ((!/^.{3,20}$/.test($.trim(userName.val())))  && (!/^1[3|4|5|8]\d{9}$/.test(userName.val()))   && (!/^\w+@\w{2,}\.\w{2,}$/.test(userName.val()))){
		msg = "用户名格式不正确！";
		userName.focus();
	}else if ($.trim(password.val()) == ""){
		msg = "密码不能为空！";
		password.focus();
	}else if (!/^\w{6,20}$/.test($.trim(password.val()))){
		msg = "密码格式不正确！";
		password.focus();
	}else if ( password.val() != comfirmPassword.val()){
		msg = "密码不一致！";
		comfirmPassword.focus();
	}else if ($.trim(vcod.val())==""){
		msg = "验证不能为空！";
	}else if (!/^1[3|4|5|8]\d{9}$/.test(mobile.val())){
		msg = "手机号码格式不正确！";
		mobile.focus();
	}
	
	if (msg != ""){
		$("#tip").html(msg);
		$("#tipimg").attr("src", "/themes/default/images/ico_error.png");
		$('#tipModal').modal('show');
	}else{
		/** 把表单的请求参数序列化成get请求的字符串 */
		var params = $("#registerFrom").serialize();
		/** 异步请求登录 */
		$.post("register.html", params, function(data, status){
			if (status == "success"){
				if (data.code == 1){
					$("#tip").html(data.msg);
					$('#tipModal').modal('show');
					$("#tipimg").attr("src", "/themes/default/images/ico_success.png");
				}else{
					$("#tip").html(data.msg);
					$("#tipimg").attr("src", "/themes/default/images/ico_error.png");
					$('#tipModal').modal('show');
				}
			}
		}, "json");
	}
}


/** 注册 */
function submit_register(){
	/** 表单输入校验 */
	var userName = $("#userName1");
	var password = $("#password1");
	var vcod = $("#vcode_m");
	var mobile = $("#mobile");
	var state = $("input[name='state']:checked");
	
	var comfirmPassword = $("#comfirmPassword");
	var msg = "";
	if ($.trim(state.val()) == ""){
		msg = "请选择用户类型！";
		state.focus();
	}else if ($.trim(userName.val()) == ""){
		msg = "用户名不能为空！";
		userName.focus();
	}else if ((!/^.{3,20}$/.test($.trim(userName.val())))  && (!/^1[3|4|5|8]\d{9}$/.test(userName.val()))   && (!/^\w+@\w{2,}\.\w{2,}$/.test(userName.val()))){
		msg = "用户名格式不正确！";
		userName.focus();
	}else if ($.trim(password.val()) == ""){
		msg = "密码不能为空！";
		password.focus();
	}else if (!/^\w{6,20}$/.test($.trim(password.val()))){
		msg = "密码格式必须是字母数字组成6-20位数！";
		password.focus();
	}else if ( password.val() != comfirmPassword.val()){
		msg = "密码不一致！";
		comfirmPassword.focus();
	}else if ($.trim(vcod.val())==""){
		msg = "验证不能为空！";
	}else if (!/^1[3|4|5|8]\d{9}$/.test(mobile.val())){
		msg = "手机号码格式不正确！";
		mobile.focus();
	}
	
	
	if (msg != ""){
		$("#tip").html(msg);
		$("#tipimg").attr("src", "/themes/default/images/ico_error.png");
		$('#tipModal').modal('show');
	}else{
		/** 把表单的请求参数序列化成get请求的字符串 */
		var params = $("#registerFrom").serialize();
		/** 异步请求登录 */
		$.post("register.html", params, function(data, status){
			if (status == "success"){
				if (data.code == 1){
					//window.location = "/login.html";
					$("#tip").html(data.msg);
					$('#tipModal').modal('show');
					$("#tipimg").attr("src", "/themes/default/images/ico_success.png");
				}else{
					$("#tip").html(data.msg);
					$("#tipimg").attr("src", "/themes/default/images/ico_error.png");
					$('#tipModal').modal('show');
				}
			}
		}, "json");
	}
}


/** 找回密码 */
function submit_find(){
	/** 表单输入校验 */
	var name = $("#name");
	var password = $("#password2");
	var vcod = $("#vcode_p");
	var mobile = $("#phone");
	var msg = "";
	if ($.trim(name.val()) == ""){
		msg = "用户名不能为空！";
		name.focus();
	}else if (!/^.{3,20}$/.test($.trim(name.val()))){
		msg = "用户名格式不正确！";
		name.focus();
	}else if ($.trim(password.val()) == ""){
		msg = "密码不能为空！";
		password.focus();
	}else if (!/^\w{6,20}$/.test($.trim(password.val()))){
		msg = "密码格式必须是字母数字组成6-20位数！";
		password.focus();
	}else if ($.trim(vcod.val())==""){
		msg = "验证不能为空！";
	}else if (!/^1[3|4|5|8]\d{9}$/.test(mobile.val())){
		msg = "手机号码格式不正确！";
		mobile.focus();
	}
	
	
	if (msg != ""){
		$("#tip").html(msg);
		$("#tipimg").attr("src", "/themes/default/images/ico_error.png");
		$('#tipModal').modal('show');
	}else{
		/** 把表单的请求参数序列化成get请求的字符串 */
		var params = $("#findPwFrom").serialize();
		/** 异步请求登录 */
		$.post("forgotPassword.html", params, function(data, status){
			if (status == "success"){
				if (data.code == 1){
					$("#tip").html(data.msg);
					$('#tipModal').modal('show');
					$("#tipimg").attr("src", "/themes/default/images/ico_success.png");
				}else{
					$("#tip").html(data.msg);
					$("#tipimg").attr("src", "/themes/default/images/ico_error.png");
					$('#tipModal').modal('show');
				}
			}
		}, "json");
	}
}


$(function(){
	$(".register_but").click(function(){
		$('#loginModal').modal('hide');
		$('#userModal').modal('show');
	});
	
	$("#backLogin").click(function(){
		$('#userModal').modal('hide');
		$('#loginModal').modal('show');
	});
	
	$(".forgetPassword").click(function(){
		$('#findModal').modal('show');
	});
	
});

//注册获取手机验证码
function getMobileCode(obj) {
	var mobile = $("#mobile").val();
	if (!/^1[3|4|5|8]\d{9}$/.test(mobile)){
		alert("手机号码格式不正确");
		return ;
	};
	
	alert(obj.mobile);
	
	$.ajax({
		type:"post",
		url:"/find_phone_code.api",
		async:true,
		data : {mobile:mobile},
		dataType : "json",
		success : function(data,status){
			if(data.code != 1){
				alert(data.msg);
				return ;
			}
			settime(obj);
		},
		error : function(err){
			alert("网络异常，请请检查网络设备");
		},
	});
};

//找回密码获取手机验证码
function getPhoneCode(obj) {
	var mobile = $("#phone").val();
	if (!/^1[3|4|5|8]\d{9}$/.test(mobile)){
		alert("手机号码格式不正确");
		return ;
	};
	
	alert(obj.mobile);
	
	$.ajax({
		type:"post",
		url:"/find_phone_code.api",
		async:true,
		data : {mobile:mobile},
		dataType : "json",
		success : function(data,status){
			if(data.code != 1){
				alert(data.msg);
				return ;
			}
			settime(obj);
		},
		error : function(err){
			alert("网络异常，请请检查网络设备");
		},
	});
};


var countdown = 60;
function settime(obj) {
	if (countdown == 0) {
		obj.removeAttribute("disabled");
		obj.value = "免费获取验证码";
		countdown = 60;
		return;
	} else {
		obj.setAttribute("disabled", true);
		obj.value = "重新发送(" + countdown + ")";
		countdown--;
	}
	setTimeout(function() {
		settime(obj)
	}, 1000)
}




