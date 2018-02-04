
$(function(){
	$("#filter li").click(function(){
		if($(this).hasClass("active")){
			$(this).children("div").children("i").toggleClass("active");
		}else{
			$("#filter li, #filter li div i").removeClass("active");
			$(this).addClass("active");			
			$(this).children("div").children("i:first").addClass("active");
		}
		
		sort = $(this).attr("f");
		adsc = $(this).children("div").children("i.active").attr("a");
		
		resetSearch();
		
	});
});