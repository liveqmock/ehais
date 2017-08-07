
$(function(){
	$("#submitUnion").click(function(){submitUnion();});
});

function submitUnion(){
	if($.trim($("#suppliers_name").val()) == ""){
		layer.open({content: '餐厅名称不能为空',skin: 'msg',time: 3 });
		$("#suppliers_name").focus(); 
		return ;
	}
	if($.trim($("#contacts").val()) == ""){
		layer.open({content: '餐厅联系人不能为空',skin: 'msg',time: 3 });
		$("#contacts").focus(); 
		return ;
	}
	if($.trim($("#mobile").val()) == ""){
		layer.open({content: '联系人手机号码不能为空',skin: 'msg',time: 3 });
		$("#mobile").focus(); 
		return ;
	}
	if(!(/^1[3|4|5|8][0-9]\d{4,8}$/.test($.trim($("#mobile").val())))){ 
        layer.open({content: '手机号不正确',skin: 'msg',time: 3 });
        $("#mobile").focus(); 
        return false; 
    } 
	if($.trim($("#address").val()) == ""){
		layer.open({content: '餐厅地址不能为空',skin: 'msg',time: 3 });
		$("#address").focus(); 
		return ;
	}
	
	$.ajax({
		url : "/api.php/Api/DiningUnionApi/saveDining",
		data : {
			suppliers_name:$.trim($("#suppliers_name").val()),
			mobile:$.trim($("#mobile").val()),
			address:$.trim($("#address").val()),
			contacts:$.trim($("#contacts").val()),
		},
		type:"post",
		dataType:"json",
		success:function(result){
			layer.open({content: result.msg,skin: 'msg',time: 3 });
			
			if(result.code != 1){
				return ;
			}
			$("#submitUnion").attr("disabled",true);
			$("#suppliers_name").val("");
			$("#mobile").val("");
			$("#address").val("");
			$("#contacts").val("");
		}
	});
}
