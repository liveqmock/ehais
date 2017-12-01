$(function(){
	$("dd").click(function(){
		if(!isBlank($(this).attr("h"))){
			window.location.href = $(this).attr("h");
		}else{
			elay.toast({content:"功能正在建设中"});
		}
	});
});