
var waterStainDetailModal;

$(function(){
	waterStainDetailModal = $("#waterStainDetailModal").modal({ backdrop: 'static', keyboard: false , show : false });
	
	$("#waterStainA").click(function(){
		
		
		$.ajax({
			url : "media_config.json",type:"post",dataType:"json",
			success:function(data){
				
				$.each(data.rows,function(k,v){
					if(v.code == "waterStainThumb"){
						show_waterStainThumb_pic(v.value);
					}else if(v.code == "isWaterStain"){
						if(v.value == 1){
							$("#isWaterStain").attr("checked",true);
						}else{
							$("#isWaterStain").attr("checked",false);
						}
					}
				});
				waterStainDetailModal.modal("show");
				
			},error:function(err){
				
			}
		});
	});
	
	$("#waterStainSaveSubmit").click(function(){
		var waterStainThumb = $("#waterStainThumb").val();
		var isWaterStain = $("#isWaterStain").val();
		$.ajax({
			url : "shopconfig_update_submit",type:"post",dataType:"json",
			data:{waterStainThumb:waterStainThumb,isWaterStain:isWaterStain},
			success:function(data){
				alert(data.msg);
				waterStainDetailModal.modal("hide");
			}
		});
	});
	
	
});

