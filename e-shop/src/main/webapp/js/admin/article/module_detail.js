$(function(){
	$("#saveSubmit").click(function(){ehaisArticleDetailSubmit();});
});



function ehaisArticleDetailSubmit(){
	$.ajax({
		url : "ehaisArticleDetailSubmit",
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
					
				});
			}
		}
	});
}
