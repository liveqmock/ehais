$(function(){
	$(".batch").click(function(){
		elay.confirm({content:"确定批量审批通过吗？",btn:["确定提交","暂不提交"],sure:function(){
			approve_submit_batch(1);
		}});
	});
});


function approve_submit_batch(approve){
	$.ajax({
		url : "ep_school_begapprove_submit_batch",data:{approve:approve},
		success : function(result){
			elay.open({content:result.msg});
			if(result.code == 1){
				$(".submit").attr("disabled",true);
			}
		}
	});
}

