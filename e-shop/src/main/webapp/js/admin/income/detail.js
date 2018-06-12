$(function(){

	$("#saveSubmit").click(function(){$("#haiIncomeForm").attr("action") == "add" ? haiIncomeAddSubmit() : haiIncomeEditSubmit() ;});

});


function haiIncomeAddSubmit(){
	
	if($("#incomeCode").val() == undefined || $("#incomeCode").val().length == 0){
		layer.msg("请输入简码");return ;
	}
	if($("#incomeName").val() == undefined || $("#incomeName").val().length == 0){
		layer.msg("请输入名称");return ;
	}

	
	
	$.ajax({
		url : "haiIncomeAddSubmit",
		type:"post",dataType:"json",data:$("#haiIncomeForm").serialize(),
		success:function(result){
			if(result.code != 1){
				layer.msg(result.msg);
				return ;
			}else{
				$('#haiIncomeForm')[0].reset();
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

function haiIncomeEditSubmit(){
	
	if($("#incomeCode").val() == undefined || $("#incomeCode").val().length == 0){
		layer.msg("请输入简码");return ;
	}
	if($("#incomeName").val() == undefined || $("#incomeName").val().length == 0){
		layer.msg("请输入名称");return ;
	}

	
	
	$.ajax({
		url : "haiIncomeEditSubmit",
		type:"post",dataType:"json",data:$("#haiIncomeForm").serialize(),
		success:function(result){
			if(result.code != 1){
				layer.msg(result.msg);
				return ;
			}else{
				$('#haiIncomeForm')[0].reset();
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
