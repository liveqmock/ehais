$(function(){
	

	$(".play").click(function(){
		window.location.href = "play"+$(this).attr("val")+".lv";
	});
	
	$("#menu >li").click(function(){
		window.location.href = $(this).attr("href");
	});

});