
$(function(){
	$("#submitUnion").click(function(){submitUnion();});
});

function submitUnion(){
	if($.trim($("#store_name").val()) == ""){
		elay.toast({content: '商家名称不能为空',skin: 'msg',time: 3 });
		$("#username").focus(); 
		return ;
	}
	if($.trim($("#password").val()) == ""){
		elay.toast({content: '密码不能为空',skin: 'msg',time: 3 });
		$("#password").focus(); 
		return ;
	}
	if($.trim($("#confirmPassword").val()) == ""){
		elay.toast({content: '确认密码不能为空',skin: 'msg',time: 3 });
		$("#confirmPassword").focus(); 
		return ;
	}
	if($.trim($("#confirmPassword").val()) != $.trim($("#password").val())){
		elay.toast({content: '密码不一致',skin: 'msg',time: 3 });
		$("#suppliers_name").focus(); 
		return ;
	}
	
	if($.trim($("#contacts").val()) == ""){
		elay.toast({content: '餐厅联系人不能为空',skin: 'msg',time: 3 });
		$("#contacts").focus(); 
		return ;
	}
	if($.trim($("#mobile").val()) == ""){
		elay.toast({content: '联系人手机号码不能为空',skin: 'msg',time: 3 });
		$("#mobile").focus(); 
		return ;
	}
	if(!(/^1[3|4|5|8][0-9]\d{4,8}$/.test($.trim($("#mobile").val())))){ 
        elay.toast({content: '手机号不正确',skin: 'msg',time: 3 });
        $("#mobile").focus(); 
        return false; 
    } 
	if($.trim($("#address").val()) == ""){
		elay.toast({content: '餐厅地址不能为空',skin: 'msg',time: 3 });
		$("#address").focus(); 
		return ;
	}
	
	$.ajax({
		url : "/ehaisRegiterUnion!"+cid,
		data : {
			username:$.trim($("#store_name").val()),
			password:$.trim($("#password").val()),
			confirmPassword:$.trim($("#confirmPassword").val()),
			store_name:$.trim($("#store_name").val()),
			mobile:$.trim($("#mobile").val()),
			address:$.trim($("#address").val()),
			contacts:$.trim($("#contacts").val()),
		},
		type:"post",
		dataType:"json",
		success:function(result){
			
			if(result.code != 1){
				elay.toast({content: result.msg,skin: 'msg',time: 3 });
				return ;
			}
			
			$("#submitUnion").attr("disabled",true);
			$("#username").val("");
			$("#password").val("");
			$("#confirmPassword").val("");
			$("#store_name").val("");
			$("#mobile").val("");
			$("#address").val("");
			$("#contacts").val("");
			
			elay.open({content:result.msg,btn : "知道了",yes:function(){window.location.reload();}});
			
		}
	});
}
