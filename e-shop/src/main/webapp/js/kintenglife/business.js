
$(function(){
	$("#submit").click(function(){
		
		if($("#guest").val().length == 0){
			elay.toast({content:"请输入您的姓名"});
			return ;
		}
		if($("#mobile").val().length == 0){
			elay.toast({content:"请输入您的电话"});
			return ;
		}
		
		if($("#content").val().length == 0){
			elay.toast({content:"请输入代理意向产品"});
			return ;
		}
		
		var data = {};
		$(".form input[type='text']").each(function(){
			data[$(this).attr("id")] = $(this).val();
		});
		
		$.ajax({
			url : "businessSubmit.kintenglife",data : data,dataType:"json",type:"post",
			success : function(result){
				elay.toast({content:result.msg});
				if(result.code == 1){
					$(".form input[type='text']").each(function(){
						$(this).val("");
					});
				}
			}
			
		});
		
	});
});
