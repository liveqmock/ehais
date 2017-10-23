$(function(){

	$("#saveSubmit").click(function(){haiStoreSettingEditSubmit();});

	switch_distribution_type($("#distributionType").val());
	
	$("#distributionType").change(function(){
		switch_distribution_type($(this).val());
	});
	
	
});


function switch_distribution_type(type){
	switch (parseInt(type)) {
	case 0:
		$("#distributionPercentage_form").addClass("dn");
		$("#firstPercentage_form").addClass("dn");
		$("#secondPercentage_form").addClass("dn");
		$("#thirdPercentage_form").addClass("dn");
		break;
	case 1:
		$("#distributionPercentage_form").addClass("dn");
		$("#firstPercentage_form").removeClass("dn");
		$("#secondPercentage_form").removeClass("dn");
		$("#thirdPercentage_form").removeClass("dn");
		break;
	case 2:
		$("#distributionPercentage_form").addClass("dn");
		$("#firstPercentage_form").addClass("dn");
		$("#secondPercentage_form").addClass("dn");
		$("#thirdPercentage_form").addClass("dn");
		break;
	case 3:
		$("#distributionPercentage_form").removeClass("dn");
		$("#firstPercentage_form").removeClass("dn");
		$("#secondPercentage_form").removeClass("dn");
		$("#thirdPercentage_form").removeClass("dn");
		break;

	default:
		break;
	}
}


function haiStoreSettingEditSubmit(){
	
	
	$.ajax({
		url : "haiStoreSettingEditSubmit",
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
