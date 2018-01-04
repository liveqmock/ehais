
$(function(){
	$("#submit").click(function(){
		
		if($("#content").val().length == 0){
			elay.toast({content:"请输入留言内容"});
			return ;
		}
		
		$.ajax({
			url : "guestbookSubmit.kintenglife",data : {content : $("#content").val()},dataType:"json",type:"post",
			success : function(result){
				elay.toast({content:result.msg});
				if(result.code == 1){
					$("#content").val("");
				}
			}
			
		});
		
	});
});
