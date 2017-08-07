$(function(){
	var ue = UE.getEditor('content');

	$("#saveSubmit").click(function(){$("#myForm").attr("action") == "add" ? addSubmit() : editSubmit() ;});

});


function addSubmit(){
	$.ajax({
		url : "m_article_info_insert_submit",
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

function editSubmit(){
	
}