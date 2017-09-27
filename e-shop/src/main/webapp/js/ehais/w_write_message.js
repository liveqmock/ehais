
$(function(){
	$("#write").click(function(){
		var content = $("#content").val();
		if($.trim(content).length == 0){
			layer.open({
				content : "请输入内容",
				skin : "msg",
				time: 3 //3秒后自动关闭
			});
			return ;
		}
		$.ajax({
			url : "/ws/writeArticleForum",
			data : {sid : sid , content : $.trim(content)},type:"post",dataType:"json",
			success : function(result){
				layer.open({
					content : result.msg,
					btn : ["朕知道了"],
					yes : function(index){
						layer.close(index);
						if(result.code == 1){
							window.history.go(-1);
						}
					}
				});
			}
		});
	});
});