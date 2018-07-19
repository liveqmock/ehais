$(function(){
	var ue = UE.getEditor('content');

	$("#saveSubmit").click(function(){$("#myForm").attr("action") == "add" ? ehaisGoodsAddSubmit() : ehaisGoodsEditSubmit() ;});

	switch_distribution_type($("#distributionType").val());
	
	joinDistributionState();
	$("#radioPicker_joinDistribution li").click(function(){
		joinDistributionState();
	});
	
	
	$("#radioPicker_defaultDistribution li").click(function(){
		if($("#radioPicker_defaultDistribution li.active").val() == "1"){
			
			$("#distributionType").val(distributionType);
			$("#distributionPercentage").val(distributionPercentage);
			$("#firstValue").val(firstValue);
			$("#secondValue").val(secondValue);
			$("#thirdValue").val(thirdValue);
			$("#toIntegral").val(toIntegral);
			
		}else{
			$("#distributionType").val(distributionType);
			$("#distributionPercentage").val(distributionPercentage);
			$("#firstValue").val(0);
			$("#secondValue").val(0);
			$("#thirdValue").val(0);
			$("#toIntegral").val(0);
		}
	});
	
	$("#distributionType").change(function(){
		switch_distribution_type($(this).val());
	});
	
	
});


function ehaisGoodsAddSubmit(){
	$.ajax({
		url : "ehaisGoodsAddSubmit",
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

function ehaisGoodsEditSubmit(){
	$.ajax({
		url : "ehaisGoodsEditSubmit",
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



function switch_distribution_type(type){
	switch (parseInt(type)) {
	case 0:
		$("#distributionPercentage_form").addClass("dn");
		$("#firstValue_form").addClass("dn");
		$("#secondValue_form").addClass("dn");
		$("#thirdValue_form").addClass("dn");
		break;
	case 1:
		$("#distributionPercentage_form").addClass("dn");
		$("#distributionMoney_form").addClass("dn");
		$("#firstValue_form").removeClass("dn");
		$("#secondValue_form").removeClass("dn");
		$("#thirdValue_form").removeClass("dn");
		$(".unit").html("%");
		break;
	case 2:
		$("#distributionPercentage_form").addClass("dn");
		$("#firstValue_form").removeClass("dn");
		$("#secondValue_form").removeClass("dn");
		$("#thirdValue_form").removeClass("dn");
		$(".unit").html("整元");
		break;
	case 3:
		$("#distributionPercentage_form").removeClass("dn");
		$("#distributionMoney_form").removeClass("dn");
		$("#firstValue_form").removeClass("dn");
		$("#secondValue_form").removeClass("dn");
		$("#thirdValue_form").removeClass("dn");
		$(".unit").html("%");
		break;

	default:
		break;
	}
}

//参与跟不参与的切换
function joinDistributionState(){
	if($("#radioPicker_joinDistribution li.active").val() == "0"){
		$(".distribution").addClass("dn");
	}else{
		$(".distribution").removeClass("dn");			
		switch_distribution_type($("#distributionType").val());
	}
}
