$(function(){

	$("#saveSubmit").click(function(){haiStoreSubmit();});

});


function haiStoreSubmit(){
	$.ajax({
		url : "haiStoreSubmit",
		type:"post",dataType:"json",data:$("#myForm").serialize(),
		success:function(result){
			if(result.code != 1){
				layer.msg(result.msg);
				return ;
			}else{
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
