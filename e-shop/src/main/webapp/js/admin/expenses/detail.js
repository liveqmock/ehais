$(function(){

	$("#saveSubmit").click(function(){$("#haiExpensesForm").attr("action") == "add" ? haiExpensesAddSubmit() : haiExpensesEditSubmit() ;});

});


function haiExpensesAddSubmit(){
	
	if($("#expensesCode").val() == undefined || $("#expensesCode").val().length == 0){
		layer.msg("请输入简码");return ;
	}
	if($("#expensesName").val() == undefined || $("#expensesName").val().length == 0){
		layer.msg("请输入名称");return ;
	}

	
	
	$.ajax({
		url : "haiExpensesAddSubmit",
		type:"post",dataType:"json",data:$("#haiExpensesForm").serialize(),
		success:function(result){
			if(result.code != 1){
				layer.msg(result.msg);
				return ;
			}else{
				$('#haiExpensesForm')[0].reset();
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

function haiExpensesEditSubmit(){
	
	if($("#expensesCode").val() == undefined || $("#expensesCode").val().length == 0){
		layer.msg("请输入简码");return ;
	}
	if($("#expensesName").val() == undefined || $("#expensesName").val().length == 0){
		layer.msg("请输入名称");return ;
	}

	
	
	$.ajax({
		url : "haiExpensesEditSubmit",
		type:"post",dataType:"json",data:$("#haiExpensesForm").serialize(),
		success:function(result){
			if(result.code != 1){
				layer.msg(result.msg);
				return ;
			}else{
				$('#haiExpensesForm')[0].reset();
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
