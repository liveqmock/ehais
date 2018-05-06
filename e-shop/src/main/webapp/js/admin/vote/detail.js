$(function(){

	$("#saveSubmit").click(function(){$("#myForm").attr("action") == "add" ? haiVoteAddSubmit() : haiVoteEditSubmit() ;});

});


function haiVoteAddSubmit(){
	
	if($("#voteName").val() == undefined || $("#voteName").val().length == 0){
		layer.msg("请输入vote_name");return ;
	}
	if($("#startTime").val() == undefined || $("#startTime").val().length == 0){
		layer.msg("请输入start_time");return ;
	}
	if($("#endTime").val() == undefined || $("#endTime").val().length == 0){
		layer.msg("请输入end_time");return ;
	}
	if($("#canMulti").val() == undefined || $("#canMulti").val().length == 0){
		layer.msg("请输入can_multi");return ;
	}
	if($("#voteCount").val() == undefined || $("#voteCount").val().length == 0){
		layer.msg("请输入vote_count");return ;
	}

	
	
	$.ajax({
		url : "haiVoteAddSubmit",
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

function haiVoteEditSubmit(){
	
	if($("#voteName").val() == undefined || $("#voteName").val().length == 0){
		layer.msg("请输入vote_name");return ;
	}
	if($("#startTime").val() == undefined || $("#startTime").val().length == 0){
		layer.msg("请输入start_time");return ;
	}
	if($("#endTime").val() == undefined || $("#endTime").val().length == 0){
		layer.msg("请输入end_time");return ;
	}
	if($("#canMulti").val() == undefined || $("#canMulti").val().length == 0){
		layer.msg("请输入can_multi");return ;
	}
	if($("#voteCount").val() == undefined || $("#voteCount").val().length == 0){
		layer.msg("请输入vote_count");return ;
	}

	
	
	$.ajax({
		url : "haiVoteEditSubmit",
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
