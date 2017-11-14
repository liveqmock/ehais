$(function(){
	$("footer ul li").click(function(){
		if($(this).attr("h").length > 0){
			window.location.href = $(this).attr("h") + ($(this).attr("h").indexOf("!") > 0 ? cid : "") ;
		}
	});
});