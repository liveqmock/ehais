$(function(){

	$("#saveSubmit").click(function(){tpDiningTableBatchAddSubmit();});

});


function tpDiningTableBatchAddSubmit(){
	var prifix = $.trim($("#prifix").val());
	var startNo = $.trim($("#startNo").val());
	var endNo = $.trim($("#endNo").val());
	
	if(prifix.length == 0){
		layer.msg('台号前缀不能为空');return ;
	}
	var reg = /[\u4e00-\u9fa5]/g;
	if(reg.test(prifix)){
		layer.msg('台号前缀不能使用中文');return ;
	}
	if(startNo.length == 0){
		layer.msg('开始号不能为空');return ;
	}
	if(endNo.length == 0){
		layer.msg('结束号不能为空');return ;
	}
	var regno = /^[0-9]*$/;
	if(!regno.test(startNo)){
		layer.msg('开始号必须为数字');return ;
	}
	if(!regno.test(endNo)){
		layer.msg('结束号必须为数字');return ;
	}
	
	$.ajax({
		url : "tpDiningTableBatchAddSubmit",
		type:"post",dataType:"json",data:$("#myForm").serialize(),
		success:function(result){
			if(result.code != 1){
				layer.msg(result.msg);
				return ;
			}else{
				$('#myForm')[0].reset();
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

function tpDiningTableEditSubmit(){
	$.ajax({
		url : "tpDiningTableEditSubmit",
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
					window.history.back();
				});
			}
		}
	});
}
