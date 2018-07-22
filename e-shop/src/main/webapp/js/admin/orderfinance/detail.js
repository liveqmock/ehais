$(function(){

	$("#saveSubmit").click(function(){$("#haiOrderFinanceForm").attr("action") == "add" ? haiOrderFinanceAddSubmit() : haiOrderFinanceEditSubmit() ;});

});


function haiOrderFinanceAddSubmit(){
	

	
	
	$.ajax({
		url : "haiOrderFinanceAddSubmit",
		type:"post",dataType:"json",data:$("#haiOrderFinanceForm").serialize(),
		success:function(result){
			if(result.code != 1){
				layer.msg(result.msg);
				return ;
			}else{
				$('#haiOrderFinanceForm')[0].reset();
				layer.confirm(result.msg, {
					btn: ['继续添加','返回列表'] //按钮
				}, function(){
				  //继续添加
					layer.closeAll();
				}, function(){
				  //返回列表
					layer.closeAll();
					window.history.back();
				});
			}
		}
	});
}

function haiOrderFinanceEditSubmit(){
	

	
	
	$.ajax({
		url : "haiOrderFinanceEditSubmit",
		type:"post",dataType:"json",data:$("#haiOrderFinanceForm").serialize(),
		success:function(result){
			if(result.code != 1){
				layer.msg(result.msg);
				return ;
			}else{
				$('#haiOrderFinanceForm')[0].reset();
				layer.alert(result.msg, {
					skin: 'layui-layer-molv' //样式类名
					,closeBtn: 0
				}, function(){
					layer.closeAll();
					window.history.back();
				});
			}
		}
	});
}
