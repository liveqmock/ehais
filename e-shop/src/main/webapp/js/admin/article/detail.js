$(function(){

	$("#saveSubmit").click(function(){$("#myForm").attr("action") == "add" ? haiArticleAddSubmit() : haiArticleEditSubmit() ;});

});


function haiArticleAddSubmit(){
	
	if($("#catId").val() == undefined || $("#catId").val().length == 0){
		layer.msg("请输入cat_id");return ;
	}
	if($("#title").val() == undefined || $("#title").val().length == 0){
		layer.msg("请输入title");return ;
	}
	if($("#content").val() == undefined || $("#content").val().length == 0){
		layer.msg("请输入content");return ;
	}
	if($("#author").val() == undefined || $("#author").val().length == 0){
		layer.msg("请输入author");return ;
	}
	if($("#authorEmail").val() == undefined || $("#authorEmail").val().length == 0){
		layer.msg("请输入author_email");return ;
	}
	if($("#keywords").val() == undefined || $("#keywords").val().length == 0){
		layer.msg("请输入keywords");return ;
	}
	if($("#isOpen").val() == undefined || $("#isOpen").val().length == 0){
		layer.msg("请输入is_open");return ;
	}
	if($("#createDate").val() == undefined || $("#createDate").val().length == 0){
		layer.msg("请输入create_date");return ;
	}
	if($("#fileUrl").val() == undefined || $("#fileUrl").val().length == 0){
		layer.msg("请输入file_url");return ;
	}
	if($("#openType").val() == undefined || $("#openType").val().length == 0){
		layer.msg("请输入open_type");return ;
	}
	if($("#link").val() == undefined || $("#link").val().length == 0){
		layer.msg("请输入link");return ;
	}

	
	
	$.ajax({
		url : "haiArticleAddSubmit",
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

function haiArticleEditSubmit(){
	
	if($("#catId").val() == undefined || $("#catId").val().length == 0){
		layer.msg("请输入cat_id");return ;
	}
	if($("#title").val() == undefined || $("#title").val().length == 0){
		layer.msg("请输入title");return ;
	}
	if($("#content").val() == undefined || $("#content").val().length == 0){
		layer.msg("请输入content");return ;
	}
	if($("#author").val() == undefined || $("#author").val().length == 0){
		layer.msg("请输入author");return ;
	}
	if($("#authorEmail").val() == undefined || $("#authorEmail").val().length == 0){
		layer.msg("请输入author_email");return ;
	}
	if($("#keywords").val() == undefined || $("#keywords").val().length == 0){
		layer.msg("请输入keywords");return ;
	}
	if($("#isOpen").val() == undefined || $("#isOpen").val().length == 0){
		layer.msg("请输入is_open");return ;
	}
	if($("#createDate").val() == undefined || $("#createDate").val().length == 0){
		layer.msg("请输入create_date");return ;
	}
	if($("#fileUrl").val() == undefined || $("#fileUrl").val().length == 0){
		layer.msg("请输入file_url");return ;
	}
	if($("#openType").val() == undefined || $("#openType").val().length == 0){
		layer.msg("请输入open_type");return ;
	}
	if($("#link").val() == undefined || $("#link").val().length == 0){
		layer.msg("请输入link");return ;
	}

	
	
	$.ajax({
		url : "haiArticleEditSubmit",
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
